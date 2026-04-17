package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ODS_APPLICATION_STATE")
public class OdsApplicationStateEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_DE_REF_NUMBER")
    private String applicationDeRefNumber;

    @Column(name = "SOURCE_SYSTEM_ID")
    private String sourceSystemId;

    @Column(name = "STATE")
    private String state;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDatetime;
}
