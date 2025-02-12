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
        apiConfigMap.clear();
        apiConfigRepository.findAll()
                .forEach(apiConfig ->
                        apiConfigMap.put(apiConfig.getId(), apiConfig));
    }

    public Long addConfig(ApiConfig apiConfig) {
        ApiConfig savedConfig = apiConfigRepository.save(apiConfig);
        apiConfigMap.put(savedConfig.getId(), savedConfig);
        return savedConfig.getId();
    }

    public Long updateConfig(Long configId, ApiConfig newConfigData) {
        // 기존 설정을 조회하고, 없으면 예외 발생 (에러 메시지에 configId 포함)
        ApiConfig existingConfig = apiConfigRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("API config not found with id: " + configId));

        // 기존 엔티티의 값 업데이트 (필요한 필드만 업데이트하도록 메서드 내부 구현)
        existingConfig.updateConfig(newConfigData);
        apiConfigRepository.save(existingConfig);

        // 업데이트된 엔티티 저장 후 반환
        apiConfigMap.put(existingConfig.getId(), existingConfig);

        return existingConfig.getId();
    }


    public void deleteConfig(Long apiId) {
        apiConfigRepository.deleteById(apiId);
        apiConfigMap.remove(apiId);
    }

    public List<ApiConfig> getConfigList() {
        return new ArrayList<>(apiConfigMap.values());
    }
}
