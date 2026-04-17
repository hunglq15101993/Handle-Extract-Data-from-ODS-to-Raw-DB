package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_APPLICANT")
public class DeCbcApplicantEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_APPLICANT",
            sequenceName = "SEQ_DE_CBC_APPLICANT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_APPLICANT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "APPLICANT_TYPE")
    private String applicantType;

    @Column(name = "CUSTOMER_SEGMENT")
    private String customerSegment;

    @Column(name = "APPLICANT_NAME")
    private String applicantName;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "DOB")
    private LocalDate dateOfBirth;

    @Column(name = "AGE")
    private BigDecimal age;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "PHONE")
    private String phoneNumber;

    @Column(name = "EMAIL")
    private String emailID;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    @Column(name = "JOB")
    private String job;

    @Column(name = "AGENCY_POSITION")
    private String agencyPosition;

    @Column(name = "OVERALL_SCORE")
    private String overallScore;

    @Column(name = "RECORDED_INCOME")
    private String recordedIncome;

    @Column(name = "INCOME_FROM_MODEL")
    private String incomeFromModel;

    @Column(name = "MODEL_ACCEPT_BEFORE_MODEL_FLOW")
    private String modelAcceptBeforeModelFlow;

    @Column(name = "FLAG_ACCEPT_MODEL")
    private String flagAcceptModel;

    @Column(name = "MODEL_FLOW_POINT")
    private String modelFlowPoint;

    @Column(name = "OVERALL_MODEL_RATING")
    private String overallModelRating;

    @Column(name = "INCOME_METHOD")
    private String incomeMethod;

    @Column(name = "CONVERSION_METHOD")
    private String conversionMethod;

    @Column(name = "IS_NTB")
    private String isNTB;

    @Column(name = "IS_SECURE")
    private String isSecure;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
