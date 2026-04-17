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
@Table(name = "DE_CBC_MODEL_CIC")
public class DeCbcModelCicEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_CIC",
            sequenceName = "SEQ_DE_CBC_MODEL_CIC", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_CIC")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CLIENT_QUESTION_ID")
    private String clientQuestionId;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "REPORT_DATE")
    private LocalDateTime reportDate;

    @Column(name = "TRANSACTION_TIME")
    private LocalDateTime transactionTime;

    @Column(name = "CODE_MESSAGE")
    private String codeMessage;

    @Column(name = "TAX_NO")
    private String taxNo;

    @Column(name = "BUSINESS_REGISTRATION_NUMBER")
    private String businessRegistrationNumber;

    @Column(name = "CUSTOMER_IDENTIFICATION_NUMBER")
    private String customerIdentificationNumber;

    @Column(name = "CIC_CODE")
    private String cicCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "DIRECTOR_NAME")
    private String directorName;

    @Column(name = "FINANCIAL_YEAR")
    private String financialYear;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "\"GROUP\"")
    private String group;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
