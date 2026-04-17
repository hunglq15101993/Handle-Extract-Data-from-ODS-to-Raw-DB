package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_FRAUD_RULE_CAPITAL")
public class DeCbcFraudRuleCapitalEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_FRAUD_RULE_CAPITAL",
            sequenceName = "SEQ_DE_CBC_FRAUD_RULE_CAPITAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_FRAUD_RULE_CAPITAL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "MSB_EQUITY")
    private String msbEquity;

    @Column(name = "EFFECTIVE_DATE")
    private String effectiveDate;

    @Column(name = "STATUS")
    private String status;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
