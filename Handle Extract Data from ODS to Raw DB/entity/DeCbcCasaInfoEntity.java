package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_CASA_INFO")
public class DeCbcCasaInfoEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CASA_INFO",
            sequenceName = "SEQ_DE_CBC_CASA_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CASA_INFO")
    @Column(name = "ID")
    private Long id;

    @Column(name = "LEAD_REFER_ID")
    private Long leadReferId;

    @Column(name = "INCOME_POLICY_CODE")
    private String incomePolicyCode;

    @Column(name = "SALARY_MSB_01")
    private BigDecimal salaryMsb01;

    @Column(name = "SALARY_MSB_02")
    private BigDecimal salaryMsb02;

    @Column(name = "SALARY_MSB_03")
    private BigDecimal salaryMsb03;

    @Column(name = "CASA_BALANCE_01")
    private BigDecimal casaBalance01;

    @Column(name = "CASA_BALANCE_02")
    private BigDecimal casaBalance02;

    @Column(name = "CASA_BALANCE_03")
    private BigDecimal casaBalance03;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
