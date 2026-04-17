package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_TYPE_QUES_GROUP")
public class DeCbcModelTypeQuesGroupEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_QUES_GROUP",
            sequenceName = "SEQ_DE_CBC_MODEL_TYPE_QUES_GROUP", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_QUES_GROUP")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @Column(name = "STD")
    private BigDecimal std;

    @Column(name = "MEAN")
    private BigDecimal mean;

    @Column(name = "SCORE")
    private BigDecimal score;

    @Column(name = "STANDARDIZE_SCORE")
    private BigDecimal standardizeScore;

    @Column(name = "STEP_FINAL")
    private String stepFinal;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
