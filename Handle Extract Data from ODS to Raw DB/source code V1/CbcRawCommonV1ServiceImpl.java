package com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppEntity;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppMigrationEntity;
import com.msb.stp.leadmanagement.exceptions.BusinessException;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcA05Entity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcAccDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcAddressEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcAmlEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcApplicantEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcApplicantVarEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcApplicationEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcApplicationVarEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcC06Entity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcCardContractEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcCardDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcCasaInfoEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcCicDerivedVarEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcCicEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcErrorEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcExistsIdentifyEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcFraudRuleBankingEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcFraudRuleCapitalEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcFraudRuleRbApprovalEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcIdentificationEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcIncomeDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcLeadDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcLeadModelInfoEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcLoanContractEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcLoanDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcModelInfoEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcModelResultEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcModelScoringConstantEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcModelTypeQuesGroupEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcModelVarEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcOdContractEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcOdDetailEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcOfferEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcOpRiskEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcOverdueAccountEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcPrevPolicyDecisionEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcProductEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcReferenceInfoEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcRuleAuditDataEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcSubModelAffectResultEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcSubModelCriterionMapEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcSubModelTypeRelEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcThirdSystemScoreEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcUwCriteriaEntity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcWay4Entity;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcA05Repository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcAccDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcAddressRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcAmlRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcApplicantRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcApplicantVarRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcApplicationRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcApplicationVarRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcC06Repository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcCardContractRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcCardDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcCasaInfoRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcCicDerivedVarRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcCicRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcErrorRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcExistsIdentifyRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcFraudRuleBankingRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcFraudRuleCapitalRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcFraudRuleRbApprovalRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcIdentificationRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcIncomeDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcLeadDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcLeadModelInfoRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcLoanContractRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcLoanDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcModelInfoRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcModelResultRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcModelScoringConstantRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcModelTypeQuesGroupRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcModelVarRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcOdContractRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcOdDetailRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcOfferRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcOpRiskRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcOverdueAccountRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcPrevPolicyDecisionRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcProductRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcReferenceInfoRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcRuleAuditDataRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcSubModelAffectResultRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcSubModelCriterionMapRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcSubModelTypeRelRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcThirdSystemScoreRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcUwCriteriaRepository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.DeCbcWay4Repository;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.v1.CbcRawCommonV1Service;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.Applicant;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ApplicantDecision;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.Application;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ApplicationDecision;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ApplicationInfo;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.B11TReport;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.CalculatedVariable;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.CicData;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ExistingCardContractDetail;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ExistingLoanContractDetail;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ExistingODContractDetail;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.F5OrF16Report;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.F6Report;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.FraudRuleData;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.Identity;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.LeadDetail;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ModelInfo;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.OdsRequestPayload;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.OpRiskData;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.PreviousPolicyDecision;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.ProductDecision;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.RequestedProduct;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.UwCriteria;
import com.msb.stp.leadmanagement.utils.BeanWrapperUtil;
import com.msb.stp.leadmanagement.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;


@Service
@Slf4j
@RequiredArgsConstructor
public class CbcRawCommonV1ServiceImpl implements CbcRawCommonV1Service {

    private final ModelMapper modelMapper;
    private final DeCbcIdentificationRepository identificationRepository;
    private final DeCbcApplicationRepository deCbcApplicationRepository;
    private final DeCbcProductRepository deCbcProductRepository;
    private final DeCbcApplicantRepository deCbcApplicantRepository;
    private final DeCbcAddressRepository deCbcAddressRepository;
    private final DeCbcReferenceInfoRepository deCbcReferenceInfoRepository;
    private final DeCbcIncomeDetailRepository deCbcIncomeDetailRepository;
    private final DeCbcC06Repository deCbcC06Repository;
    private final DeCbcWay4Repository deCbcWay4Repository;
    private final DeCbcOpRiskRepository deCbcOpRiskRepository;
    private final DeCbcA05Repository deCbcA05Repository;
    private final DeCbcAmlRepository deCbcAmlRepository;
    private final DeCbcCicRepository deCbcCicRepository;
    private final DeCbcCicDerivedVarRepository deCbcCicDerivedVarRepository;
    private final DeCbcLoanContractRepository deCbcLoanContractRepository;
    private final DeCbcLoanDetailRepository deCbcLoanDetailRepository;
    private final DeCbcOdContractRepository deCbcOdContractRepository;
    private final DeCbcOdDetailRepository deCbcOdDetailRepository;
    private final DeCbcCardContractRepository cbcCardContractRepository;
    private final DeCbcCardDetailRepository deCbcCardDetailRepository;
    private final DeCbcAccDetailRepository deCbcAccDetailRepository;
    private final DeCbcExistsIdentifyRepository identifyRepository;
    private final DeCbcThirdSystemScoreRepository systemScoreRepository;
    private final DeCbcOverdueAccountRepository overdueAccountRepository;
    private final DeCbcFraudRuleBankingRepository fraudRuleBankingRepository;
    private final DeCbcFraudRuleRbApprovalRepository rbApprovalRepository;
    private final DeCbcFraudRuleCapitalRepository capitalRepository;
    private final DeCbcModelInfoRepository modelInfoRepository;
    private final DeCbcModelVarRepository modelVarRepository;
    private final DeCbcModelTypeQuesGroupRepository typeQuesGroupRepository;
    private final DeCbcModelScoringConstantRepository scoringConstantRepository;
    private final DeCbcSubModelTypeRelRepository modelTypeRelRepository;
    private final DeCbcSubModelCriterionMapRepository criterionMapRepository;
    private final DeCbcSubModelAffectResultRepository affectResultRepository;
    private final DeCbcModelResultRepository modelResultRepository;
    private final DeCbcLeadDetailRepository leadDetailRepository;
    private final DeCbcCasaInfoRepository casaInfoRepository;
    private final DeCbcLeadModelInfoRepository leadModelInfoRepository;
    private final DeCbcRuleAuditDataRepository ruleAuditDataRepository;
    private final DeCbcErrorRepository errorRepository;
    private final DeCbcOfferRepository offerRepository;
    private final DeCbcUwCriteriaRepository uwCriteriaRepository;
    private final DeCbcPrevPolicyDecisionRepository policyDecisionRepository;
    private final DeCbcApplicationVarRepository applicationVarRepository;
    private final DeCbcApplicantVarRepository applicantVarRepository;

