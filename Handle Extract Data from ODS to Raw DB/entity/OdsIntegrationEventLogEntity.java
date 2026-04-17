package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "ODS_INTEGRATION_EVENT_LOG")
public class OdsIntegrationEventLogEntity {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "APPLICATION_DE_REF_NUMBER")
    private String applicationDeRefNumber;

    @Column(name = "APPLICANT_MAIN_ID")
    private String applicantMainId;

    @Column(name = "APPLICANT_ID")
    private String applicantId;

    @Column(name = "APPLICANT_TYPE")
    private String applicantType;

    @Column(name = "CALL_TYPE")
    private String callType;

    @Column(name = "SYSTEM")
    private String system;

    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;

    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "START_DATETIME")
    private LocalDateTime startDatetime;

    @Column(name = "END_DATETIME")
    private LocalDateTime endDatetime;

    @Column(name = "CREATED_DATETIME")
    private LocalDateTime createdDatetime;

}
