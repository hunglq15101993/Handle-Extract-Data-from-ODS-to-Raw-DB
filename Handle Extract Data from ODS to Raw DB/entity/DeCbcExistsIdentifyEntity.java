package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_EXISTS_IDENTIFICATION")
public class DeCbcExistsIdentifyEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_EXISTS_IDENTIFICATION",
            sequenceName = "SEQ_DE_CBC_EXISTS_IDENTIFICATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_EXISTS_IDENTIFICATION")
    @Column(name = "ID")
    private Long id;

    @Column(name = "GTTT")
    private String gttt;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CIF_NUMBER")
    private String cifNumber;

    @Column(name = "IDENTIFICATION_TYPE")
    private String identificationType;

    @Column(name = "AML_LIST_NAME")
    private String amlListName;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "CARD_NO")
    private String cardNo;

    @Column(name = "OPEN_DATE")
    private LocalDate openDate;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
