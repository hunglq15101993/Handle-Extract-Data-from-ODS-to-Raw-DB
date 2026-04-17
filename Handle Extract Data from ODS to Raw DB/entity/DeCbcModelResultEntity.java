package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_RESULT")
public class DeCbcModelResultEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_RESULT",
            sequenceName = "SEQ_DE_CBC_MODEL_RESULT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_RESULT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "MODEL_TYPE_SCORE_DIRECTION")
    private String modelTypeScoreDirection;

    @Column(name = "OVERRIDE_VALUE")
    private Integer overrideValue;

    @Column(name = "SPECIALIZED_BANK")
    private String specializedBank;

    @Column(name = "STANDARDISED_SCORE")
    private BigDecimal standardizeScore;

    @Column(name = "OVERRIDE_LEVEL")
    private String overrideLevel;

    @Column(name = "SCORE")
    private BigDecimal score;

    @Column(name = "SCORE_PD")
    private BigDecimal scorePd;

    @Column(name = "UNCALIBRATED_PD")
    private BigDecimal uncalibratedPd;

    @Column(name = "CALIBRATED_PD")
    private BigDecimal calibratedPd;

    @Column(name = "FINAL_SCORE")
    private BigDecimal finalScore;

    @Column(name = "RANK")
    private String rank;

    @Column(name = "RANK_ORDER")
    private Integer rankOrder;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "RESULT_STATUS_CODE")
    private String resultStatusCode;

    @Column(name = "IS_MODEL_SELECTED")
    private String isModelSelected;

    @Column(name = "IS_OVERRIDE")
    private String isOverride;

    @Column(name = "IS_TOLERRANCE")
    private String isTolerrance;

    @Column(name = "IS_SUB_AFFECT")
    private String isSubAffect;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @Column(name = "SCORING_DATE")
    private LocalDateTime scoringDate;

    @Column(name = "STEP_FINAL")
    private BigDecimal stepFinal;

    @Column(name = "PRODUCT_CLASS")
    private String productClass;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
