package com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.impl;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsApplicationEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsApplicationStateEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsIntegrationEventLogEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsModelExecutionLogEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsPreviousPolicyDecisionEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.OdsApplicationRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.OdsApplicationStateRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.OdsIntegrationEventLogRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.OdsModelExecutionLogRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.OdsPreviousPolicyDecisionRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.OdsCommonV1Service;
import com.msb.stp.leadmanagement.oracle.ods.entities.ApplicationEntity;
import com.msb.stp.leadmanagement.oracle.ods.entities.ApplicationStateEntity;
import com.msb.stp.leadmanagement.oracle.ods.entities.IntegrationEventLogEntity;
import com.msb.stp.leadmanagement.oracle.ods.entities.ModelExecutionLogEntity;
import com.msb.stp.leadmanagement.oracle.ods.entities.PreviousPolicyDecisionEntity;
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
public class OdsCommonV1ServiceImpl implements OdsCommonV1Service {

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
    public String getRequestPayloadOds(String appId) {
        ApplicationEntity applicationEntity = applicationService.getApplication(appId);
        OdsApplicationEntity odsApplicationEntity = new OdsApplicationEntity();
        modelMapper.map(applicationEntity, odsApplicationEntity);
        odsApplicationRepository.save(odsApplicationEntity);
        return applicationEntity.getRequestPayload();
    }

    @Override
    public String getRequestPayloadEventLog(String appId) {
        String requestPayload = null;
        List<IntegrationEventLogEntity> integrationEventLogEntities = integrationEventLogService.
                getIntegrationEventLog(appId);
        if (CollectionUtils.isNotEmpty(integrationEventLogEntities)) {
            List<OdsIntegrationEventLogEntity> odsIntegrationEventLogEntities = new ArrayList<>();
            for (IntegrationEventLogEntity eventLogEntity : integrationEventLogEntities) {
                if (ObjectUtils.isEmpty(eventLogEntity)) continue;
                OdsIntegrationEventLogEntity odsIntegrationEventLogEntity = new OdsIntegrationEventLogEntity();
                modelMapper.map(eventLogEntity, odsIntegrationEventLogEntity);
                odsIntegrationEventLogEntities.add(odsIntegrationEventLogEntity);
                if ("DMPS-BEFORE-BLAZE".equalsIgnoreCase(eventLogEntity.getSystem())) {
                    requestPayload = eventLogEntity.getRequestPayload();
                }
            }
            if (CollectionUtils.isNotEmpty(odsIntegrationEventLogEntities)) {
                odsIntegrationEventLogRepository.saveAll(odsIntegrationEventLogEntities);
            }
        }
        return requestPayload;
    }

    @Override
    public void moveModelExecutionLog(String appId) {

        /// move record ApplicationState
        ApplicationStateEntity stateEntity = applicationStateService.getApplicationState(appId);
        OdsApplicationStateEntity odsApplicationStateEntity = new OdsApplicationStateEntity();
        modelMapper.map(stateEntity, odsApplicationStateEntity);
        odsApplicationStateRepository.save(odsApplicationStateEntity);

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
