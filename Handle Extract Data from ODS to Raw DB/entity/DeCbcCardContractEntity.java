package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_CARD_CONTRACT")
public class DeCbcCardContractEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CARD_CONTRACT",
            sequenceName = "SEQ_DE_CBC_CARD_CONTRACT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CARD_CONTRACT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @Column(name = "CONTRACT_ID")
    private String contractId;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "AUTH_LIMIT_AMOUNT")
    private BigDecimal authLimitAmount;

    @Column(name = "GTTT")
    private String gttt;

    @Column(name = "APPLICATION_CODE")
    private String applicationCode;

    @Column(name = "SECURED")
    private String secured;

    @Column(name = "COLLATCODE_FLAG")
    private String collatCodeFlag;

    @Column(name = "APPROVAL_TIME")
    private LocalDateTime approvalTime;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
