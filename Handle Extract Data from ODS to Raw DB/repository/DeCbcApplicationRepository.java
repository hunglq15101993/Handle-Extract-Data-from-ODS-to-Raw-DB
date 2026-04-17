package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeCbcApplicationRepository extends JpaRepository<DeCbcApplicationEntity, String> {

    DeCbcApplicationEntity findByApplicationId(String applicationId);

}
