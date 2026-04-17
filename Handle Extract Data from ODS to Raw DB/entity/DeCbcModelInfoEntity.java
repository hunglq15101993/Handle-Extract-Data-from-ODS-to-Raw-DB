package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_INFO")
public class DeCbcModelInfoEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_INFO",
            sequenceName = "SEQ_DE_CBC_MODEL_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_INFO")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CUST_ID")
    private String custId;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "MODEL_INFO_CODE")
    private String modelInfoCode;

    @Column(name = "TYPE_OF_MODEL")
    private String typeOfModel;

    @Column(name = "MODEL_TYPE_CODE")
    private String modelTypeCode;

    @Column(name = "TYPE_OF_SEGMENT")
    private String typeOfSegment;

    @Column(name = "SEGMENT_RESULT")
    private String segmentResult;

    @Column(name = "ML_FLG")
    private String mlFlg;

    @Column(name = "IS_MODEL_AUTO")
    private String isModelAuto;

    @Column(name = "SUB_MODEL_TYPE")
    private String subModelType;

    @Column(name = "OVERRIDE_TOLERANCE_SUB")
    private String overrideToleranceSub;

    @Column(name = "IS_SEGMENT_MODEL")
    private String isSegmentModel;

    @Column(name = "MODEL_CATEGORY")
    private String modelCategory;

    @Column(name = "MODEL_TYPE_SCORE_DIRECTION")
    private String modelTypeScoreDirection;

    @Column(name = "MODEL_SEQUENCE_NO")
    private Integer modelSequenceNo;

    @Column(name = "APPLICANT_ID")
    private String applicantId;

    @Column(name = "FLAG_EXECUTE_STATUS")
    private String flagExecuteStatus;

    @Column(name = "MODEL_GROUP_NAME")
    private String modelGroupName;

    @Column(name = "LOAN_METHOD")
    private String loanMethod;

    @Column(name = "PRODUCT_CATEGORY")
    private String productCategory;

    @Column(name = "LOAN_METHOD_REQUEST")
    private String loanMethodRequest;

    @Column(name = "PRODUCT_CATEGORY_REQUEST")
    private String productCategoryRequest;

    @Column(name = "IS_NEXT_MODEL")
    private String isNextModel;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
