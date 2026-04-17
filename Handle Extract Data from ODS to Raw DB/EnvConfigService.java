package com.msb.stp.leadmanagement.services;

import com.msb.stp.leadmanagement.entities.EnvConfigEntity;
import com.msb.stp.leadmanagement.repositories.EnvConfigRepository;
import com.msb.stp.leadmanagement.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnvConfigService {
    private final EnvConfigRepository envConfigRepository;

    private final ConversionService conversionService;

    public <T> T getEnvValue(String type, String key, T defaultValue) {
        String value = "";
        try {
            Optional<EnvConfigEntity> optional = envConfigRepository.findByTypeAndKey(type, key);
            if (optional.isPresent()) {
                value = optional.get().getValue();
                if (value != null) {
                    return (T) conversionService.convert(value, defaultValue.getClass());
                }
            }
        } catch (Exception e) {
            log.warn("⚠️ Failed to convert value '{}': ", value, e);
        }

        return defaultValue;
    }

    public String getEnvValue(String key, String type) {
        List<EnvConfigEntity> envConfigEntities = envConfigRepository.findAllByKeyAndType(key, type);
        if (CollectionUtils.isEmpty(envConfigEntities)) {
            log.info("GetEnvValueEntity IS INVALID Key=[{}].Type=[{}]", key, type);
            return null;
        }
        if (envConfigEntities.size() > 1) {
            List<Long> ids = envConfigEntities.stream().map(EnvConfigEntity::getId).toList();
            log.info("GetEnvValueEntity IS INVALID With id=[{}].Key=[{}].Type=[{}]", ids, key, type);
            return null;
        }

        EnvConfigEntity envConfigEntity = envConfigEntities.get(0);
        log.info("GetEnvValue With Type=[{}].Key=[{}] EnvValueEntity=[{}]", key, type,
                JsonUtil.writeValueAsString(envConfigEntities.get(0)));
        return envConfigEntity.getValue();
    }

    public void saveEnvValue(String type, String key, String value) {
        try {
            Optional<EnvConfigEntity> envConfigEntityOptional = envConfigRepository.findByTypeAndKey(type, key);
            if (envConfigEntityOptional.isPresent() && envConfigEntityOptional.get().getValue() != null) {
                EnvConfigEntity envConfigEntity = envConfigEntityOptional.get();
                envConfigEntity.setValue(value);
                envConfigRepository.save(envConfigEntityOptional.get());
            }
            log.info("saveEnvValue runType={} key={} value={}", type, key, value);
        } catch (Exception e) {
            log.error("saveEnvValue runType={} key={} value={}", type, key, value, e);
        }

    }

}
