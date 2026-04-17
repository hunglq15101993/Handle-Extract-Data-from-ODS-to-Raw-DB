package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_PRODUCT")
public class DeCbcProductEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_PRODUCT",
            sequenceName = "SEQ_DE_CBC_PRODUCT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_PRODUCT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "APPLICATION_PARENT_CODE")
    private String applicationParentCode;

    @Column(name = "PRODUCT_CLASS")
    private String productClass;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_CATEGORY")
    private String productCategory;

    @Column(name = "REGULATION_CODE")
    private String regulationCode;

    @Column(name = "PROPOSED_AMOUNT_OR_LIMIT")
    private BigDecimal proposedAmountOrLimit;

    @Column(name = "LOAN_PROPOSE_MONTH")
    private BigDecimal loanProposeMonth;

    @Column(name = "APPROVAL_FLOW")
    private String approvalFlow;

    @Column(name = "LOAN_METHOD")
    private String loanMethod;

    @Column(name = "PRODUCT_PACKAGE")
    private String productPackage;

    @Column(name = "LOAN_SECURE")
    private String loanSecure;

    @Column(name = "FINAL_DECISION")
    private String finalDecision;

    @Column(name = "RECOMMENDED_CREDIT_DECISION")
    private String recommendedCreditDecision;

    @Column(name = "DTI")
    private BigDecimal dti;

    @Column(name = "MUE")
    private BigDecimal mue;

    @Column(name = "MIN_PRODUCT_LIMIT")
    private BigDecimal minProductLimit;

    @Column(name = "MAX_PRODUCT_LIMIT")
    private BigDecimal maxProductLimit;

    @Column(name = "PRODUCT_FLOW")
    private String productFlow;

    @Column(name = "DRA")
    private BigDecimal dra;
    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