    @Override
    @Transactional("oracleTransactionManagerDeCbcRaw")
    public void insertCbcRawByMigration(ProcessSaveAppMigrationEntity saveAppEntity, com.msb.stp.leadmanagement.oracle.ods.dto.request.v1.OdsRequestPayload requestPayload) {
        String appId = saveAppEntity.getApplicationId();
        if (ObjectUtils.isEmpty(requestPayload)) {
            log.error("CaseByCase.appId=[{}] RequestPayload IS EMPTY", appId);
            saveAppEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
            throw new BusinessException("RequestPayload IS EMPTY");
        }
        Application application = requestPayload.getApplication();
        if (ObjectUtils.isEmpty(application)) {
            log.error("CaseByCase.appId=[{}] Application IS EMPTY", appId);
            saveAppEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
            throw new BusinessException("Application IS EMPTY");
        }
        DeCbcApplicationEntity deCbcApplicationEntity = deCbcApplicationRepository.findByApplicationId(appId);
        if (ObjectUtils.isNotEmpty(deCbcApplicationEntity)) {
            log.error("CaseByCase.appId=[{}] IS EXISTED IN CBC RAW", appId);
            saveAppEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
            throw new BusinessException(String.format("appId=[%s] IS EXISTED IN CBC RAW", appId));
        }

        /// saveApplication
        List<DeCbcApplicationVarEntity> applicationVarEntities = new ArrayList<>();
        List<DeCbcUwCriteriaEntity> uwCriteriaEntities = new ArrayList<>();
        this.saveApplication(appId, application, uwCriteriaEntities, applicationVarEntities);
        /// saveProduct
        this.saveRequestProducts(appId, application.getRequestedProducts(), uwCriteriaEntities, applicationVarEntities);
        if (CollectionUtils.isNotEmpty(uwCriteriaEntities))
            uwCriteriaRepository.saveAll(uwCriteriaEntities);
        if (CollectionUtils.isNotEmpty(applicationVarEntities))
            applicationVarRepository.saveAll(applicationVarEntities);
        /// saveApplicant
        this.saveApplicants(appId, saveAppEntity.getEventRequestPayload(), application.getApplicants());
        /// saveErrorDE
        List<DeCbcErrorEntity> errorEntities = new ArrayList<>();
        this.mapAndCollectEntities(requestPayload.getError(), DeCbcErrorEntity.class,
                appId, errorEntities, (entity, id) -> entity.setApplicationId(appId));
        if (CollectionUtils.isNotEmpty(errorEntities)) {
            errorRepository.saveAll(errorEntities);
        }
    }

    private void saveApplication(String appId, Application application,
                                 List<DeCbcUwCriteriaEntity> uwCriteriaEntities,
                                 List<DeCbcApplicationVarEntity> applicationVarEntities) {
        ApplicationInfo appInfo = application.getApplicationInfo();
        if (ObjectUtils.isEmpty(appInfo)) appInfo = new ApplicationInfo();
        DeCbcApplicationEntity appEntity = new DeCbcApplicationEntity();
        modelMapper.map(appInfo, appEntity);

        if (ObjectUtils.isNotEmpty(application.getApplicationDecision())) {
            ApplicationDecision applicationDecision = application.getApplicationDecision();
            appEntity.setRecommendedDecision(applicationDecision.getRecommendedDecision());
            /// saveRuleAuditData
            List<DeCbcRuleAuditDataEntity> ruleAuditDataEntities = new ArrayList<>();
            this.mapAndCollectEntities(applicationDecision.getRuleAuditData(), DeCbcRuleAuditDataEntity.class,
                    appId, ruleAuditDataEntities, (entity, id) -> entity.setApplicationId(appId));
            if (CollectionUtils.isNotEmpty(ruleAuditDataEntities)) {
                ruleAuditDataRepository.saveAll(ruleAuditDataEntities);
            }

            /// buildApplicationVar
            if (CollectionUtils.isNotEmpty(applicationDecision.getCalculatedVariables())) {
                for (CalculatedVariable calculatedVariable : applicationDecision.getCalculatedVariables()) {
                    if (ObjectUtils.isEmpty(calculatedVariable)) continue;
                    DeCbcApplicationVarEntity applicationVarEntity = new DeCbcApplicationVarEntity();
                    modelMapper.map(calculatedVariable, applicationVarEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(applicationVarEntity)) continue;
                    applicationVarEntity.setApplicationId(appId);
                    applicationVarEntity.setVarGroup("Application");
                    applicationVarEntities.add(applicationVarEntity);
                }
            }
        }

        if (!BeanWrapperUtil.isAllFieldsNull(appEntity)){
            appEntity.setApplicationId(appId);
            deCbcApplicationRepository.save(appEntity);
        }

        /// savePreviousPolicyDecision
        if (CollectionUtils.isEmpty(appInfo.getPreviousPolicyDecision())) return;
        for (PreviousPolicyDecision policyDecision : appInfo.getPreviousPolicyDecision()) {
            if (ObjectUtils.isEmpty(policyDecision)) continue;
            DeCbcPrevPolicyDecisionEntity policyDecisionEntity = new DeCbcPrevPolicyDecisionEntity();
            modelMapper.map(policyDecision, policyDecisionEntity);
            if (BeanWrapperUtil.isAllFieldsNull(policyDecisionEntity)
                    && CollectionUtils.isEmpty(policyDecision.getUwCriterias())) continue;
            policyDecisionEntity.setApplicationId(appId);
            Long policyId = policyDecisionRepository.save(policyDecisionEntity).getId();
            /// buildUwCriteria
            for (UwCriteria uwCriteria : policyDecision.getUwCriterias()){
                if (ObjectUtils.isEmpty(uwCriteria)) continue;
                DeCbcUwCriteriaEntity uwCriteriaEntity = new DeCbcUwCriteriaEntity();
                modelMapper.map(uwCriteria, uwCriteriaEntity);
                if (BeanWrapperUtil.isAllFieldsNull(uwCriteriaEntity)) continue;
                uwCriteriaEntity.setPrevPolicyDecisionId(policyId);
                uwCriteriaEntity.setGroup("PREV_POLICY_DECISION");
                uwCriteriaEntities.add(uwCriteriaEntity);
            }
        }
    }

