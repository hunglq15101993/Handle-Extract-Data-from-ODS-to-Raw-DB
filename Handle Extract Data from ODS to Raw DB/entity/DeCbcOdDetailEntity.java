package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_OD_DETAIL")
public class DeCbcOdDetailEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_OD_DETAIL",
            sequenceName = "SEQ_DE_CBC_OD_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_OD_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "OD_CONTRACT_ID")
    private Long odContractId;

    @Column(name = "CIF_NO")
    private String cifNo;

    @Column(name = "OVERDRAFT_ACCOUNT")
    private String overdraftAccount;

    @Column(name = "OVERDRAFT_OUTSTANDING_BALANCE")
    private BigDecimal overdraftOutstandingBalance;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_INFO")
    private String productInfo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
