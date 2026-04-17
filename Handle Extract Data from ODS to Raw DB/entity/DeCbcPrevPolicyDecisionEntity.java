package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_PREV_POLICY_DECISION")
public class DeCbcPrevPolicyDecisionEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_POLICY_DECISION",
            sequenceName = "SEQ_DE_CBC_PREV_POLICY_DECISION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_POLICY_DECISION")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "PREVIOS_APPLICATION_ID")
    private String prevApplicationId;

    @Column(name = "APPLICANT_ID")
    private String applicantId;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "RESULT_DATE")
    private LocalDate resultDate;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
