package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_THIRD_SYSTEM_SCORE")
public class DeCbcThirdSystemScoreEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_SYSTEM_SCORE",
            sequenceName = "SEQ_DE_CBC_THIRD_SYSTEM_SCORE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_SYSTEM_SCORE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "SCORE_TYPE")
    private String scoreType;

    @Column(name = "PARTNER_STATUS")
    private String partnerStatus;

    @Column(name = "PARTNER_CODE")
    private String partnerCode;

    @Column(name = "PARTNER_DESC")
    private String partnerDesc;

    @Column(name = "SCORE")
    private BigDecimal score;

    @Column(name = "VERSION")
    private String version;

    @Column(name = "RISKS")
    private String risks;

    @Column(name = "RESPONSE_TIME")
    private LocalDateTime responseTime;

    @Column(name = "TRACKING_ID")
    private String trackingId;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