    private void saveRequestProducts(String appId, List<RequestedProduct> requestedProduct,
                                     List<DeCbcUwCriteriaEntity> uwCriteriaEntities,
                                     List<DeCbcApplicationVarEntity> applicationVarEntities) {
        if (CollectionUtils.isEmpty(requestedProduct)) return;
        List<DeCbcOfferEntity> offerEntities = new ArrayList<>();
        for (RequestedProduct product : requestedProduct) {
            if (ObjectUtils.isEmpty(product)) continue;
            if (ObjectUtils.isEmpty(product.getProposedAmountOrLimit()))
                product.setProposedAmountOrLimit(product.getLoanProposeAmount());

            DeCbcProductEntity productEntity = new DeCbcProductEntity();
            modelMapper.map(product, productEntity);
            ProductDecision productDecision = product.getProductDecision();

            if (BeanWrapperUtil.isAllFieldsNull(productEntity)
                    && ObjectUtils.isEmpty(productDecision)) return;
            productEntity.setApplicationId(appId);

            if (ObjectUtils.isNotEmpty(productDecision)) {
                modelMapper.map(productDecision, productEntity);
            }
            Long productId = deCbcProductRepository.save(productEntity).getId();

            if (ObjectUtils.isNotEmpty(productDecision)) {
                /// buildOffer
                this.mapAndCollectEntities(Collections.singletonList(productDecision.getOffers()), DeCbcOfferEntity.class,
                        productId, offerEntities, (entity, id) -> entity.setProductId(productId));
                /// buildUwCriteria
                for (UwCriteria uwCriteria : productDecision.getUwCriterias()){
                    if (ObjectUtils.isEmpty(uwCriteria)) continue;
                    DeCbcUwCriteriaEntity uwCriteriaEntity = new DeCbcUwCriteriaEntity();
                    modelMapper.map(uwCriteria, uwCriteriaEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(uwCriteriaEntity)) continue;
                    uwCriteriaEntity.setProductId(productId);
                    uwCriteriaEntity.setGroup("PRODUCT");
                    uwCriteriaEntities.add(uwCriteriaEntity);
                }
                /// buildApplicationVar
                for (CalculatedVariable calculatedVariable : productDecision.getCalculatedVariables()) {
                    if (ObjectUtils.isEmpty(calculatedVariable)) continue;
                    DeCbcApplicationVarEntity applicationVarEntity = new DeCbcApplicationVarEntity();
                    modelMapper.map(calculatedVariable, applicationVarEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(applicationVarEntity)) continue;
                    applicationVarEntity.setApplicationId(appId);
                    applicationVarEntity.setProductId(productId);
                    applicationVarEntity.setVarGroup("Product");
                    applicationVarEntities.add(applicationVarEntity);
                }
            }
        }

        if (CollectionUtils.isNotEmpty(offerEntities)) {
            offerRepository.saveAll(offerEntities);
        }
    }

