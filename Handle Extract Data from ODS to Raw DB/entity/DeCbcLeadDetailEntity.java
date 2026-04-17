package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_LEAD_DETAIL")
public class DeCbcLeadDetailEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_LEAD_DETAIL",
            sequenceName = "SEQ_DE_CBC_LEAD_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_LEAD_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "LEAD_ID")
    private String leadId;

    @Column(name = "LEAD_NAME")
    private String leadName;

    @Column(name = "LEAD_TYPE")
    private String leadType;

    @Column(name = "LEAD_LIMIT")
    private BigDecimal leadLimit;

    @Column(name = "CREDIT_CARD_LIMIT")
    private BigDecimal creditCardLimit;

    @Column(name = "EFFECTIVE_DATE")
    private LocalDateTime effectiveDate;

    @Column(name = "EXPERY_DATE")
    private LocalDateTime expireDate;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
