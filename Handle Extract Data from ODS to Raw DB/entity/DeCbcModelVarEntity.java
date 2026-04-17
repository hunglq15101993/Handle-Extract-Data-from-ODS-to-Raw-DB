package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_VARIABLE")
public class DeCbcModelVarEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_VARIABLE",
            sequenceName = "SEQ_DE_CBC_MODEL_VARIABLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_VARIABLE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "QUESTION_CODE")
    private String questionCode;

    @Column(name = "ANS_DATA_TYPE")
    private String ansDataType;

    @Column(name = "ANSWER_VALUE")
    private String answerValue;

    @Column(name = "MODEL_TYPE_CODE")
    private String modelTypeCode;

    @Column(name = "DATE_ANSWER")
    private LocalDateTime dateAnswer;

    @Column(name = "MODEL_TYPE_QUES_CODE")
    private String modelTypeQuesCode;

    @Column(name = "CALCULATE_LEVEL")
    private Integer calculateLevel;

    @Column(name = "MODEL_TYPE_QUES_GROUP")
    private String modelTypeQuesGroup;

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @Column(name = "MEAN")
    private BigDecimal mean;

    @Column(name = "STD")
    private BigDecimal std;

    @Column(name = "IS_SUB_ACCOMPANY")
    private String isSubAccompany;

    @Column(name = "IS_TOLERANCE")
    private String isTolerance;

    @Column(name = "IS_TOLERANCE_APPLIED")
    private String isToleranceApplied;

    @Column(name = "TOLERANCE_UNIT")
    private String toleranceUnit;

    @Column(name = "TOLERANCE_VALUE")
    private BigDecimal toleranceValue;

    @Column(name = "IS_OVERRIDE")
    private String isOverride;

    @Column(name = "IS_OVERRIDE_APPLIED")
    private String isOverrideApplied;

    @Column(name = "OVERRIDE_TYPE")
    private String overrideType;

    @Column(name = "OVERRIDE_VALUE")
    private Integer overrideValue;

    @Column(name = "ML_QUESTION_CODE")
    private String mlQuestionCode;

    @Column(name = "NUMERIC_ANSWER")
    private BigDecimal numericAnswer;

    @Column(name = "BOOLEAN_ANSWER")
    private String booleanAnswer;

    @Column(name = "STRING_ANSWER")
    private String stringAnswer;

    @Column(name = "PARTIAL_SCORE")
    private BigDecimal partialScore;

    @Column(name = "STANDARDIZE_SCORE")
    private BigDecimal standardizeScore;

    @Column(name = "REASON_CODE")
    private String reasonCode;

    @Column(name = "ANSWER_ORDER")
    private Integer answerOrder;

    @Column(name = "SCORE_ORDER")
    private Integer scoreOrder;

    @Column(name = "RANK_ORDER")
    private Integer rankOrder;

    @Column(name = "OVERRIDE_DIRECTION")
    private String overrideDirection;

    @Column(name = "INT_OR_REAL_ANSWER")
    private BigDecimal intOrRealAnswer;

    @Column(name = "OVERRIDEN_RANK")
    private BigDecimal overridenRank;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
