package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_FRAUD_RULE_RB_APPROVAL")
public class DeCbcFraudRuleRbApprovalEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_FRAUD_RULE_RB_APPROVAL",
            sequenceName = "SEQ_DE_CBC_FRAUD_RULE_RB_APPROVAL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_FRAUD_RULE_RB_APPROVAL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "APPLICATION_CODE")
    private String applicationCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_CLN")
    private String nameCln;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "PHONE_CLN")
    private String phoneCln;

    @Column(name = "RELATION_CODE")
    private String relationCode;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
