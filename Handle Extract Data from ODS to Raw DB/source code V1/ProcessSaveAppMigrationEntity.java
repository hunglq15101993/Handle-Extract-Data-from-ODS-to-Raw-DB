package com.msb.stp.leadmanagement.entities;

import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "process_save_application_migration")
@EntityListeners(AuditingEntityListener.class)
@Data
@DynamicUpdate
public class ProcessSaveAppMigrationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "kafka_message")
    private String kafkaMessage;

    @Column(name = "ods_request_payload")
    private String odsRequestPayload;

    @Column(name = "event_request_payload")
    private String eventRequestPayload;

    @Column(name = "model_request_payload")
    private String modelRequestPayload;

    @Column(name = "status")
    private String status;

    @Column(name = "current_step")
    private String currentStep;

    @Column(name = "retry_count", nullable = false)
    private Integer retryCount = 0;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "update_payLoad")
    private String updatePayLoad;

    @Column(name = "version")
    private Long version;

    @Column(name = "enable_retry", nullable = false)
    private String enableRetry = CaseProcess.OFF_RETRY.getCode();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
