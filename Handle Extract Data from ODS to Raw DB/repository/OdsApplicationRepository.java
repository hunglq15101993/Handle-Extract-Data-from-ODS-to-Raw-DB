package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.OdsApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OdsApplicationRepository extends JpaRepository<OdsApplicationEntity,Long> {

    List<OdsApplicationEntity> findAllByApplicationDeRefNumber(String applicationDeRefNumber);

}
