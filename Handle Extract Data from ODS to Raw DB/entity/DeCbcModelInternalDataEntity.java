package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_INTERNAL_DATA")
public class DeCbcModelInternalDataEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_INTERNAL_DATA",
            sequenceName = "SEQ_DE_CBC_MODEL_INTERNAL_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_INTERNAL_DATA")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "VAR_NAME")
    private String varName;

    @Column(name = "VAR_VALUE")
    private String varValue;

    @Column(name = "ACC_TYPE")
    private String accType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
