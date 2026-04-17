package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_OVERDUE_ACCOUNT")
public class DeCbcOverdueAccountEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_OVERDUE_ACCOUNT",
            sequenceName = "SEQ_DE_CBC_OVERDUE_ACCOUNT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_OVERDUE_ACCOUNT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICANT_REFER_ID")
    private Long applicantReferId;

    @Column(name = "CIF")
    private String cif;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "ACCOUNT_TYPE")
    private String accountType;

    @Column(name = "BALANCE_PD_DAY")
    private String balancePdDay;

    @Column(name = "INTEREST_PD_DAY")
    private String interestPdDay;

    @Column(name = "FINAL_PD_DAY")
    private String finalPdDay;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
