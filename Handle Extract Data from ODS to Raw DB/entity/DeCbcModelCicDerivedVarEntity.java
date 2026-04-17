package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_MODEL_CIC_DERIVED_VARIABLE")
public class DeCbcModelCicDerivedVarEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_MODEL_CIC_DERIVED_VARIABLE",
            sequenceName = "SEQ_DE_CBC_MODEL_CIC_DERIVED_VARIABLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_MODEL_CIC_DERIVED_VARIABLE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "CIC_ID")
    private Long cicId;

    @Column(name = "VAR_NAME")
    private String varName;

    @Column(name = "VAR_VALUE")
    private String varValue;

    @Column(name = "\"GROUP\"")
    private String group;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
