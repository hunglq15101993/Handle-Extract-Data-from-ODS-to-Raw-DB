package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcFraudRuleRbApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeCbcFraudRuleRbApprovalRepository extends JpaRepository<DeCbcFraudRuleRbApprovalEntity,Long> {
}
