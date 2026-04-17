package com.msb.stp.leadmanagement.oracle.cbcMart.repository;

import com.msb.stp.leadmanagement.constants.AppContant;
import com.msb.stp.leadmanagement.constants.enums.CaseProcess;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppEntity;
import com.msb.stp.leadmanagement.entities.ProcessSaveAppMigrationEntity;
import com.msb.stp.leadmanagement.repositories.ProcessSaveAppMigrationRepository;
import com.msb.stp.leadmanagement.repositories.ProcessSaveAppRepository;
import com.msb.stp.leadmanagement.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Repository
@Slf4j
public class CbcMartJdbcRepository {
    private final ProcessSaveAppRepository processSaveAppRepository;

    private final ProcessSaveAppMigrationRepository processSaveAppMigrationRepository;
    @Autowired
    @Qualifier("oracleJdbcTemplateDeCbcMart")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier("processCaseAppExecutor")
    private Executor executor;

    public void insertRawToMARTASync(ProcessSaveAppEntity saveAppEntity, String traceId) {
        CompletableFuture.supplyAsync(() -> {
            try {
                MDC.put(AppContant.REQUEST_ID, traceId);
                saveAppEntity.setCurrentStep(CaseProcess.INSERT_CBC_MART.getCode());
                saveAppEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
                saveAppEntity.setStatus(CaseProcess.SUCCESS.getCode());
                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                        .withSchemaName("DE_CBC_MART")
                        .withCatalogName("PKG_CBC_AGG_FR_RAW")
                        .withProcedureName("PRC_CBC_MAIN");
//                jdbcCall.getJdbcTemplate().setQueryTimeout(60);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String reportDate = LocalDate.now().format(formatter);
                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("P_APPLICATION_ID", saveAppEntity.getApplicationId())
                        .addValue("P_REPORT_DATE", reportDate);

                Map<String, Object> result = jdbcCall.execute(params);
                String message = JsonUtil.writeValueAsString(result.get("P_STATUS_MESSAGE"));
                if (StringUtils.isNotEmpty(message) && message.contains(CaseProcess.FAILURE.getCode())) {
                    saveAppEntity.setStatus(CaseProcess.FAILED.getCode());
                    saveAppEntity.setErrorMessage(JsonUtil.writeValueAsString(result));
                }
                log.info("insertRawToMART Complete!");

            } catch (Exception e) {
                log.error("Call PROCEDURE insertRawToMART error", e);
                StringBuilder ex = new StringBuilder(e.getMessage());
                if (e.getCause() != null && e.getCause().getCause() != null) {
                    ex.append(": ").append(e.getCause().getCause());
                }
                saveAppEntity.setStatus(CaseProcess.FAILED.getCode());
                saveAppEntity.setErrorMessage(ex.toString());
            } finally {
                MDC.clear();
            }
            return processSaveAppRepository.save(saveAppEntity).getId();
        }, executor);
    }

    public void insertRawToMARTByAppMigrationASync(ProcessSaveAppMigrationEntity saveAppMigrationEntity, String traceId) {
        CompletableFuture.supplyAsync(() -> {
            try {
                MDC.put(AppContant.REQUEST_ID, traceId);
                saveAppMigrationEntity.setCurrentStep(CaseProcess.INSERT_CBC_MART.getCode());
                saveAppMigrationEntity.setEnableRetry(CaseProcess.OFF_RETRY.getCode());
                saveAppMigrationEntity.setStatus(CaseProcess.SUCCESS.getCode());
                SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                        .withSchemaName("DE_CBC_MART")
                        .withCatalogName("PKG_CBC_AGG_FR_RAW")
                        .withProcedureName("PRC_CBC_MAIN");
//                jdbcCall.getJdbcTemplate().setQueryTimeout(60);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String reportDate = LocalDate.now().format(formatter);
                MapSqlParameterSource params = new MapSqlParameterSource()
                        .addValue("P_APPLICATION_ID", saveAppMigrationEntity.getApplicationId())
                        .addValue("P_REPORT_DATE", reportDate);

                Map<String, Object> result = jdbcCall.execute(params);
                String message = JsonUtil.writeValueAsString(result.get("P_STATUS_MESSAGE"));
                if (StringUtils.isNotEmpty(message) && message.contains(CaseProcess.FAILURE.getCode())) {
                    saveAppMigrationEntity.setStatus(CaseProcess.FAILED.getCode());
                    saveAppMigrationEntity.setErrorMessage(JsonUtil.writeValueAsString(result));
                }
                log.info("insertRawToMART Complete!");

            } catch (Exception e) {
                log.error("Call PROCEDURE insertRawToMART error", e);
                StringBuilder ex = new StringBuilder(e.getMessage());
                if (e.getCause() != null && e.getCause().getCause() != null) {
                    ex.append(": ").append(e.getCause().getCause());
                }
                saveAppMigrationEntity.setStatus(CaseProcess.FAILED.getCode());
                saveAppMigrationEntity.setErrorMessage(ex.toString());
            } finally {
                MDC.clear();
            }
            return processSaveAppMigrationRepository.save(saveAppMigrationEntity).getId();
        }, executor);
    }
}
