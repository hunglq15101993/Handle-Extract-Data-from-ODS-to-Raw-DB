package com.msb.stp.leadmanagement.oracle.cbcRaw.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppEntity;
import com.msb.stp.leadmanagement.exceptions.BusinessException;
import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.*;
import com.msb.stp.leadmanagement.oracle.cbcRaw.repository.*;
import com.msb.stp.leadmanagement.oracle.cbcRaw.service.CbcRawCommonService;
import com.msb.stp.leadmanagement.oracle.ods.dto.request.*;
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
public class CbcRawCommonServiceImpl implements CbcRawCommonService {

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
    private final DeCbcModelCicRepository deCbcModelCicRepository;
    private final DeCbcModelCicDerivedVarRepository deCbcModelCicDerivedVarRepository;
    private final DeCbcModelOutputRepository deCbcModelOutputRepository;
    private final DeCbcModelInternalDataRepository modelInternalDataRepository;

    @Override
    @Transactional("oracleTransactionManagerDeCbcRaw")
    public void insertCbcRaw(ProcessSaveAppEntity saveAppEntity, OdsRequestPayload requestPayload) {
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
        this.saveApplication(appId, application, uwCriteriaEntities, applicationVarEntities, saveAppEntity.getVersion());
        /// saveProduct
        this.saveRequestProducts(appId, application.getRequestedProducts(), uwCriteriaEntities, applicationVarEntities);
        if (CollectionUtils.isNotEmpty(uwCriteriaEntities))
            uwCriteriaRepository.saveAll(uwCriteriaEntities);
        if (CollectionUtils.isNotEmpty(applicationVarEntities))
            applicationVarRepository.saveAll(applicationVarEntities);
        /// saveApplicant
        this.saveApplicants(appId, saveAppEntity.getEventRequestPayload(),
                application.getApplicants(), saveAppEntity.getModelRequestPayload());
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
                                 List<DeCbcApplicationVarEntity> applicationVarEntities,
                                 Long version) {
        ApplicationInfo appInfo = application.getApplicationInfo();
        if (ObjectUtils.isEmpty(appInfo)) appInfo = new ApplicationInfo();
        DeCbcApplicationEntity appEntity = new DeCbcApplicationEntity();
        modelMapper.map(appInfo, appEntity);
        if (Objects.nonNull(version)) {
            appEntity.setVersion(version);
        }

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
            if (CollectionUtils.isEmpty(policyDecision.getUwCriterias())) continue;
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
                if(CollectionUtils.isNotEmpty(productDecision.getUwCriterias())){
                    for (UwCriteria uwCriteria : productDecision.getUwCriterias()){
                        if (ObjectUtils.isEmpty(uwCriteria)) continue;
                        DeCbcUwCriteriaEntity uwCriteriaEntity = new DeCbcUwCriteriaEntity();
                        modelMapper.map(uwCriteria, uwCriteriaEntity);
                        if (BeanWrapperUtil.isAllFieldsNull(uwCriteriaEntity)) continue;
                        uwCriteriaEntity.setProductId(productId);
                        uwCriteriaEntity.setGroup("PRODUCT");
                        uwCriteriaEntities.add(uwCriteriaEntity);
                    }
                }

                /// buildApplicationVar
                if (CollectionUtils.isNotEmpty(productDecision.getCalculatedVariables())) {
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
        }

        if (CollectionUtils.isNotEmpty(offerEntities)) {
            offerRepository.saveAll(offerEntities);
        }
    }

