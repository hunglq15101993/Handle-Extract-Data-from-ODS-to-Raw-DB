package com.msb.stp.leadmanagement.kafka;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.msb.stp.leadmanagement.constants.AppContant;
import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.dto.ApplicationSTP;
import com.msb.stp.leadmanagement.dto.ApplicationSTPInfo;
import com.msb.stp.leadmanagement.dto.RequestSTPError;
import com.msb.stp.leadmanagement.dto.RequestSTPSuccess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppMigrationEntity;
import com.msb.stp.leadmanagement.oracle.cbcMart.repository.CbcMartJdbcRepository;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.OdsRequestPayload;
import com.msb.stp.leadmanagement.services.ProcessSaveAppMigrationService;
import com.msb.stp.leadmanagement.utils.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMigrationConsumer {

    private final ProcessSaveAppMigrationService processSaveAppMigrationService;
    private final CbcMartJdbcRepository cbcMartJdbcRepository;

    /**
     * Apply for source code V1 before 01/04
     * @param message
     * @param ack
     */
    @KafkaListener(
            autoStartup = "${kafka.case-by-case.old-success.startup}",
            topics = "${kafka.case-by-case.old-success.topic}",
            containerFactory = "kafkaListenerContainerFactoryCaseSuccess",
            groupId = "${kafka.case-by-case.old-success.group-id}")
    public void consumeCaseAppMigrationSuccess(String message, Acknowledgment ack) {

        try {
            log.info("consumeCaseAppMigrationSuccess Message=[{}]", message);
            RequestSTPSuccess requestSuccess = JsonUtil.readValue(message, RequestSTPSuccess.class);
            ApplicationSTP application = requestSuccess.getApplication();
            if (ObjectUtils.isEmpty(application)
                    || StringUtils.isEmpty(application.getApplicationDeRefNumber())) {
                log.error("consumeCaseAppMigrationSuccess.appId IS EMPTY message=[{}]", message);
                return;
            }
            String applicationDeRefNumber = application.getApplicationDeRefNumber();
            String versionDataOds = application.getVersionDataRaw();
            String uuid = UUID.randomUUID().toString();
            String traceId = applicationDeRefNumber + "-" + uuid;
            MDC.put(uuid, traceId);
            ProcessSaveAppMigrationEntity saveAppMigrationEntity = this.checkAppMigrationExisted(applicationDeRefNumber, message, versionDataOds);
            if (ObjectUtils.isNotEmpty(saveAppMigrationEntity))
                this.delayAndProcessSaveMigration(saveAppMigrationEntity, message, ack);
        } catch (Exception e) {
            log.error("consumeCaseAppMigrationSuccess function with message = {}", e.getMessage(), e);
            MDC.clear();
        }
    }

    @KafkaListener(
            autoStartup = "${kafka.case-by-case.old-error.startup}",
            topics = "${kafka.case-by-case.old-error.topic}",
            containerFactory = "kafkaListenerContainerFactoryCaseError",
            groupId = "${kafka.case-by-case.old-error.group-id}")
    public void consumeCaseAppMigrationFalse(String message, Acknowledgment ack) {

        try {
            log.info("consumeCaseAppMigrationFalse Message=[{}]", message);
            String uuid = UUID.randomUUID().toString();

            RequestSTPError requestError = JsonUtil.readValue(message, RequestSTPError.class);
            ApplicationSTPInfo applicationInfo = requestError.getApplication();
            if (ObjectUtils.isEmpty(applicationInfo)
                    || ObjectUtils.isEmpty(applicationInfo.getApplicationInfo())
                    || StringUtils.isEmpty(applicationInfo.getApplicationInfo().getApplicationDeRefNumber())) {
                log.error("consumeCaseAppMigrationFalse.appId IS EMPTY message=[{}]", message);
                return;
            }
            String applicationDeRefNumber = applicationInfo.getApplicationInfo().getApplicationDeRefNumber();
            String traceId = applicationDeRefNumber + "-" + uuid;
            String versionDataOds = applicationInfo.getApplicationInfo().getVersionDataRaw();
            MDC.put(uuid, traceId);
            ProcessSaveAppMigrationEntity saveAppMigrationEntity = this.checkAppMigrationExisted(applicationDeRefNumber, message, versionDataOds);
            if (ObjectUtils.isNotEmpty(saveAppMigrationEntity))
                this.delayAndProcessSaveMigration(saveAppMigrationEntity, message, ack);
        } catch (Exception e) {
            log.error("consumeCaseAppMigrationFalse function with message = {}", e.getMessage(), e);
            MDC.clear();
            throw e;
        }
    }

    private void delayAndProcessSaveMigration(ProcessSaveAppMigrationEntity saveAppMigrationEntity, String message,
                                              Acknowledgment ack) {
        long startProcessAt = System.currentTimeMillis();
        log.info("DelayAndProcessSaveMigration.Message=[{}], startProcessAt={}, formatted={}, delay={}ms", message,
                startProcessAt, Instant.ofEpochMilli(startProcessAt).atZone(ZoneId.systemDefault()));
        try {
            this.insertRawAndMartMigration(saveAppMigrationEntity);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error after delay = {}", e.getMessage(), e);
            processSaveAppMigrationService.setProcessAppError(saveAppMigrationEntity, e, CaseProcess.ON_RETRY.getCode());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    private ProcessSaveAppMigrationEntity checkAppMigrationExisted(String appId, String message, String versionDataOds) {
        List<ProcessSaveAppMigrationEntity> caseProcessAppEntities = processSaveAppMigrationService.getProcessStepAppMigrationByAppId(appId);
        if (CollectionUtils.isNotEmpty(caseProcessAppEntities) && "3".equals(versionDataOds)) {
            log.info("CaseByCase.ProcessSaveAppMigrationEntity with size=[{}]", caseProcessAppEntities.size());
            return null;

        }
        ProcessSaveAppMigrationEntity saveAppMigrationEntity = processSaveAppMigrationService.buildProcessSaveAppMigration(appId, message,
                CaseProcess.RUNNING.getCode(), CaseProcess.FETCH_DATA_ODS.getCode(), versionDataOds);
        log.info("CaseByCase saveAppMigrationEntityId=[{}]", saveAppMigrationEntity.getId());
        return saveAppMigrationEntity;
    }

    private void insertRawAndMartMigration(ProcessSaveAppMigrationEntity saveAppMigrationEntity) {
        OdsRequestPayload requestPayLoad = processSaveAppMigrationService.fetchDataOdsMigration(saveAppMigrationEntity);
        if (ObjectUtils.isEmpty(requestPayLoad)) return;

        if (processSaveAppMigrationService.insertCbcRawBySaveAppMigration(saveAppMigrationEntity, requestPayLoad)) {
            cbcMartJdbcRepository.insertRawToMARTByAppMigrationASync(saveAppMigrationEntity, MDC.get(AppContant.REQUEST_ID));
        }
        log.info("CaseByCase insertRawAndMartMigration.[END] saveAppMigrationEntityId=[{}].", saveAppMigrationEntity.getId());
    }

}
