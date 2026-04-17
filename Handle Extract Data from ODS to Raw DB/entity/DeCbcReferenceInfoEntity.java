package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_REFERENCE_INFO")
public class DeCbcReferenceInfoEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_REFERENCE_INFO",
            sequenceName = "SEQ_DE_CBC_REFERENCE_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_REFERENCE_INFO")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "REFERENCE_RELATIONSHIP")
    private String referenceRelationship;

    @Column(name = "REFERENCE_NAME")
    private String referenceName;

    @Column(name = "REFERENCE_PHONE")
    private String referencePhone;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
