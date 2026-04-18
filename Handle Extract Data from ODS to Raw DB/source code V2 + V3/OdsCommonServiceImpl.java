package com.msb.stp.leadmanagement.oracle.cbcRaw.service.impl;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.*;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.*;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.OdsCommonService;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.OdsRequestPayload;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.RequestPayLoadDto;
import com.msb.stp.leadmanagement.oracle.ods.entities.*;
import com.msb.stp.leadmanagement.oracle.ods.repository.PreviousPolicyDecisionRepository;
import com.msb.stp.leadmanagement.oracle.ods.service.ApplicationService;
import com.msb.stp.leadmanagement.oracle.ods.service.ApplicationStateService;
import com.msb.stp.leadmanagement.oracle.ods.service.IntegrationEventLogService;
import com.msb.stp.leadmanagement.oracle.ods.service.ModelExecutionLogService;
import com.msb.stp.leadmanagement.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OdsCommonServiceImpl implements OdsCommonService {

    private final ApplicationService applicationService;
    private final ApplicationStateService applicationStateService;
    private final IntegrationEventLogService integrationEventLogService;
    private final ModelExecutionLogService modelExecutionLogService;
    private final PreviousPolicyDecisionRepository previousPolicyDecisionRepository;
    private final ModelMapper modelMapper;
    private final OdsApplicationRepository odsApplicationRepository;
    private final OdsApplicationStateRepository odsApplicationStateRepository;
    private final OdsIntegrationEventLogRepository odsIntegrationEventLogRepository;
    private final OdsModelExecutionLogRepository odsModelExecutionLogRepository;
    private final OdsPreviousPolicyDecisionRepository odsPreviousPolicyDecisionRepository;

    @Override
    public void checkStatusAndMoveData(RequestPayLoadDto requestPayLoadDto) {
        String appId = requestPayLoadDto.getAppid();
        /// check applicationState IS FAILED OR COMPLETED
        ApplicationStateEntity stateEntity = applicationStateService.getApplicationState(appId);

        /// check requestPayLoad in ApplicationEntity with application.callType = DP_IncomeAndLimit
        ApplicationEntity applicationEntity = applicationService.getApplication(appId);
        OdsRequestPayload requestPayload = JsonUtil.readValue(applicationEntity.getRequestPayload(), OdsRequestPayload.class);
        requestPayLoadDto.setRequestPayLoad(applicationEntity.getRequestPayload());
        if (ObjectUtils.isNotEmpty(requestPayload)
                && "DP_IncomeAndLimit".equalsIgnoreCase(requestPayload.getCallType())) {
            requestPayLoadDto.setHasIncomeAndLimit(true);
        }

        /// vào integration lấy thông tin system=’Blaze_Model’
        /// callType ='DP_ModelExecution' và blazeDMPSIterationCount lớn nhất trong response
        List<IntegrationEventLogEntity> integrationEventLogEntities = integrationEventLogService.
                getIntegrationEventLog(appId);

        int blazeDMPSIterationCount = 0;
        List<OdsIntegrationEventLogEntity> odsIntegrationEventLogEntities = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(integrationEventLogEntities)) {
            for (IntegrationEventLogEntity eventLogEntity : integrationEventLogEntities) {
                if (ObjectUtils.isEmpty(eventLogEntity)) continue;
                if ("Blaze_Model".equalsIgnoreCase(eventLogEntity.getSystem())
                        && "DP_ModelExecution".equalsIgnoreCase(eventLogEntity.getCallType())) {
                    OdsRequestPayload reqByModel = JsonUtil.readValue(eventLogEntity.getResponsePayload(), OdsRequestPayload.class);
                    if ( blazeDMPSIterationCount <= reqByModel.getBlazeDMPSIterationCount()) {
                        blazeDMPSIterationCount = reqByModel.getBlazeDMPSIterationCount();
                        requestPayLoadDto.setReqByModel(eventLogEntity.getResponsePayload());
                    }
                }
                OdsIntegrationEventLogEntity odsIntegrationEventLogEntity = new OdsIntegrationEventLogEntity();
                modelMapper.map(eventLogEntity, odsIntegrationEventLogEntity);
                odsIntegrationEventLogEntities.add(odsIntegrationEventLogEntity);
                if ("DMPS-BEFORE-BLAZE".equalsIgnoreCase(eventLogEntity.getSystem())) {
                    requestPayLoadDto.setReqByBeforeDMPS(eventLogEntity.getRequestPayload());
                }
            }
        }

        if (requestPayLoadDto.isHasIncomeAndLimit()
                && ObjectUtils.isEmpty(requestPayLoadDto.getReqByModel())) {
            log.error("RequestPayLoadByModel IS INVALID");
            throw new RuntimeException("RequestPayLoadByModel IS INVALID");
        }

        /// moveDB
        OdsApplicationEntity odsApplicationEntity = new OdsApplicationEntity();
        modelMapper.map(applicationEntity, odsApplicationEntity);
        odsApplicationRepository.save(odsApplicationEntity);

        OdsApplicationStateEntity odsApplicationStateEntity = new OdsApplicationStateEntity();
        modelMapper.map(stateEntity, odsApplicationStateEntity);
        odsApplicationStateRepository.save(odsApplicationStateEntity);

        if (CollectionUtils.isNotEmpty(odsIntegrationEventLogEntities)) {
            odsIntegrationEventLogRepository.saveAll(odsIntegrationEventLogEntities);
        }

        /// move records ModelExecutionLog
        List<ModelExecutionLogEntity> modelExecutionLogEntities = modelExecutionLogService.
                getModelExecutionLog(appId);
        if (CollectionUtils.isNotEmpty(modelExecutionLogEntities)) {
            List<OdsModelExecutionLogEntity> odsModelExecutionLogEntities = new ArrayList<>();
            for (ModelExecutionLogEntity modelExecutionLogEntity : modelExecutionLogEntities) {
                if (ObjectUtils.isEmpty(modelExecutionLogEntity)) continue;
                OdsModelExecutionLogEntity odsModelExecutionLogEntity = new OdsModelExecutionLogEntity();
                modelMapper.map(modelExecutionLogEntity, odsModelExecutionLogEntity);
                odsModelExecutionLogEntities.add(odsModelExecutionLogEntity);
            }
            if (CollectionUtils.isNotEmpty(odsModelExecutionLogEntities)) {
                odsModelExecutionLogRepository.saveAll(odsModelExecutionLogEntities);
            }
        }

        /// move record PreviousPolicyDecision
        List<PreviousPolicyDecisionEntity> prePolicyDecisionEntities = previousPolicyDecisionRepository
                .findAllByApplicationDeRefNumber(appId);
        if (CollectionUtils.isNotEmpty(prePolicyDecisionEntities)) {
            List<OdsPreviousPolicyDecisionEntity> odsPreviousPolicyDecisionEntities = new ArrayList<>();
            for (PreviousPolicyDecisionEntity policyDecisionEntity : prePolicyDecisionEntities) {
                if (ObjectUtils.isEmpty(policyDecisionEntity)) continue;
                OdsPreviousPolicyDecisionEntity odsPrePolicyDecisionEntity = new OdsPreviousPolicyDecisionEntity();
                modelMapper.map(policyDecisionEntity, odsPrePolicyDecisionEntity);
                odsPreviousPolicyDecisionEntities.add(odsPrePolicyDecisionEntity);
            }
            if (CollectionUtils.isNotEmpty(odsPreviousPolicyDecisionEntities)) {
                odsPreviousPolicyDecisionRepository.saveAll(odsPreviousPolicyDecisionEntities);
            }
        }

    }
}
