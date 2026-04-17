package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_SCORING_CONSTANT")
public class DeCbcModelScoringConstantEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_SCORING_CONSTANT",
            sequenceName = "SEQ_DE_CBC_MODEL_SCORING_CONSTANT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_SCORING_CONSTANT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "CONSTANT_CODE")
    private String constantCode;

    @Column(name = "CONSTANT_VALUE")
    private BigDecimal constantValue;

    @Column(name = "CONSTANT_ORDER")
    private Integer constantOrder;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
