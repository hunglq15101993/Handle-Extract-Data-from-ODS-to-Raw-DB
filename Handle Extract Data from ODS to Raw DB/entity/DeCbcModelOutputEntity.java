package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_OUTPUT")
public class DeCbcModelOutputEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_OUTPUT",
            sequenceName = "SEQ_DE_CBC_MODEL_OUTPUT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_OUTPUT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "RANK")
    private String rank;

    @Column(name = "MODEL_TYPE_CODE")
    private String modelTypeCode;

    @Column(name = "PRODUCT_CATEGORY")
    private String productCategory;

    @Column(name = "RANK_ORDER")
    private String rankOrder;

    @Column(name = "MODEL_GROUP_NAME")
    private String modelGroupName;

    @Column(name = "TYPE_OF_MODEL")
    private String typeOfModel;

    @Column(name = "LOAN_METHOD")
    private String loanMethod;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "FINAL_SCORE")
    private BigDecimal finalScore;

    @Column(name = "SCORING_DATE")
    private LocalDateTime scoringDate;

    @Column(name = "SPECIALIZED_BANK")
    private String specializedBank;

    @Column(name = "CUST_ID")
    private String custId;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "IS_MODEL_SELECTED")
    private String isModelSelected;

    @Column(name = "MODEL_INFO_CODE")
    private String modelInfoCode;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
