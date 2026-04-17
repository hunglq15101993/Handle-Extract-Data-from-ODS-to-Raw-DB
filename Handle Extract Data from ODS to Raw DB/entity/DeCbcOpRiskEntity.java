package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_OPRISK")
public class DeCbcOpRiskEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CBC_OPRISK",
            sequenceName = "SEQ_DE_CBC_OPRISK", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CBC_OPRISK")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "ID_OPRISK")
    private Long idOpRisk;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BRANCH_INPUT")
    private String branchInput;

    @Column(name = "CUS_ID")
    private String cusId;

    @Column(name = "DATE_EX")
    private LocalDate dateEx;

    @Column(name = "DATE_INPUT")
    private LocalDate dateInput;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "DSCN")
    private String dscn;

    @Column(name = "DTCN")
    private String dtcn;

    @Column(name = "ERROR_MESS")
    private String errorMess;

    @Column(name = "INPUT_USER")
    private String inputUser;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RECORD_ID")
    private String recordId;

    @Column(name = "RESULT")
    private String result;

    @Column(name = "VERSION")
    private String version;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
