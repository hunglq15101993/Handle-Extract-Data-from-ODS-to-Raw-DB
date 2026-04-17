package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsModelExecutionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdsModelExecutionLogRepository extends JpaRepository<OdsModelExecutionLogEntity,Long> {

    List<OdsModelExecutionLogEntity> findAllByApplicationDeRefNumber(String applicationDeRefNumber);
}
