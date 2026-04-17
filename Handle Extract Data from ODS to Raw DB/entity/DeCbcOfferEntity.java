package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_OFFER")
public class DeCbcOfferEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CBC_OFFER",
            sequenceName = "SEQ_DE_CBC_OFFER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CBC_OFFER")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "APPROVED_CREDIT_OR_LOAN_LIMIT")
    private BigDecimal approvedCreditOrLoanLimit;

    @Column(name = "APPROVAL_TERM")
    private Integer approvalTerm;

    @Column(name = "APPROVAL_VALIDITY")
    private LocalDate approvalValidity;

    @Column(name = "APPROVAL_TIME")
    private LocalDateTime approvalTime;

    @Column(name = "INTEREST_RATE")
    private BigDecimal interestRate;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
