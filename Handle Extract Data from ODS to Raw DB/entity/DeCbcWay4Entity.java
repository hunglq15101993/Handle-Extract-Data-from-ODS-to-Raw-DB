package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_WAY4")
public class DeCbcWay4Entity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_WAY4", sequenceName = "SEQ_DE_CBC_WAY4", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_WAY4")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CODE")
    private Integer code;

    @Column(name = "REQUEST_TIMESTAMP")
    private String requestTimeStamp;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @Column(name = "CONTRACT_STATUS")
    private String contractStatus;

    @Column(name = "CONTRACT_STATUS_CODE")
    private String contractStatusCode;

    @Column(name = "DATE_OPEN")
    private String dateOpen;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "CONTRACT_LIMIT")
    private BigDecimal contractLimit;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
