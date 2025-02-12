package com.example.apistatusmonitor.config;

import com.example.apistatusmonitor.config.repository.DBConfigRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DBConfigManager {

    private final ConcurrentHashMap<Long, DBConfig> configMap;
    private final DBConfigRepository dbConfigRepository;

    public DBConfigManager(DBConfigRepository dbConfigRepository) {
        this.configMap = new ConcurrentHashMap<>();
        this.dbConfigRepository = dbConfigRepository;
    }

    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        List<DBConfig> configs = dbConfigRepository.findAll();
        configs.forEach(config -> configMap.put(config.getId(), config));
    }

    public Long addConfig(DBConfig dbConfig) {
        DBConfig savedConfig = dbConfigRepository.save(dbConfig);
        configMap.put(dbConfig.getId(), dbConfig);
        return savedConfig.getId();
    }

    public Long updateConfig(Long configId, DBConfig dbConfig) {
        DBConfig existedConfig = dbConfigRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("DB config not found with id: " + configId));
        existedConfig.updateConfig(dbConfig);
        dbConfigRepository.save(existedConfig);
        configMap.put(dbConfig.getId(), dbConfig);
        return existedConfig.getId();
    }

    public void deleteConfig(Long id) {
        dbConfigRepository.deleteById(id);
        configMap.remove(id);
    }

    public List<DBConfig> getConfigList() {
        return List.copyOf(configMap.values());
    }
}