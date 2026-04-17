package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_SUB_MODEL_CRITERION_MAP")
public class DeCbcSubModelCriterionMapEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_CRITERION_MAP",
            sequenceName = "SEQ_DE_CBC_SUB_MODEL_CRITERION_MAP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_CRITERION_MAP")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "PRINCIPAL_CODE")
    private String principalCode;

    @Column(name = "SUB_SCORE")
    private BigDecimal subScore;

    @Column(name = "SUB_RANK")
    private BigDecimal subRank;

    @Column(name = "SUB_QUESTION_CODE")
    private String subQuestionCode;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
