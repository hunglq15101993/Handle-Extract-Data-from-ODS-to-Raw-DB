package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_IDENTIFICATION")
public class DeCbcIdentificationEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CBC_IDENTIFICATION",
            sequenceName = "SEQ_DE_CBC_IDENTIFICATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CBC_IDENTIFICATION")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "IS_MAIN_IDENTIFICATION")
    private String isMainIdentification;

    @Column(name = "IDENTIFICATION_NUMBER")
    private String identificationNumber;

    @Column(name = "PLACE_OF_ISSUE")
    private String placeOfIssue;

    @Column(name = "DATE_OF_ISSUE")
    private LocalDate dateOfIssue;

    @Column(name = "DATE_OF_EXPIRE")
    private LocalDate dateOfExpire;

    @Column(name = "IDENTIFICATION_TYPE_MAIN")
    private String identificationTypeMain;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
