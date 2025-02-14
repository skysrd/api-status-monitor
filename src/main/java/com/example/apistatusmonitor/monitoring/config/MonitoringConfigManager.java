package com.example.apistatusmonitor.monitoring.config;

import com.example.apistatusmonitor.monitoring.config.repository.MonitoringConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class MonitoringConfigManager {
    private final ConcurrentHashMap<Long, MonitoringConfig> monitoringConfigMap = new ConcurrentHashMap<>();
    private final MonitoringConfigRepository monitoringConfigRepository;

    @PostConstruct
    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        monitoringConfigMap.clear();
        monitoringConfigRepository.findAll()
                .forEach(apiConfig ->
                        monitoringConfigMap.put(apiConfig.getId(), apiConfig));
    }

    public Long addConfig(ApiMonitoringConfig apiMonitoringConfig) {
        ApiMonitoringConfig savedConfig = monitoringConfigRepository.save(apiMonitoringConfig);
        monitoringConfigMap.put(savedConfig.getId(), savedConfig);
        return savedConfig.getId();
    }

    public Long updateConfig(Long configId, ApiMonitoringConfig newConfigData) {
        // 기존 설정을 조회하고, 없으면 예외 발생 (에러 메시지에 configId 포함)
        MonitoringConfig existingConfig = monitoringConfigRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("API config not found with id: " + configId));

        // 기존 엔티티의 값 업데이트 (필요한 필드만 업데이트하도록 메서드 내부 구현)
        existingConfig.updateConfig(newConfigData);
        monitoringConfigRepository.save(existingConfig);

        // 업데이트된 엔티티 저장 후 반환
        monitoringConfigMap.put(existingConfig.getId(), existingConfig);

        return existingConfig.getId();
    }


    public void deleteConfig(Long apiId) {
        monitoringConfigRepository.deleteById(apiId);
        monitoringConfigMap.remove(apiId);
    }

    public List<MonitoringConfig> getConfigList() {
        return new ArrayList<>(monitoringConfigMap.values());
    }
}
