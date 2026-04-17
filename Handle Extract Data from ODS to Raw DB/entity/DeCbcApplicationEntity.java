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
@Table(name = "DE_CBC_APPLICATION")
public class DeCbcApplicationEntity {

    @Id
    @Column(name = "APPLICATION_ID")
    private String applicationId;

    @Column(name = "APPLICATION_CODE_MBA")
    private String applicationCodeMBA;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "APPLICATION_CODE_BPM")
    private String applicationCodeBPM;

    @Column(name = "APPLICATION_FLOW")
    private String applicationFlow;

    @Column(name = "APPLICATION_CREATE_TIME")
    private String applicationCreatedTime;

    @Column(name = "APPLICATION_SALE_ID")
    private String applicationSaleId;

    @Column(name = "RECOMMENDED_DECISION")
    private String recommendedDecision;

    @Column(name = "VERSION")
    private Long version;

    @CreationTimestamp
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

}
