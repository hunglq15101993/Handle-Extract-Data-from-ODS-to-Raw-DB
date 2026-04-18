package com.msb.stp.leadmanagement.repositories;

import com.msb.stp.leadmanagement.entities.ProcessSaveAppMigrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessSaveAppMigrationRepository extends JpaRepository<ProcessSaveAppMigrationEntity, Long> {

    List<ProcessSaveAppMigrationEntity> findAllByApplicationId(String appId);

    @Query(value = "select * from process_save_application_migration psa " +
            "where  psa.enable_retry = '1' " +
            "AND psa.status = 'FAILED' " +
            "AND psa.updated_at BETWEEN " +
            "       ((CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Ho_Chi_Minh') - INTERVAL '1 days' * :days) " +
            "   AND ((CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Ho_Chi_Minh') - INTERVAL '1 minutes' * :minutes) ", nativeQuery = true)
    List<ProcessSaveAppMigrationEntity> findProcessStepByRetry(@Param("days") int days,
                                                      @Param("minutes") int minutes);

    @Query(value = "select * from process_save_application_migration psa " +
            "where psa.status = 'FAILED' " +
            "AND (psa.retry_count >= :retryCount OR psa.current_step = 'INSERT_CBC_MART') " +
            "AND psa.updated_at >= ((CURRENT_TIMESTAMP AT TIME ZONE 'Asia/Ho_Chi_Minh') - INTERVAL '1 hour' * :hour) "
            , nativeQuery = true)
    List<ProcessSaveAppMigrationEntity> findProcessByError(@Param("retryCount") int retryCount,
                                                  @Param("hour") int hour);

    @Query(value = "SELECT * FROM move_process_save_app(:days)", nativeQuery = true)
    Object moveAndDeleteProcessApp(@Param("days") int days);

}
