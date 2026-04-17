package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_SUB_MODEL_AFFECT_RESULT")
public class DeCbcSubModelAffectResultEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_AFFECT_RESULT",
            sequenceName = "SEQ_DE_CBC_SUB_MODEL_AFFECT_RESULT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_AFFECT_RESULT")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "PRINCIPAL_CODE")
    private String principalCode;

    @Column(name = "SCORE_AFTER_SUB")
    private BigDecimal scoreAfterSub;

    @Column(name = "RANK_AFTER_SUB")
    private BigDecimal rankAfterSub;

    @Column(name = "AFFECT_SCORE_TYPE")
    private String affectScoreType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
