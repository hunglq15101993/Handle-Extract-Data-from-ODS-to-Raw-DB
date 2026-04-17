package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_CARD_DETAIL")
public class DeCbcCardDetailEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CARD_DETAIL",
            sequenceName = "SEQ_DE_CBC_CARD_DETAIL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CARD_DETAIL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CARD_CONTRACT_ID")
    private Long cardContractId;

    @Column(name = "CIF_NO")
    private String cifNo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PRODUCT_CODE")
    private String productCode;

    @Column(name = "PRODUCT_INFO")
    private String productInfo;

    @Column(name = "SOURCE_SYSTEM")
    private String sourceSystem;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
