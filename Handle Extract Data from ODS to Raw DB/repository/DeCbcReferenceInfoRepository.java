package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcReferenceInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeCbcReferenceInfoRepository extends JpaRepository<DeCbcReferenceInfoEntity,Long> {
}
