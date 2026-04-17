package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ODS_MODEL_EXECUTION_LOG")
public class OdsModelExecutionLogEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_DE_REF_NUMBER")
    private String applicationDeRefNumber;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

    @Column(name = "CALL_TYPE")
    private String callType;

    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "INPUT")
    private String input;

    @Column(name = "OUTPUT")
    private String output;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

}
