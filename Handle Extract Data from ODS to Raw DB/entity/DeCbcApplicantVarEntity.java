package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_APPLICANT_VARIABLE")
public class DeCbcApplicantVarEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_APPLICANT_VARIABLE",
            sequenceName = "SEQ_DE_CBC_APPLICANT_VARIABLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_APPLICANT_VARIABLE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "VAR_NAME")
    private String varName;

    @Column(name = "VAR_VALUE")
    private String varValue;

    @Column(name = "VAR_TYPE")
    private String varType;

    @Column(name = "VAR_GROUP")
    private String varGroup;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
