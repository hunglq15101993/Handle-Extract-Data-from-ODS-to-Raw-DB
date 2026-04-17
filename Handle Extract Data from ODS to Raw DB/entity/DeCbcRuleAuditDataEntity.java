package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_RULE_AUDIT_DATA")
public class DeCbcRuleAuditDataEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_AUDIT_DATA",
            sequenceName = "SEQ_DE_CBC_RULE_AUDIT_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_AUDIT_DATA")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "METAPHOR_NAME")
    private String metaphorName;

    @Column(name = "RULE_NAME")
    private String ruleName;

    @Column(name = "OUTCOME")
    private String outcome;

    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
