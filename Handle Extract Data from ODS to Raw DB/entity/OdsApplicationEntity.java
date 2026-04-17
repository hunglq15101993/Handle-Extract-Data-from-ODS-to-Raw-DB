package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ODS_APPLICATION")
public class OdsApplicationEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CIF_NO")
    private Long cifNo;

    @Column(name = "MAIN_IDENTIFICATION_NUMBER")
    private String mainIdentificationNumber;

    @Column(name = "APPLICATION_DE_REF_NUMBER")
    private String applicationDeRefNumber;

    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;

    @Column(name = "UPDATED_DATETIME")
    private LocalDateTime updatedDateTime;

}
