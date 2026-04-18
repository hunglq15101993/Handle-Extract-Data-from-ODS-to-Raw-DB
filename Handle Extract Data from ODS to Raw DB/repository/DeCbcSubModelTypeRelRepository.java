package com.msb.stp.leadmanagement.oracle.cbcRaw.repository;

import com.msb.stp.leadmanagement.oracle.cbcRaw.entities.DeCbcSubModelTypeRelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeCbcSubModelTypeRelRepository extends JpaRepository<DeCbcSubModelTypeRelEntity,Long> {
}
