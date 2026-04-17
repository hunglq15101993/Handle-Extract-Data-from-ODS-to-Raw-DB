package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "DE_CBC_SUB_MODEL_TYPE_REL")
public class DeCbcSubModelTypeRelEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_TYPE_REL",
            sequenceName = "SEQ_DE_CBC_SUB_MODEL_TYPE_REL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_TYPE_REL")
    @Column(name = "ID")
    private Long id;

    @Column(name = "MODEL_INFO_ID")
    private Long modelInfoId;

    @Column(name = "SUB_MODEL_TYPE_CODE")
    private String subModelTypeCode;

    @Column(name = "AFFECT_TYPE")
    private String affectType;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
