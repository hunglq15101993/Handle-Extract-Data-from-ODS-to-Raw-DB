package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_INCOME_DETAIL")
public class DeCbcIncomeDetailEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_INCOME_DETAIL",
            sequenceName = "SEQ_DE_CBC_INCOME_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_INCOME_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "INCOME_MONTH")
    private String incomeMonth;

    @Column(name = "INCOME_TYPE")
    private String incomeType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
