package com.example.apistatusmonitor.config;

import com.example.apistatusmonitor.config.repository.ApiConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ApiConfigManager {
    private final ConcurrentHashMap<Long, ApiConfig> apiConfigMap = new ConcurrentHashMap<>();
    private final ApiConfigRepository apiConfigRepository;

    @PostConstruct
    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        apiConfigRepository.findAll()
                .forEach(apiConfig ->
                        apiConfigMap.put(apiConfig.getId(), apiConfig));
    }

    public Long addConfig(ApiConfig apiConfig) {
        apiConfigRepository.save(apiConfig);
        apiConfigMap.put(apiConfig.getId(), apiConfig);
        return apiConfig.getId();
    }

    public Long updateConfig(Long configId, ApiConfig apiConfig) {
        ApiConfig updateConfig = apiConfigRepository.findById(configId).orElseThrow(
                () -> new IllegalArgumentException("Api config not found"));
        updateConfig.updateConfig(apiConfig);
        apiConfigMap.replace(configId, updateConfig);
        return updateConfig.getId();
    }

    public void deleteConfig(Long apiId) {
        apiConfigRepository.deleteById(apiId);
        apiConfigMap.remove(apiId);
    }

    public List<ApiConfig> getConfigList() {
        return new ArrayList<>(apiConfigMap.values());
    }
}
