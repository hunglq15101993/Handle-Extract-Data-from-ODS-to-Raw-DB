package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_APPLICATION_VARIABLE")
public class DeCbcApplicationVarEntity {

    @Id
    @SequenceGenerator(name = "DE_CBC_SEQ_APPLICATION_VARIABLE",
            sequenceName = "SEQ_DE_CBC_APPLICATION_VARIABLE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DE_CBC_SEQ_APPLICATION_VARIABLE")
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "VAR_NAME")
    private String varName;

    @Column(name = "VAR_VALUE")
    private String varValue;

    @Column(name = "VAR_TYPE")
    private String varType;

    @Column(name = "VAR_GROUP")
    private String varGroup;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
