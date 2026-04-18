package com.msb.stp.leadmanagement.services.impl;

import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppEntity;
import com.msb.stp.leadmanagement.exceptions.BusinessException;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.CbcRawCommonService;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.OdsCommonService;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.OdsRequestPayload;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.RequestPayLoadDto;
import com.msb.stp.leadmanagement.repositories.ProcessSaveAppRepository;
import com.msb.stp.leadmanagement.services.EnvConfigService;
import com.msb.stp.leadmanagement.services.ProcessSaveAppService;
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
public class ProcessSaveAppServiceImpl implements ProcessSaveAppService {

    private final ProcessSaveAppRepository processSaveAppRepository;
    private final TransactionTemplate transactionTemplate;
    private final EnvConfigService envConfigService;
    private final OdsCommonService odsCommonService;
    private final CbcRawCommonService cbcRawCommonService;

    @Override
    public OdsRequestPayload fetchDataOds(ProcessSaveAppEntity saveAppEntity) {
        String appid = saveAppEntity.getApplicationId();
        RequestPayLoadDto reqDto = RequestPayLoadDto.builder().appid(appid).build();
        try {

            /// checkStatus and move data
            odsCommonService.checkStatusAndMoveData(reqDto);
            String odsRequestPayload, eventRequestPayload, modelRequestPayload;
            if (StringUtils.isNotEmpty(saveAppEntity.getUpdatePayLoad())) {
                odsRequestPayload = StringUtils.isNotEmpty(saveAppEntity.getOdsRequestPayload())
                        ? saveAppEntity.getOdsRequestPayload()
                        : reqDto.getRequestPayLoad();

                eventRequestPayload = StringUtils.isNotEmpty(saveAppEntity.getEventRequestPayload())
                        ? saveAppEntity.getOdsRequestPayload()
                        : reqDto.getReqByBeforeDMPS();

                modelRequestPayload = StringUtils.isNotEmpty(saveAppEntity.getEventRequestPayload())
                        ? saveAppEntity.getModelRequestPayload()
                        : reqDto.getReqByModel();

            } else {
                odsRequestPayload = reqDto.getRequestPayLoad();
                eventRequestPayload = reqDto.getReqByBeforeDMPS();
                modelRequestPayload = reqDto.getReqByModel();
            }

            if (ObjectUtils.isEmpty(reqDto.getRequestPayLoad())) {
                log.error("FetchDataOds.Can't Find RequestPayload ODS OR IS RUNNING");
                throw new BusinessException("FetchDataOds.Can't Find RequestPayload ODS OR IS RUNNING");
            }

            saveAppEntity.setOdsRequestPayload(odsRequestPayload);
            saveAppEntity.setEventRequestPayload(eventRequestPayload);
            saveAppEntity.setModelRequestPayload(modelRequestPayload);
            return JsonUtil.readValue(reqDto.getRequestPayLoad(), OdsRequestPayload.class);
        }catch (Exception e){
            log.error("FetchDataOds is error=[{}]", e.getMessage());
            this.setProcessAppError(saveAppEntity, e, saveAppEntity.getEnableRetry());
            return null;
        }
    }


    @Override
    public boolean insertCbcRaw(ProcessSaveAppEntity saveAppEntity, OdsRequestPayload requestPayLoad) {
        saveAppEntity.setCurrentStep(CaseProcess.SAVE_CBC_RAW.getCode());
        try {
            cbcRawCommonService.insertCbcRaw(saveAppEntity, requestPayLoad);
            saveAppEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
            log.info("InsertCbcRaw insert Success");
            return true;
        } catch (Exception e) {
            log.error("InsertCbcRaw is error=[{}]", e.getMessage(), e);
            this.setProcessAppError(saveAppEntity, e, saveAppEntity.getEnableRetry());
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ProcessSaveAppEntity buildProcessSaveApp(String appId, String message,
                                                    String status, String currentStep, String versionDataOds) {

        ProcessSaveAppEntity saveAppEntity = new ProcessSaveAppEntity();
        saveAppEntity.setApplicationId(appId);
        saveAppEntity.setKafkaMessage(message);
        saveAppEntity.setStatus(CaseProcess.RUNNING.getCode());
        saveAppEntity.setCurrentStep(CaseProcess.FETCH_DATA_ODS.getCode());
        saveAppEntity.setEnableRetry(CaseProcess.ON_RETRY.getCode());
        if (Strings.isNotEmpty(versionDataOds)) {
            saveAppEntity.setVersion(Long.valueOf(versionDataOds));
        }
        return processSaveAppRepository.save(saveAppEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveProcessStepApp(List<ProcessSaveAppEntity> caseProcessApps) {
        if (CollectionUtils.isEmpty(caseProcessApps)) return;
        processSaveAppRepository.saveAll(caseProcessApps);
    }

    @Override
    public List<ProcessSaveAppEntity> getProcessStepAppByAppId(String appId) {
        if (ObjectUtils.isEmpty(appId)) return null;
        return processSaveAppRepository.findAllByApplicationId(appId);
    }

    @Override
    public List<ProcessSaveAppEntity> getProcessStepAppByRetry() {
        int day = 7, minutes = 10;
        try {
            String dayConfig = envConfigService.getEnvValue("DAY_RETRY_VALUE", "SAVE_CBC_DATA");
            if (StringUtils.isNotEmpty(dayConfig)) day = Integer.parseInt(dayConfig);
            String minuteConfig = envConfigService.getEnvValue("MINUTES_RETRY_VALUE", "SAVE_CBC_DATA");
            if (StringUtils.isNotEmpty(minuteConfig)) minutes = Integer.parseInt(minuteConfig);
        } catch (Exception ex) {
            log.info("GetProcessStepAppByRetry Error={}", ex.getMessage());
        }
        List<ProcessSaveAppEntity> processSaveAppEntities = processSaveAppRepository.findProcessStepByRetry(day, minutes);
        if (ObjectUtils.isEmpty(processSaveAppEntities)) return null;
        return processSaveAppEntities;
    }

    @Override
    public void setProcessAppError(ProcessSaveAppEntity processSaveAppEntity, Exception e, String onRetry) {
        processSaveAppEntity.setStatus(CaseProcess.FAILED.getCode());
        StringBuilder ex = new StringBuilder(e.getMessage());
        if (e.getCause() != null && e.getCause().getCause() != null) {
            ex.append(": ").append(e.getCause().getCause());
        }
        processSaveAppEntity.setErrorMessage(ex.toString());
        processSaveAppEntity.setEnableRetry(onRetry);
        transactionTemplate.execute(trans -> processSaveAppRepository.save(processSaveAppEntity));
    }

    @Override
    public List<ProcessSaveAppEntity> getProcessCurrentStepError() {
        int retryCount = 3, hour = 12;
        try {
            String retryConfig = envConfigService.getEnvValue("MAX_RETRY_SAVE_APP", "MAX_RETRY_SAVE_APP");
            if (StringUtils.isNotEmpty(retryConfig)) retryCount = Integer.parseInt(retryConfig);
            String hourConfig = envConfigService.getEnvValue("HOUR_QUERY_EMAIL", "HOUR_QUERY_EMAIL");
            if (StringUtils.isNotEmpty(hourConfig)) hour = Integer.parseInt(hourConfig);
        } catch (Exception ex) {
            log.info("getProcessCurrentStepError Error={}", ex.getMessage());
        }
        return processSaveAppRepository.findProcessByError(retryCount, hour);
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
        return processSaveAppRepository.moveAndDeleteProcessApp(day);
    }
}
