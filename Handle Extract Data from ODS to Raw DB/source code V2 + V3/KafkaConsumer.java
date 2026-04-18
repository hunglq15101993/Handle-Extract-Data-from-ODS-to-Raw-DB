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
import com.msb.stp.leadmanagement.entities.ProcessSaveAppEntity;
import com.msb.stp.leadmanagement.oracle.cbcMart.repository.CbcMartJdbcRepository;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.OdsRequestPayload;
import com.msb.stp.leadmanagement.services.ProcessSaveAppService;
import com.msb.stp.leadmanagement.utils.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProcessSaveAppService processSaveAppService;
    private final CbcMartJdbcRepository cbcMartJdbcRepository;

    /**
     * Apply for source code V2 both 01/04 ->16/04
     * Apply for source code V3 from after 16/04
     * @param message
     * @param ack
     */
    @KafkaListener(
            autoStartup = "${kafka.case-by-case.success.startup}",
            topics = "${kafka.case-by-case.success-raw-new-de.topic}",
            containerFactory = "kafkaListenerContainerFactoryCaseSuccess",
            groupId = "${kafka.case-by-case.success-raw-new-de.group-id}")
    public void consumeCaseSuccess(String message, Acknowledgment ack) {

        try {
            log.info("CaseSuccess.Receive Message=[{}]", message);
            RequestSTPSuccess requestSuccess = JsonUtil.readValue(message, RequestSTPSuccess.class);
            ApplicationSTP application = requestSuccess.getApplication();
            if (ObjectUtils.isEmpty(application)
                    || StringUtils.isEmpty(application.getApplicationDeRefNumber())) {
                log.error("CaseSuccess.appId IS EMPTY message=[{}]", message);
                return;
            }
            String applicationDeRefNumber = application.getApplicationDeRefNumber();
            String requestID = UUID.randomUUID().toString();
            String traceId = applicationDeRefNumber + "-" + requestID;
            MDC.put(requestID, traceId);
            String versionDataRaw = application.getVersionDataRaw();
            ProcessSaveAppEntity saveAppEntity = this.checkAppExisted(applicationDeRefNumber, message, versionDataRaw);
            if (ObjectUtils.isNotEmpty(saveAppEntity))
                this.delayAndProcessSave(saveAppEntity, message, ack);
        } catch (Exception e) {
            log.error("consumeCaseSuccess function with message = {}", e.getMessage(), e);
            MDC.clear();
        }

    }

    @KafkaListener(
            autoStartup = "${kafka.case-by-case.error.startup}",
            topics = "${kafka.case-by-case.error-raw-new-de.topic}",
            containerFactory = "kafkaListenerContainerFactoryCaseError",
            groupId = "${kafka.case-by-case.error-raw-new-de.group-id}")
    public void consumeCaseFalse(String message, Acknowledgment ack) {

        try {
            log.info("CaseFalse.Receive Message=[{}]", message);
            String requestIDError = UUID.randomUUID().toString();

            RequestSTPError requestError = JsonUtil.readValue(message, RequestSTPError.class);
            ApplicationSTPInfo applicationInfo = requestError.getApplication();
            if (ObjectUtils.isEmpty(applicationInfo)
                    || ObjectUtils.isEmpty(applicationInfo.getApplicationInfo())
                    || StringUtils.isEmpty(applicationInfo.getApplicationInfo().getApplicationDeRefNumber())) {
                log.error("CaseFalse.appId IS EMPTY message=[{}]", message);
                return;
            }
            String applicationDeRefNumber = applicationInfo.getApplicationInfo().getApplicationDeRefNumber();
            String traceId = applicationDeRefNumber + "-" + requestIDError;
            String versionDataRaw = applicationInfo.getApplicationInfo().getVersionDataRaw();

            MDC.put(requestIDError, traceId);
            ProcessSaveAppEntity saveAppEntity = this.checkAppExisted(applicationDeRefNumber, message, versionDataRaw);
            if (ObjectUtils.isNotEmpty(saveAppEntity))
                this.delayAndProcessSave(saveAppEntity, message, ack);
		} catch (Exception e) {
			log.error("consumeCaseFalse function with message = {}", e.getMessage(), e);
			MDC.clear();
            throw e;
        }
    }

    private void delayAndProcessSave(ProcessSaveAppEntity saveAppEntity, String message,
                                     Acknowledgment ack) {
        long startProcessAt = System.currentTimeMillis();
        log.info("ProcessDelay.Message=[{}], startProcessAt={}, formatted={}, delay={}ms", message,
                startProcessAt, Instant.ofEpochMilli(startProcessAt).atZone(ZoneId.systemDefault()));
        try {
            this.insertRawAndMartNewDe(saveAppEntity);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error after delay = {}", e.getMessage(), e);
            processSaveAppService.setProcessAppError(saveAppEntity, e, CaseProcess.ON_RETRY.getCode());
            throw e;
        } finally {
            MDC.clear();
        }
    }

    private ProcessSaveAppEntity checkAppExisted(String appId, String message, String versionDataOds) {
        List<ProcessSaveAppEntity> caseProcessAppEntities = processSaveAppService.getProcessStepAppByAppId(appId);
        if (CollectionUtils.isNotEmpty(caseProcessAppEntities) && "3".equals(versionDataOds)) {
            log.info("CaseByCase.GetCaseProcessAppEntity with size=[{}]", caseProcessAppEntities.size());
            return null;
        }
        ProcessSaveAppEntity saveAppEntity = processSaveAppService.buildProcessSaveApp(appId, message,
                CaseProcess.RUNNING.getCode(), CaseProcess.FETCH_DATA_ODS.getCode(), versionDataOds);
        log.info("CaseByCase saveAppEntityId=[{}]", saveAppEntity.getId());
        return saveAppEntity;
    }

    private void insertRawAndMartNewDe(ProcessSaveAppEntity saveAppEntity) {
        OdsRequestPayload requestPayLoad = processSaveAppService.fetchDataOds(saveAppEntity);
        if (ObjectUtils.isEmpty(requestPayLoad)) return;

        if (processSaveAppService.insertCbcRaw(saveAppEntity, requestPayLoad)) {
            cbcMartJdbcRepository.insertRawToMARTASync(saveAppEntity, MDC.get(AppContant.REQUEST_ID));
        }
        log.info("CaseByCase insertRawAndMartNewDe .[END] saveAppEntityId=[{}].", saveAppEntity.getId());
    }

}
