package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_C06")
public class DeCbcC06Entity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_C06",
            sequenceName = "SEQ_DE_CBC_C06", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_C06")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CARD_NO")
    private String cardNo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "OLD_ID_CARD_NO")
    private String oldIdCardNo;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PLACE_OF_ORIGIN")
    private String placeOfOrigin;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "SPOUSE_NAME")
    private String spouseName;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
