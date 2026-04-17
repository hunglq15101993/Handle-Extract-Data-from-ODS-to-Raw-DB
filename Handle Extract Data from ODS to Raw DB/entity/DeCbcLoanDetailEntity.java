package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_LOAN_DETAIL")
public class DeCbcLoanDetailEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_LOAN_DETAIL",
            sequenceName = "SEQ_DE_CBC_LOAN_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_LOAN_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOAN_CONTRACT_ID")
    private Long loanContractId;

    @Column(name = "CIF_NO")
    private String cifNo;

    @Column(name = "LOAN_ACCOUNT_NUMBER")
    private String loanAccountNumber;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_INFO")
    private String productInfo;

    @Column(name = "PAYMENT_CODE")
    private String paymentCode;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "OUTSTANDING_BALANCE")
    private BigDecimal outstandingBalance;

    @Column(name = "PAYMENT_AMT")
    private BigDecimal paymentAmt;

    @Column(name = "REMAINING_MONTHS")
    private Integer remainingMonths;

    @Column(name = "LIMIT_TERM")
    private Integer limitTerm;

    @Column(name = "DISBURSED_AMT")
    private BigDecimal disbursedAmt;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
