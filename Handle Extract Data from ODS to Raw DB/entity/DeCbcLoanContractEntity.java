package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_LOAN_CONTRACT")
public class DeCbcLoanContractEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_LOAN_CONTRACT",
            sequenceName = "SEQ_DE_CBC_LOAN_CONTRACT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_LOAN_CONTRACT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "ACF_STATUS")
    private String acfStatus;

    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    @Column(name = "AMOUNT_APPROVED")
    private BigDecimal amountApproved;

    @Column(name = "APPROVAL_TIME")
    private LocalDateTime approvalTime;

    @Column(name = "SECURED")
    private String secured;

    @Column(name = "COLLATCODE_FLAG")
    private String collatCodeFlag;

    @Column(name = "REVOL_NON_REVOL")
    private String revolNonRevol;

    @Column(name = "GTTT")
    private String gttt;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
