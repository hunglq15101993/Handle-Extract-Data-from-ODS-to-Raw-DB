package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_UW_CRITERIA")
public class DeCbcUwCriteriaEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CRITERIA",
            sequenceName = "SEQ_DE_CBC_UW_CRITERIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CRITERIA")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PREV_POLICY_DECISION_ID")
    private Long prevPolicyDecisionId;

    @Column(name = "\"GROUP\"")
    private String group;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "REASON_CODE")
    private String reasonCode;

    @Column(name = "REASON_DESCRIPTION")
    private String reasonDescription;

    @Column(name = "RULE_CATEGORY")
    private String ruleCategory;

    @Column(name = "APPLICANT_TYPE")
    private String applicantType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
