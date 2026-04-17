package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ODS_PREVIOUS_POLICY_DECISION")
public class OdsPreviousPolicyDecisionEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_DE_REF_NUMBER")
    private String applicationDeRefNumber;

    @Column(name = "APPLICANT_IDS")
    private String applicantIds;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "RULE_ACTION")
    private String ruleAction;

    @Column(name = "REASON_CODE")
    private String reasonCode;

    @Column(name = "REASON_DESCRIPTION")
    private String reasonDescription;

    @Column(name = "RULE_CATEGORY")
    private String ruleCategory;

    @Column(name = "APPLICANT_TYPE")
    private String applicantType;

    @Column(name = "RESULT_DATE")
    private LocalDateTime resultDate;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

}