    private void saveApplicants(String appId, String eventRequestPayload,
                                List<Applicant> applicants) {
        if (CollectionUtils.isEmpty(applicants)) return;

        List<DeCbcIdentificationEntity> identificationEntities = new ArrayList<>();
        List<DeCbcAddressEntity> addressEntities = new ArrayList<>();
        List<DeCbcReferenceInfoEntity> referenceInfoEntities = new ArrayList<>();
        List<DeCbcIncomeDetailEntity> incomeDetailEntities = new ArrayList<>();
        List<DeCbcC06Entity> c06Entities = new ArrayList<>();
        List<DeCbcWay4Entity> way4Entities = new ArrayList<>();
        List<DeCbcOpRiskEntity> opRiskEntities = new ArrayList<>();
        List<DeCbcA05Entity> a05Entities = new ArrayList<>();
        List<DeCbcAmlEntity> amlEntities = new ArrayList<>();
        List<DeCbcCicDerivedVarEntity> cbcCicDerivedVarEntities = new ArrayList<>();
        List<DeCbcLoanDetailEntity> loanDetailEntities = new ArrayList<>();
        List<DeCbcOdDetailEntity> odDetailEntities = new ArrayList<>();
        List<DeCbcCardDetailEntity> cardDetailEntities = new ArrayList<>();
        List<DeCbcAccDetailEntity> deCbcAccDetailEntities = new ArrayList<>();
        List<DeCbcExistsIdentifyEntity> identifyEntities = new ArrayList<>();
        List<DeCbcThirdSystemScoreEntity> systemScoreEntities = new ArrayList<>();
        List<DeCbcOverdueAccountEntity>  overdueAccEntities= new ArrayList<>();
        List<DeCbcFraudRuleBankingEntity> fraudRuleBankingEntities = new ArrayList<>();
        List<DeCbcFraudRuleRbApprovalEntity> rbApprovalEntities = new ArrayList<>();
        List<DeCbcFraudRuleCapitalEntity> capitalEntities = new ArrayList<>();
        List<DeCbcModelVarEntity> modelVarEntities = new ArrayList<>();
        List<DeCbcModelTypeQuesGroupEntity> typeQuesGroupEntities = new ArrayList<>();
        List<DeCbcModelScoringConstantEntity> scoringConstantEntities = new ArrayList<>();
        List<DeCbcSubModelTypeRelEntity> modelTypeRelEntities = new ArrayList<>();
        List<DeCbcSubModelCriterionMapEntity> criterionMapEntities = new ArrayList<>();
        List<DeCbcSubModelAffectResultEntity> affectResultEntities = new ArrayList<>();
        List<DeCbcModelResultEntity> modelResultEntities = new ArrayList<>();
        List<DeCbcLeadDetailEntity> leadDetailEntities = new ArrayList<>();
        List<DeCbcCasaInfoEntity> casaInfoEntities = new ArrayList<>();
        List<DeCbcLeadModelInfoEntity> leadModelInfoEntities = new ArrayList<>();
        List<DeCbcApplicantVarEntity> applicantVarEntities = new ArrayList<>();

        for (Applicant applicant : applicants){
            if (ObjectUtils.isEmpty(applicant)) continue;
            /// saveApplicant
            DeCbcApplicantEntity applicantEntity = new DeCbcApplicantEntity();
            modelMapper.map(applicant, applicantEntity);
            applicantEntity.setApplicationId(appId);
            ApplicantDecision applicantDecision = applicant.getApplicantDecision();
            if (ObjectUtils.isNotEmpty(applicantDecision))
                modelMapper.map(applicantDecision, applicantEntity);
            Long applicantId = deCbcApplicantRepository.save(applicantEntity).getId();

            /// buildApplicantVar
            if (ObjectUtils.isNotEmpty(applicantDecision)
                    && CollectionUtils.isNotEmpty(applicantDecision.getCalculatedVariables())){
                for (CalculatedVariable calculatedVariable : applicantDecision.getCalculatedVariables()) {
                    if (ObjectUtils.isEmpty(calculatedVariable)) continue;
                    DeCbcApplicantVarEntity applicantVarEntity = new DeCbcApplicantVarEntity();
                    modelMapper.map(calculatedVariable, applicantVarEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(applicantVarEntity)) continue;
                    applicantVarEntity.setApplicantReferId(applicantId);
                    applicantVarEntity.setVarGroup("CALCULATE");
                    applicantVarEntities.add(applicantVarEntity);
                }
            }
            /// buildIdentities
            this.mapAndCollectEntities(applicant.getIdentities(), DeCbcIdentificationEntity.class,
                    applicantId, identificationEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildAddress
            this.mapAndCollectEntities(applicant.getAddress(), DeCbcAddressEntity.class,
                    applicantId, addressEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            if (CollectionUtils.isEmpty(addressEntities)) {
                if (ObjectUtils.isNotEmpty(applicant.getPermanentAddress())) {
                    applicant.getPermanentAddress().setAddressType("permanent");
                    this.mapAndCollectEntities(Collections.singletonList(applicant.getPermanentAddress()), DeCbcAddressEntity.class,
                            applicantId, addressEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                }
                if (ObjectUtils.isNotEmpty(applicant.getCurrentAddress())) {
                    applicant.getCurrentAddress().setAddressType("current");
                    this.mapAndCollectEntities(Collections.singletonList(applicant.getCurrentAddress()), DeCbcAddressEntity.class,
                            applicantId, addressEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                }
            }
            /// buildReferenceInfo
            this.mapAndCollectEntities(applicant.getReferenceInfo(), DeCbcReferenceInfoEntity.class,
                    applicantId, referenceInfoEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildC06Data
            this.mapAndCollectEntities(applicant.getC06Data(), DeCbcC06Entity.class,
                    applicantId, c06Entities, (entity, id) -> entity.setApplicantReferId(applicantId));
            String identificationNumber = this.getIdentificationNumber(applicant.getIdentities());
            /// update object address, referenceInfo và c06Data in integrationLoaEvent
            OdsRequestPayload odsRequestPayload = this.parseRequestPayloadByEvent(eventRequestPayload);
            if (ObjectUtils.isNotEmpty(odsRequestPayload)
                    && ObjectUtils.isNotEmpty(odsRequestPayload.getApplication())
                    && CollectionUtils.isNotEmpty(odsRequestPayload.getApplication().getApplicants())) {
                for (Applicant eventApplicant : odsRequestPayload.getApplication().getApplicants()) {
                    if (ObjectUtils.isEmpty(eventApplicant)) continue;
                    String identificationNumberByEvent = this.getIdentificationNumber(eventApplicant.getIdentities());
                    if (identificationNumber.equals(identificationNumberByEvent)) {
                        log.info("Update Address,ReferenceInfo,C06Data In IntegrationEvenLog");
                        /// buildAddress
                        if (CollectionUtils.isNotEmpty(eventApplicant.getAddress())) {
                            addressEntities = new ArrayList<>();
                            this.mapAndCollectEntities(eventApplicant.getAddress(), DeCbcAddressEntity.class,
                                    applicantId, addressEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                        }
                        /// buildReferenceInfo
                        if (CollectionUtils.isNotEmpty(eventApplicant.getReferenceInfo())) {
                            referenceInfoEntities = new ArrayList<>();
                            this.mapAndCollectEntities(eventApplicant.getReferenceInfo(), DeCbcReferenceInfoEntity.class,
                                    applicantId, referenceInfoEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                        }
                        /// buildC06Data
                        if (CollectionUtils.isNotEmpty(eventApplicant.getC06Data())) {
                            c06Entities = new ArrayList<>();
                            this.mapAndCollectEntities(eventApplicant.getC06Data(), DeCbcC06Entity.class,
                                    applicantId, c06Entities, (entity, id) -> entity.setApplicantReferId(applicantId));
                        }
                    }
                }
            }
            /// buildIncomeDetail
            this.mapAndCollectEntities(applicant.getIncomeDetails(), DeCbcIncomeDetailEntity.class,
                    applicantId, incomeDetailEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildWay4Data
            this.mapAndCollectEntities(applicant.getWay4Data(), DeCbcWay4Entity.class,
                    applicantId, way4Entities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildOpRiskData
            OpRiskData opRiskData = applicant.getOpRiskData();
            if (ObjectUtils.isNotEmpty(opRiskData)) {
                this.mapAndCollectEntities(opRiskData.getBlackListLosP(), DeCbcOpRiskEntity.class,
                        applicantId, opRiskEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            }
            /// buildA05Data
            this.mapAndCollectEntities(applicant.getA05Data(), DeCbcA05Entity.class,
                    applicantId, a05Entities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildAmlData
            this.mapAndCollectEntities(applicant.getAmlData(), DeCbcAmlEntity.class,
                    applicantId, amlEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildCICData
            CicData cicData = applicant.getCicData();
            if (ObjectUtils.isNotEmpty(cicData)) {
                this.getCicVariableEntity(applicantId, cicData.getF5OrF16Report(), cicData.getF6Report(),
                        cicData.getB11TReport(), cbcCicDerivedVarEntities);
            }
            /// buildLoanContract
            if (CollectionUtils.isNotEmpty(applicant.getExistingLoanContractDetails())) {
                for (ExistingLoanContractDetail loanContractDetail : applicant.getExistingLoanContractDetails()) {
                    if (ObjectUtils.isEmpty(loanContractDetail)) continue;
                    DeCbcLoanContractEntity loanContractEntity = new DeCbcLoanContractEntity();
                    modelMapper.map(loanContractDetail, loanContractEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(loanContractEntity)
                            && CollectionUtils.isEmpty(loanContractDetail.getLoanDetails())) continue;
                    loanContractEntity.setApplicantReferId(applicantId);
                    Long loanContractId = deCbcLoanContractRepository.save(loanContractEntity).getId();
                    this.mapAndCollectEntities(loanContractDetail.getLoanDetails(), DeCbcLoanDetailEntity.class,
                            loanContractId, loanDetailEntities, (entity, id) -> entity.setLoanContractId(loanContractId));
                }
            }
            /// buildODContract
            if (CollectionUtils.isNotEmpty(applicant.getExistingODContractDetails())) {
                for(ExistingODContractDetail odContractDetail : applicant.getExistingODContractDetails()) {
                    if (ObjectUtils.isEmpty(odContractDetail)) continue;
                    DeCbcOdContractEntity odContractEntity = new DeCbcOdContractEntity();
                    modelMapper.map(odContractDetail, odContractEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(odContractEntity)
                            && CollectionUtils.isEmpty(odContractDetail.getOverdraftDetails())) continue;
                    odContractEntity.setApplicantReferId(applicantId);
                    Long odContractId = deCbcOdContractRepository.save(odContractEntity).getId();
                    this.mapAndCollectEntities(odContractDetail.getOverdraftDetails(), DeCbcOdDetailEntity.class,
                            odContractId, odDetailEntities, (entity, id) -> entity.setOdContractId(odContractId));
                }
            }
            /// buildCardContract
            if (CollectionUtils.isNotEmpty(applicant.getExistingCardContractDetails())) {
                for (ExistingCardContractDetail cardContractDetail : applicant.getExistingCardContractDetails()) {
                    if (ObjectUtils.isEmpty(cardContractDetail)) continue;
                    DeCbcCardContractEntity cardContractEntity = new DeCbcCardContractEntity();
                    modelMapper.map(cardContractDetail, cardContractEntity);

                    if (BeanWrapperUtil.isAllFieldsNull(cardContractEntity)
                            && CollectionUtils.isEmpty(cardContractDetail.getCardDetails())) continue;
                    cardContractEntity.setApplicantReferId(applicantId);
                    Long cardContractId = cbcCardContractRepository.save(cardContractEntity).getId();
                    this.mapAndCollectEntities(cardContractDetail.getCardDetails(), DeCbcCardDetailEntity.class,
                            cardContractId, cardDetailEntities, (entity, id) -> entity.setCardContractId(cardContractId));
                }
            }
            /// buildAccDetail
            this.buildAccDetailEntities(applicantId, applicant.getCasaAccountDetails(), deCbcAccDetailEntities);
            /// buildIdentification from LDZ
            this.mapAndCollectEntities(applicant.getExistingIdentificationData(), DeCbcExistsIdentifyEntity.class,
                    applicantId, identifyEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildThirdSystemScore
            this.mapAndCollectEntities(applicant.getThirdSystemScores(), DeCbcThirdSystemScoreEntity.class,
                    applicantId, systemScoreEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildOverdueAccountDetail
            this.mapAndCollectEntities(applicant.getOverdueAccountDetails(), DeCbcOverdueAccountEntity.class,
                    applicantId, overdueAccEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
            /// buildFraudRuleData
            if (CollectionUtils.isNotEmpty(applicant.getFraudRuleData())) {
                for (FraudRuleData fraudRule : applicant.getFraudRuleData()) {
                    this.mapAndCollectEntities(fraudRule.getMsbBankingData(), DeCbcFraudRuleBankingEntity.class,
                            applicantId, fraudRuleBankingEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                    this.mapAndCollectEntities(fraudRule.getMsbRbApprovalData(), DeCbcFraudRuleRbApprovalEntity.class,
                            applicantId, rbApprovalEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                    this.mapAndCollectEntities(fraudRule.getMsbCapitalData(), DeCbcFraudRuleCapitalEntity.class,
                            applicantId, capitalEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                }
            }
            /// buildModelInfo
            if (CollectionUtils.isNotEmpty(applicant.getModelInfo())) {
                for (ModelInfo modelInfo : applicant.getModelInfo()) {
                    if (ObjectUtils.isEmpty(modelInfo)) continue;
                    DeCbcModelInfoEntity modelInfoEntity = new DeCbcModelInfoEntity();
                    modelMapper.map(modelInfo, modelInfoEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(modelInfoEntity)
                            && CollectionUtils.isEmpty(modelInfo.getModelVariable())
                            && CollectionUtils.isEmpty(modelInfo.getModelTypeQuesGroup())
                            && CollectionUtils.isEmpty(modelInfo.getScoringConstant())
                            && ObjectUtils.isEmpty(modelInfo.getSubModelTypeRel())
                            && ObjectUtils.isEmpty(modelInfo.getSubModelCriterionMap())
                            && ObjectUtils.isEmpty(modelInfo.getSubModelAffectResult())
                            && ObjectUtils.isEmpty(modelInfo.getModelResult())) continue;
                    modelInfoEntity.setApplicantReferId(applicantId);
                    Long modeInfoId = modelInfoRepository.save(modelInfoEntity).getId();
                    this.mapAndCollectEntities(modelInfo.getModelVariable(), DeCbcModelVarEntity.class,
                            modeInfoId, modelVarEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(modelInfo.getModelTypeQuesGroup(), DeCbcModelTypeQuesGroupEntity.class,
                            modeInfoId, typeQuesGroupEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(modelInfo.getScoringConstant(), DeCbcModelScoringConstantEntity.class,
                            modeInfoId, scoringConstantEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(Collections.singletonList(modelInfo.getSubModelTypeRel()), DeCbcSubModelTypeRelEntity.class,
                            modeInfoId, modelTypeRelEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(Collections.singletonList(modelInfo.getSubModelCriterionMap()), DeCbcSubModelCriterionMapEntity.class,
                            modeInfoId, criterionMapEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(Collections.singletonList(modelInfo.getSubModelAffectResult()), DeCbcSubModelAffectResultEntity.class,
                            modeInfoId, affectResultEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                    this.mapAndCollectEntities(Collections.singletonList(modelInfo.getModelResult()), DeCbcModelResultEntity.class,
                            modeInfoId, modelResultEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                }
            }
            /// buildLeadDetail
            if (CollectionUtils.isNotEmpty(applicant.getLeadDetails())) {
                for (LeadDetail leadDetail : applicant.getLeadDetails()) {
                    if (ObjectUtils.isEmpty(leadDetail)) continue;
                    DeCbcLeadDetailEntity leadDetailEntity = new DeCbcLeadDetailEntity();
                    modelMapper.map(leadDetail, leadDetailEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(leadDetailEntity)
                            && ObjectUtils.isEmpty(leadDetail.getCasaInfo())) continue;
                    leadDetailEntity.setApplicantReferId(applicantId);
                    Long leadReferId = leadDetailRepository.save(leadDetailEntity).getId();
                    /// buildCasaInfo
                    this.mapAndCollectEntities(Collections.singletonList(leadDetail.getCasaInfo()), DeCbcCasaInfoEntity.class,
                            leadReferId, casaInfoEntities, (entity, id) -> entity.setLeadReferId(leadReferId));
                }
            }
            /// buildLeadModelInfo
            this.mapAndCollectEntities(applicant.getLeadModelInfo(), DeCbcLeadModelInfoEntity.class,
                    applicantId, leadModelInfoEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
        }

        identificationRepository.saveAll(identificationEntities);
        if (CollectionUtils.isNotEmpty(addressEntities))
            deCbcAddressRepository.saveAll(addressEntities);
        if (CollectionUtils.isNotEmpty(referenceInfoEntities))
            deCbcReferenceInfoRepository.saveAll(referenceInfoEntities);
        if (CollectionUtils.isNotEmpty(incomeDetailEntities))
            deCbcIncomeDetailRepository.saveAll(incomeDetailEntities);
        if (CollectionUtils.isNotEmpty(c06Entities))
            deCbcC06Repository.saveAll(c06Entities);
        if (CollectionUtils.isNotEmpty(way4Entities))
            deCbcWay4Repository.saveAll(way4Entities);
        if (CollectionUtils.isNotEmpty(opRiskEntities))
            deCbcOpRiskRepository.saveAll(opRiskEntities);
        if (CollectionUtils.isNotEmpty(a05Entities))
            deCbcA05Repository.saveAll(a05Entities);
        if (CollectionUtils.isNotEmpty(amlEntities))
            deCbcAmlRepository.saveAll(amlEntities);
        if (CollectionUtils.isNotEmpty(cbcCicDerivedVarEntities))
            deCbcCicDerivedVarRepository.saveAll(cbcCicDerivedVarEntities);
        if (CollectionUtils.isNotEmpty(loanDetailEntities))
            deCbcLoanDetailRepository.saveAll(loanDetailEntities);
        if (CollectionUtils.isNotEmpty(odDetailEntities))
            deCbcOdDetailRepository.saveAll(odDetailEntities);
        if (CollectionUtils.isNotEmpty(cardDetailEntities))
            deCbcCardDetailRepository.saveAll(cardDetailEntities);
        if (CollectionUtils.isNotEmpty(deCbcAccDetailEntities))
            deCbcAccDetailRepository.saveAll(deCbcAccDetailEntities);
        if (CollectionUtils.isNotEmpty(identifyEntities))
            identifyRepository.saveAll(identifyEntities);
        if (CollectionUtils.isNotEmpty(systemScoreEntities))
            systemScoreRepository.saveAll(systemScoreEntities);
        if (CollectionUtils.isNotEmpty(overdueAccEntities))
            overdueAccountRepository.saveAll(overdueAccEntities);
        if (CollectionUtils.isNotEmpty(fraudRuleBankingEntities))
            fraudRuleBankingRepository.saveAll(fraudRuleBankingEntities);
        if (CollectionUtils.isNotEmpty(rbApprovalEntities))
            rbApprovalRepository.saveAll(rbApprovalEntities);
        if (CollectionUtils.isNotEmpty(capitalEntities))
            capitalRepository.saveAll(capitalEntities);
        if (CollectionUtils.isNotEmpty(modelVarEntities))
            modelVarRepository.saveAll(modelVarEntities);
        if (CollectionUtils.isNotEmpty(typeQuesGroupEntities))
            typeQuesGroupRepository.saveAll(typeQuesGroupEntities);
        if (CollectionUtils.isNotEmpty(modelTypeRelEntities))
            modelTypeRelRepository.saveAll(modelTypeRelEntities);
        if (CollectionUtils.isNotEmpty(scoringConstantEntities))
            scoringConstantRepository.saveAll(scoringConstantEntities);
        if (CollectionUtils.isNotEmpty(criterionMapEntities))
            criterionMapRepository.saveAll(criterionMapEntities);
        if (CollectionUtils.isNotEmpty(affectResultEntities))
            affectResultRepository.saveAll(affectResultEntities);
        if (CollectionUtils.isNotEmpty(modelResultEntities))
            modelResultRepository.saveAll(modelResultEntities);
        if (CollectionUtils.isNotEmpty(leadDetailEntities))
            leadDetailRepository.saveAll(leadDetailEntities);
        if (CollectionUtils.isNotEmpty(casaInfoEntities))
            casaInfoRepository.saveAll(casaInfoEntities);
        if (CollectionUtils.isNotEmpty(leadModelInfoEntities))
            leadModelInfoRepository.saveAll(leadModelInfoEntities);
        if (CollectionUtils.isNotEmpty(applicantVarEntities))
            applicantVarRepository.saveAll(applicantVarEntities);
    }

    private <T, E> void mapAndCollectEntities(List<T> sourceList, Class<E> targetClass, Object foreignKeyValue,
                                              List<E> result, BiConsumer<E, Object> foreignKeySetter) {

        if (CollectionUtils.isEmpty(sourceList)) return;
        for (T source : sourceList) {
            if (ObjectUtils.isEmpty(source)) continue;
            try {
                E entity = targetClass.getDeclaredConstructor().newInstance();
                modelMapper.map(source, entity);
                if (BeanWrapperUtil.isAllFieldsNull(entity)) continue;
                foreignKeySetter.accept(entity, foreignKeyValue);
                result.add(entity);
            } catch (Exception e) {
                log.error("Mapping Entity error:{}", e.getMessage());
                throw new RuntimeException("Mapping Entity error:" + targetClass.getSimpleName(), e);
            }
        }
    }

    private void getCicVariableEntity(Long applicantId, List<F5OrF16Report> f5OrF16Reports,
                                      List<F6Report> f6Reports, List<B11TReport> b11TReports,
                                      List<DeCbcCicDerivedVarEntity> cbcCicDerivedVarEntities) {

        /// getF5orF16Report
        if (CollectionUtils.isNotEmpty(f5OrF16Reports)) {
            for (F5OrF16Report f5OrF16Report : f5OrF16Reports){
                DeCbcCicEntity cicEntity = new DeCbcCicEntity();
                modelMapper.map(f5OrF16Report, cicEntity);
                if (BeanWrapperUtil.isAllFieldsNull(cicEntity)) continue;
                cicEntity.setApplicantReferId(applicantId);
                cicEntity.setGroup("F5ORF16");
                Long cicId = deCbcCicRepository.save(cicEntity).getId();
                this.buildCicDerivedVarEntities(cicId, f5OrF16Report.getF5orF16DerivedVariables(),
                        cicEntity.getGroup(), cbcCicDerivedVarEntities);
            }
        }
        /// getF6Report
        if (CollectionUtils.isNotEmpty(f6Reports)) {
            for (F6Report f6Report : f6Reports){
                DeCbcCicEntity cicEntity = new DeCbcCicEntity();
                modelMapper.map(f6Report, cicEntity);
                if (BeanWrapperUtil.isAllFieldsNull(cicEntity)) continue;
                cicEntity.setApplicantReferId(applicantId);
                cicEntity.setGroup("F6");
                Long cicId = deCbcCicRepository.save(cicEntity).getId();
                this.buildCicDerivedVarEntities(cicId, f6Report.getF6DerivedVariables(),
                        cicEntity.getGroup(), cbcCicDerivedVarEntities);
            }
        }
        /// getB11TReport
        if (CollectionUtils.isNotEmpty(b11TReports)) {
            for (B11TReport b11TReport : b11TReports){
                DeCbcCicEntity cicEntity = new DeCbcCicEntity();
                modelMapper.map(b11TReport, cicEntity);
                if (BeanWrapperUtil.isAllFieldsNull(cicEntity)) continue;
                cicEntity.setApplicantReferId(applicantId);
                cicEntity.setGroup("B11T");
                Long cicId = deCbcCicRepository.save(cicEntity).getId();
                this.buildCicDerivedVarEntities(cicId, b11TReport.getB11DerivedVariables(),
                        cicEntity.getGroup(), cbcCicDerivedVarEntities);
            }
        }
    }

    private <T> void buildCicDerivedVarEntities(Long cicId, List<T> variables, String group,
                                                List<DeCbcCicDerivedVarEntity> cbcCicDerivedVarEntities){
        if (CollectionUtils.isEmpty(variables)) return;
        for (T variable : variables) {
            Map<String, Object> map = JsonUtil.readValue(variable, new TypeReference<>(){});
            map.forEach((varName, varValue) -> {
                if (ObjectUtils.isNotEmpty(varValue)) {
                    DeCbcCicDerivedVarEntity varEntity = new DeCbcCicDerivedVarEntity();
                    varEntity.setCicId(cicId);
                    varEntity.setVarName(varName);
                    varEntity.setVarValue(varValue.toString());
                    varEntity.setGroup(group);
                    cbcCicDerivedVarEntities.add(varEntity);
                }
            });
        }
    }

    private void buildAccDetailEntities(Long applicantId, List<Object> casaAccountDetails,
                                        List<DeCbcAccDetailEntity> deCbcAccDetailEntities) {
        if (CollectionUtils.isEmpty(casaAccountDetails)) return;
        for (Object casaAccountDetail : casaAccountDetails) {
            Map<String, Object> accDetailMap = JsonUtil.readValue(casaAccountDetail, new TypeReference<>(){});
            String cifNumber = null;
            for (Map.Entry<String, Object> entry : accDetailMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (ObjectUtils.isEmpty(value)) continue;
                if ("cifNumber".equalsIgnoreCase(key)) {
                    cifNumber = value.toString(); continue;
                }
                DeCbcAccDetailEntity accDetailEntity = new DeCbcAccDetailEntity();
                accDetailEntity.setApplicantReferId(applicantId);
                accDetailEntity.setCifNumber(cifNumber);
                accDetailEntity.setVarName(key);
                accDetailEntity.setVarValue(value.toString());
                accDetailEntity.setAccType("CASA");
                deCbcAccDetailEntities.add(accDetailEntity);
            }
        }
    }

    private String getIdentificationNumber(List<Identity> identities) {
        String identificationNumber = null;
        if (CollectionUtils.isEmpty(identities)) {
            log.error("identities IS INVALID");
            throw new RuntimeException("identities IS INVALID");
        }

        for (Identity identity: identities) {
            if (Boolean.TRUE.toString().equalsIgnoreCase(identity.getIsMainIdentification())
                    || Boolean.TRUE.toString().equalsIgnoreCase(identity.getMainIdentification()) ) {
                identificationNumber = identity.getIdentificationNumber();
            }
        }

        if (StringUtils.isEmpty(identificationNumber)) {
            log.error("identificationNumber IS INVALID");
            throw new RuntimeException("identificationNumber IS INVALID");
        }
        return identificationNumber;
    }

    private OdsRequestPayload parseRequestPayloadByEvent(String eventRequestPayload) {
        if (StringUtils.isEmpty(eventRequestPayload)) return null;
        try {
            return JsonUtil.readValue(eventRequestPayload, OdsRequestPayload.class);
        } catch (Exception e) {
            log.error("Parse odsRequestPayload Error=[{}]", e.getMessage(), e);
            throw new BusinessException(String.format("Parse odsRequestPayload Error=[%s]", e.getMessage()));
        }
    }
}