    private void saveApplicants(String appId, String eventRequestPayload,
                                List<Applicant> applicants, String modelRequestPayload) {
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
        List<DeCbcModelCicDerivedVarEntity> modelCicDerivedVarEntities = new ArrayList<>();
        List<DeCbcModelOutputEntity> modelOutputEntities = new ArrayList<>();
        List<DeCbcModelInternalDataEntity> modelInternalDataEntities = new ArrayList<>();

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
            /// build F5Report and F6Report
            if (StringUtils.isNotEmpty(modelRequestPayload)) {
                OdsRequestPayload requestPayload = JsonUtil.readValue(modelRequestPayload, OdsRequestPayload.class);
                if (ObjectUtils.isEmpty(requestPayload)
                        || ObjectUtils.isEmpty(requestPayload.getApplication())
                        || CollectionUtils.isEmpty(requestPayload.getApplication().getApplicants())) {
                    log.info("ModelRequestPayload IS INVALID");
                    throw new RuntimeException("ModelRequestPayload IS INVALID");
                }
                List<Applicant> applicantsByModel = requestPayload.getApplication().getApplicants();
                if (CollectionUtils.isNotEmpty(applicantsByModel)) {
                    this.getModelCicVariableEntity(applicantId, applicantsByModel, applicant.getApplicantType(), modelCicDerivedVarEntities);
                    for (Applicant applicantModel : applicantsByModel) {
                        if (!applicantModel.getApplicantType().equalsIgnoreCase(applicant.getApplicantType())
                                || ObjectUtils.isEmpty(applicant.getCicData()))
                            continue;

                        /// buildLeadModelInfo
                        this.mapAndCollectEntities(applicantModel.getLeadModelInfo(), DeCbcLeadModelInfoEntity.class,
                                applicantId, leadModelInfoEntities, (entity, id) -> entity.setApplicantReferId(applicantId));
                        /// buildModelOutout
                        this.mapAndCollectEntities(applicantModel.getModelOutputs(), DeCbcModelOutputEntity.class,
                                applicantId, modelOutputEntities, (entity, id) -> entity.setApplicantReferId(applicantId));

                        /// buildModelInternalData
                        this.buildInternalDataEntities(applicantId, applicantModel.getInternalData(), modelInternalDataEntities);

                        /// buildModelInfo
                        if (CollectionUtils.isNotEmpty(applicantModel.getModelInfo())) {
                            for (ModelInfo modelInfo : applicantModel.getModelInfo()) {
                                if (ObjectUtils.isEmpty(modelInfo)) continue;
                                DeCbcModelInfoEntity modelInfoEntity = new DeCbcModelInfoEntity();
                                modelMapper.map(modelInfo, modelInfoEntity);
                                if (BeanWrapperUtil.isAllFieldsNull(modelInfoEntity)
                                        && CollectionUtils.isEmpty(modelInfo.getModelVariable())
                                        && CollectionUtils.isEmpty(modelInfo.getModelTypeQuesGroup())
                                        && CollectionUtils.isEmpty(modelInfo.getScoringConstant())
                                        && ObjectUtils.isEmpty(modelInfo.getModelResult())) continue;
                                modelInfoEntity.setApplicantReferId(applicantId);
                                Long modeInfoId = modelInfoRepository.save(modelInfoEntity).getId();
                                this.mapAndCollectEntities(modelInfo.getModelVariable(), DeCbcModelVarEntity.class,
                                        modeInfoId, modelVarEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                                this.mapAndCollectEntities(modelInfo.getModelTypeQuesGroup(), DeCbcModelTypeQuesGroupEntity.class,
                                        modeInfoId, typeQuesGroupEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                                this.mapAndCollectEntities(modelInfo.getScoringConstant(), DeCbcModelScoringConstantEntity.class,
                                        modeInfoId, scoringConstantEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));

                                if (ObjectUtils.isEmpty(modelInfo.getSubModelInfo())) continue;
                                this.mapAndCollectEntities(Collections.singletonList(modelInfo.getSubModelInfo().getSubModelTypeRel()), DeCbcSubModelTypeRelEntity.class,
                                        modeInfoId, modelTypeRelEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                                this.mapAndCollectEntities(modelInfo.getSubModelInfo().getSubModelCriterionMaps(), DeCbcSubModelCriterionMapEntity.class,
                                        modeInfoId, criterionMapEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                                this.mapAndCollectEntities(modelInfo.getSubModelInfo().getSubModelAffectResults(), DeCbcSubModelAffectResultEntity.class,
                                        modeInfoId, affectResultEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                                this.mapAndCollectEntities(Collections.singletonList(modelInfo.getModelResult()), DeCbcModelResultEntity.class,
                                        modeInfoId, modelResultEntities, (entity, id) -> entity.setModelInfoId(modeInfoId));
                            }
                        }

                    }
                }
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
        }

        identificationRepository.saveAll(identificationEntities);

        log.info("addressEntities values[{}]", referenceInfoEntities);
        if (CollectionUtils.isNotEmpty(addressEntities))
            deCbcAddressRepository.saveAll(addressEntities);

        log.info("referenceInfoEntities values[{}]", referenceInfoEntities);
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
        if (CollectionUtils.isNotEmpty(modelCicDerivedVarEntities))
            deCbcModelCicDerivedVarRepository.saveAll(modelCicDerivedVarEntities);
        if (CollectionUtils.isNotEmpty(modelOutputEntities))
            deCbcModelOutputRepository.saveAll(modelOutputEntities);
        if (CollectionUtils.isNotEmpty(modelInternalDataEntities))
            modelInternalDataRepository.saveAll(modelInternalDataEntities);
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
                log.error("Mapping Entity error:{}", e.getMessage(), e);
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

    private void getModelCicVariableEntity(Long applicantId, List<Applicant> applicantsByModel, String applicantType,
                                     List<DeCbcModelCicDerivedVarEntity> modelCicDerivedVarEntities) {
        for (Applicant applicant : applicantsByModel) {
            if (!applicant.getApplicantType().equalsIgnoreCase(applicantType)
                    || ObjectUtils.isEmpty(applicant.getCicData()))
                continue;

            /// getF6ReportModel
            List<F6Report> f6Reports = applicant.getCicData().getF6Report();
            if (CollectionUtils.isNotEmpty(f6Reports)) {
                for (F6Report f6Report : f6Reports){
                    DeCbcModelCicEntity modelCicEntity = new DeCbcModelCicEntity();
                    modelMapper.map(f6Report, modelCicEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(modelCicEntity)) continue;
                    modelCicEntity.setApplicantReferId(applicantId);
                    modelCicEntity.setGroup("F6");
                    Long cicId = deCbcModelCicRepository.save(modelCicEntity).getId();
                    this.buildModelCicDerivedVarEntities(cicId, f6Report.getF6DerivedVariables(),
                            modelCicEntity.getGroup(), modelCicDerivedVarEntities);
                }
            }

            /// getF5ReportModel
            List<F5Report> f5Reports = applicant.getCicData().getF5Report();
            if (CollectionUtils.isNotEmpty(f5Reports)) {
                for (F5Report f5Report : f5Reports){
                    DeCbcModelCicEntity modelCicEntity = new DeCbcModelCicEntity();
                    modelMapper.map(f5Report, modelCicEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(modelCicEntity)) continue;
                    modelCicEntity.setApplicantReferId(applicantId);
                    modelCicEntity.setGroup("F5");
                    Long cicId = deCbcModelCicRepository.save(modelCicEntity).getId();
                    this.buildModelCicDerivedVarEntities(cicId, f5Report.getF5DerivedVariables(),
                            modelCicEntity.getGroup(), modelCicDerivedVarEntities);
                }
            }

            /// getS11TReportModel
            List<S11TReport> s11TReports = applicant.getCicData().getS11TReport();
            if (CollectionUtils.isNotEmpty(s11TReports)) {
                for(S11TReport s11TReport : s11TReports){
                    DeCbcModelCicEntity modelCicEntity = new DeCbcModelCicEntity();
                    modelMapper.map(s11TReport, modelCicEntity);
                    if (BeanWrapperUtil.isAllFieldsNull(modelCicEntity)) continue;
                    modelCicEntity.setApplicantReferId(applicantId);
                    modelCicEntity.setGroup("F5");
                    Long cicId = deCbcModelCicRepository.save(modelCicEntity).getId();
                    this.buildModelCicDerivedVarEntities(cicId, s11TReport.getS11TDerivedVariables(),
                            modelCicEntity.getGroup(), modelCicDerivedVarEntities);
                }
            }
        }

    }

    private <T> void buildModelCicDerivedVarEntities(Long cicId, List<T> variables, String group,
                                                     List<DeCbcModelCicDerivedVarEntity> ModelCicDerivedVarEntities){
        if (CollectionUtils.isEmpty(variables)) return;
        for (T variable : variables) {
            Map<String, Object> map = JsonUtil.readValue(variable, new TypeReference<>(){});
            map.forEach((varName, varValue) -> {
                if (ObjectUtils.isNotEmpty(varValue)) {
                    DeCbcModelCicDerivedVarEntity varEntity = new DeCbcModelCicDerivedVarEntity();
                    varEntity.setCicId(cicId);
                    varEntity.setVarName(varName);
                    varEntity.setVarValue(varValue.toString());
                    varEntity.setGroup(group);
                    ModelCicDerivedVarEntities.add(varEntity);
                }
            });
        }
    }

    private <T> void buildInternalDataEntities(Long applicantId, List<T> internalDatas,
                                               List<DeCbcModelInternalDataEntity> modelInternalDataEntities){
        if (CollectionUtils.isEmpty(internalDatas)) return;
        for (T internalData : internalDatas) {
            Map<String, Object> map = JsonUtil.readValue(internalData, new TypeReference<>(){});
            String cifNumber = null;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (ObjectUtils.isEmpty(entry.getValue())) continue;
                if ("CUIF001".equalsIgnoreCase(entry.getKey())) {
                    cifNumber = entry.getValue().toString(); break;
                }
            }
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (ObjectUtils.isEmpty(entry.getValue())) continue;
                DeCbcModelInternalDataEntity varEntity = new DeCbcModelInternalDataEntity();
                varEntity.setApplicantReferId(applicantId);
                varEntity.setCifNumber(cifNumber);
                varEntity.setVarName(entry.getKey());
                varEntity.setVarValue(entry.getValue().toString());
                varEntity.setAccType("INTERNAL");
                modelInternalDataEntities.add(varEntity);
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
            if (CollectionUtils.isEmpty(accDetailMap.entrySet())) continue;
            for (Map.Entry<String, Object> entry : accDetailMap.entrySet()) {
                if (ObjectUtils.isEmpty(entry.getValue())) continue;
                if ("cifNumber".equalsIgnoreCase(entry.getKey())) {
                    cifNumber = entry.getValue().toString(); break;
                }
            }
            for (Map.Entry<String, Object> entry : accDetailMap.entrySet()) {
                if (ObjectUtils.isEmpty(entry.getValue())) continue;
                DeCbcAccDetailEntity accDetailEntity = new DeCbcAccDetailEntity();
                accDetailEntity.setApplicantReferId(applicantId);
                accDetailEntity.setCifNumber(cifNumber);
                accDetailEntity.setVarName(entry.getKey());
                accDetailEntity.setVarValue(entry.getValue().toString());
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
