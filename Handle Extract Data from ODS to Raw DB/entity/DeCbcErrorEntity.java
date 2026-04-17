package com.msb.stp.leadmanagement.oracle.cbcRaw.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "DE_CBC_ERROR")
public class DeCbcErrorEntity {

    @Id
    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "ERROR_CODE")
    private String errorCode;

    @Column(name = "ERROR_DESCRIPTION")
    private String errorDescription;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "DATETIME")
    private String datetime;

    @Column(name = "TRACE")
    private String trace;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
