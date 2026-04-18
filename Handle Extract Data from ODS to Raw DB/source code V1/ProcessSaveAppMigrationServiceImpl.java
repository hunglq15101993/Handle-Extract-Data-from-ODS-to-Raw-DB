package com.msb.stp.leadmanagement.services.impl;

import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppMigrationEntity;
import com.msb.stp.leadmanagement.exceptions.BusinessException;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.CbcRawCommonV1Service;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.OdsCommonV1Service;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.OdsRequestPayload;
import com.msb.stp.leadmanagement.repositories.ProcessSaveAppMigrationRepository;
import com.msb.stp.leadmanagement.services.EnvConfigService;
import com.msb.stp.leadmanagement.services.ProcessSaveAppMigrationService;
import com.msb.stp.leadmanagement.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProcessSaveAppMigrationServiceImpl implements ProcessSaveAppMigrationService {

    private final ProcessSaveAppMigrationRepository processSaveAppMigrationRepository;
    private final TransactionTemplate transactionTemplate;
    private final EnvConfigService envConfigService;
    private final OdsCommonV1Service odsCommonV1Service;
    private final CbcRawCommonV1Service cbcRawCommonV1Service;

    @Override
    public OdsRequestPayload fetchDataOdsMigration(ProcessSaveAppMigrationEntity saveAppMigrationEntity) {
        String appId = saveAppMigrationEntity.getApplicationId();
        try {
            odsCommonV1Service.moveModelExecutionLog(appId);
            String odsRequestPayload, eventRequestPayload;
            if (StringUtils.isNotEmpty(saveAppMigrationEntity.getUpdatePayLoad())) {
                odsRequestPayload = StringUtils.isNotEmpty(saveAppMigrationEntity.getOdsRequestPayload())
                        ? saveAppMigrationEntity.getOdsRequestPayload()
                        : odsCommonV1Service.getRequestPayloadOds(appId);

                eventRequestPayload = StringUtils.isNotEmpty(saveAppMigrationEntity.getEventRequestPayload())
                        ? saveAppMigrationEntity.getOdsRequestPayload()
                        : odsCommonV1Service.getRequestPayloadEventLog(appId);

            } else {
                odsRequestPayload = odsCommonV1Service.getRequestPayloadOds(appId);
                eventRequestPayload = odsCommonV1Service.getRequestPayloadEventLog(appId);
            }

            if (StringUtils.isEmpty(odsRequestPayload)) {
                log.error("FetchDataOds.Can't Find RequestPayload ODS OR IS RUNNING");
                throw new BusinessException("FetchDataOds.Can't Find RequestPayload ODS OR IS RUNNING");
            }
            saveAppMigrationEntity.setOdsRequestPayload(odsRequestPayload);
            saveAppMigrationEntity.setEventRequestPayload(eventRequestPayload);
            return JsonUtil.readValue(odsRequestPayload, OdsRequestPayload.class);
        }catch (Exception e){
            log.error("FetchDataOds is error=[{}]", e.getMessage());
            this.setProcessAppError(saveAppMigrationEntity, e, saveAppMigrationEntity.getEnableRetry());
            return null;
        }
    }


    @Override
    public boolean insertCbcRawBySaveAppMigration(ProcessSaveAppMigrationEntity saveAppMigrationEntity, OdsRequestPayload requestPayLoad) {
        saveAppMigrationEntity.setCurrentStep(CaseProcess.SAVE_CBC_RAW.getCode());
        try {
            cbcRawCommonV1Service.insertCbcRawByMigration(saveAppMigrationEntity, requestPayLoad);
            saveAppMigrationEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
            log.info("InsertCbcRaw insert Success");
            return true;
        } catch (Exception e) {
            log.error("InsertCbcRaw is error=[{}]", e.getMessage());
            this.setProcessAppError(saveAppMigrationEntity, e, saveAppMigrationEntity.getEnableRetry());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ProcessSaveAppMigrationEntity buildProcessSaveAppMigration(String appId, String message,
                                                    String status, String currentStep, String versionDataOds) {

        ProcessSaveAppMigrationEntity saveAppMigrationEntity = new ProcessSaveAppMigrationEntity();
        saveAppMigrationEntity.setApplicationId(appId);
        saveAppMigrationEntity.setKafkaMessage(message);
        saveAppMigrationEntity.setStatus(CaseProcess.RUNNING.getCode());
        saveAppMigrationEntity.setCurrentStep(CaseProcess.FETCH_DATA_ODS.getCode());
        saveAppMigrationEntity.setEnableRetry(CaseProcess.ON_RETRY.getCode());
        if (Strings.isNotEmpty(versionDataOds)) {
            saveAppMigrationEntity.setVersion(Long.valueOf(versionDataOds));
        }
        return processSaveAppMigrationRepository.save(saveAppMigrationEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProcessStepAppMigration(List<ProcessSaveAppMigrationEntity> caseProcessApps) {
        if (CollectionUtils.isEmpty(caseProcessApps)) return;
        processSaveAppMigrationRepository.saveAll(caseProcessApps);
    }

    @Override
    public List<ProcessSaveAppMigrationEntity> getProcessStepAppMigrationByAppId(String appId) {
        if (ObjectUtils.isEmpty(appId)) return null;
        return processSaveAppMigrationRepository.findAllByApplicationId(appId);
    }

    @Override
    public List<ProcessSaveAppMigrationEntity> getProcessStepAppByRetry() {
        int day = 7, minutes = 10;
        try {
            String dayConfig = envConfigService.getEnvValue("DAY_RETRY_VALUE", "SAVE_CBC_DATA");
            if (StringUtils.isNotEmpty(dayConfig)) day = Integer.parseInt(dayConfig);
            String minuteConfig = envConfigService.getEnvValue("MINUTES_RETRY_VALUE", "SAVE_CBC_DATA");
            if (StringUtils.isNotEmpty(minuteConfig)) minutes = Integer.parseInt(minuteConfig);
        } catch (Exception ex) {
            log.info("GetProcessStepAppByRetry Error={}", ex.getMessage());
        }
        List<ProcessSaveAppMigrationEntity> processSaveAppEntities = processSaveAppMigrationRepository.findProcessStepByRetry(day, minutes);
        if (ObjectUtils.isEmpty(processSaveAppEntities)) return null;
        return processSaveAppEntities;
    }

    @Override
    public void setProcessAppError(ProcessSaveAppMigrationEntity processSaveAppEntity, Exception e, String onRetry) {
        processSaveAppEntity.setStatus(CaseProcess.FAILED.getCode());
        StringBuilder ex = new StringBuilder(e.getMessage());
        if (e.getCause() != null && e.getCause().getCause() != null) {
            ex.append(": ").append(e.getCause().getCause());
        }
        processSaveAppEntity.setErrorMessage(ex.toString());
        processSaveAppEntity.setEnableRetry(onRetry);
        transactionTemplate.execute(trans -> processSaveAppMigrationRepository.save(processSaveAppEntity));
    }

    @Override
    public List<ProcessSaveAppMigrationEntity> getProcessCurrentStepError() {
        int retryCount = 3, hour = 12;
        try {
            String retryConfig = envConfigService.getEnvValue("MAX_RETRY_SAVE_APP", "MAX_RETRY_SAVE_APP");
            if (StringUtils.isNotEmpty(retryConfig)) retryCount = Integer.parseInt(retryConfig);
            String hourConfig = envConfigService.getEnvValue("HOUR_QUERY_EMAIL", "HOUR_QUERY_EMAIL");
            if (StringUtils.isNotEmpty(hourConfig)) hour = Integer.parseInt(hourConfig);
        } catch (Exception ex) {
            log.info("getProcessCurrentStepError Error={}", ex.getMessage());
        }
        return processSaveAppMigrationRepository.findProcessByError(retryCount, hour);
    }

    @Transactional
    @Override
    public Object moveAndDeleteProcessApp() {
        int day = 3;
        try {
            String dayConfig = envConfigService.getEnvValue("DAY_MOVE_SAVE_APP_PREV", "DAY_MOVE_SAVE_APP_PREV");
            if (StringUtils.isNotEmpty(dayConfig)) day = Integer.parseInt(dayConfig);
        } catch (Exception ex) {
            log.info("MoveAndDeleteProcessApp Error={}", ex.getMessage());
        }
        return processSaveAppMigrationRepository.moveAndDeleteProcessApp(day);
    }
}
