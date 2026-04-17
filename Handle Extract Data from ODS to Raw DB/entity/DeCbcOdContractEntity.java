package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_OD_CONTRACT")
public class DeCbcOdContractEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_OD_CONTRACT",
            sequenceName = "SEQ_DE_CBC_OD_CONTRACT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_OD_CONTRACT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @Column(name = "CIF_NO")
    private String cifNo;

    @Column(name = "OVERDRAFT_LIMIT")
    private BigDecimal overdraftLimit;

    @Column(name = "SECURED")
    private String secured;

    @Column(name = "COLLATCODE_FLAG")
    private String collatcodeFlag;

    @Column(name = "OVERDRAFT_EXPIRY_DATE")
    private LocalDate overdraftExpiryDate;

    @Column(name = "GTTT")
    private String gttt;

    @Column(name = "APPLICATION_CODE")
    private String applicationCode;

    @Column(name = "APPROVAL_TIME")
    private LocalDateTime approvalTime;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
