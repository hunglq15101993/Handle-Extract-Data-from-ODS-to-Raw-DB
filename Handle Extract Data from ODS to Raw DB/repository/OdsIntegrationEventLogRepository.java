package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsIntegrationEventLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdsIntegrationEventLogRepository extends JpaRepository<OdsIntegrationEventLogEntity,Long> {

    List<OdsIntegrationEventLogEntity> findAllByApplicationDeRefNumber(String applicationDeRefNumber);
}
