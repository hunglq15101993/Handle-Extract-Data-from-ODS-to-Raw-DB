package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_LEAD_MODEL_INFO")
public class DeCbcLeadModelInfoEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_LEAD_MODEL_INFO",
            sequenceName = "SEQ_DE_CBC_LEAD_MODEL_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_LEAD_MODEL_INFO")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "RANK")
    private String rank;

    @Column(name = "MODEL_TYPE_CODE")
    private String modelTypeCode;

    @Column(name = "MODEL_INFO_CODE")
    private String modelInfoCode;

    @Column(name = "PRODUCT_CLASS")
    private String productClass;

    @Column(name = "RANK_ORDER")
    private Integer rankOrder;

    @Column(name = "EFFECTIVE_DATE")
    private LocalDateTime effectiveDate;

    @Column(name = "EXPIRE_DATE")
    private LocalDateTime expireDate;

    @Column(name = "CUST_ID")
    private String custId;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "FINAL_SCORE")
    private BigDecimal finalScore;

    @Column(name = "MODEL_GROUP_NAME")
    private String modelGroupName;

    @Column(name = "LOAN_METHOD")
    private String loanMethod;

    @Column(name = "PRODUCT_CATEGORY")
    private String productCategory;

    @Column(name = "TYPE_OF_MODEL")
    private String typeOfModel;

    @Column(name = "SCORING_DATE")
    private LocalDateTime scoringDate;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
