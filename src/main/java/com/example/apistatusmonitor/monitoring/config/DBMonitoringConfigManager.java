package com.example.apistatusmonitor.monitoring.config;

import com.example.apistatusmonitor.monitoring.config.repository.DBMonitoringConfigRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DBMonitoringConfigManager {

    private final ConcurrentHashMap<Long, DBMonitoringConfig> configMap;
    private final DBMonitoringConfigRepository dbMonitoringConfigRepository;

    public DBMonitoringConfigManager(DBMonitoringConfigRepository dbMonitoringConfigRepository) {
        this.configMap = new ConcurrentHashMap<>();
        this.dbMonitoringConfigRepository = dbMonitoringConfigRepository;
    }

    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        List<DBMonitoringConfig> configs = dbMonitoringConfigRepository.findAll();
        configs.forEach(config -> configMap.put(config.getId(), config));
    }

    public Long addConfig(DBMonitoringConfig dbMonitoringConfig) {
        DBMonitoringConfig savedConfig = dbMonitoringConfigRepository.save(dbMonitoringConfig);
        configMap.put(dbMonitoringConfig.getId(), dbMonitoringConfig);
        return savedConfig.getId();
    }

    public Long updateConfig(Long configId, DBMonitoringConfig dbMonitoringConfig) {
        DBMonitoringConfig existedConfig = dbMonitoringConfigRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("DB config not found with id: " + configId));
        existedConfig.updateConfig(dbMonitoringConfig);
        dbMonitoringConfigRepository.save(existedConfig);
        configMap.put(dbMonitoringConfig.getId(), dbMonitoringConfig);
        return existedConfig.getId();
    }

    public void deleteConfig(Long id) {
        dbMonitoringConfigRepository.deleteById(id);
        configMap.remove(id);
    }

    public List<DBMonitoringConfig> getConfigList() {
        return List.copyOf(configMap.values());
    }
}