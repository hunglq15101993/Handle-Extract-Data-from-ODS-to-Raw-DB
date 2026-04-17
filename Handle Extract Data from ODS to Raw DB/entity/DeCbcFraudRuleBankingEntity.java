package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_FRAUD_RULE_BANKING")
public class DeCbcFraudRuleBankingEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_FRAUD_RULE_BANKING",
            sequenceName = "SEQ_DE_CBC_FRAUD_RULE_BANKING", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_FRAUD_RULE_BANKING")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_CLN")
    private String nameCln;

    @Column(name = "CONTACT_TYPE")
    private String contactType;

    @Column(name = "CONTACT_INFO")
    private String contactInfo;

    @Column(name = "CONTACT_INFO_CLN")
    private String contactInfoCln;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
