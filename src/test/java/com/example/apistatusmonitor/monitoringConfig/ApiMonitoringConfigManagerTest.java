package com.example.apistatusmonitor.monitoringConfig;

import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfigManager;
import com.example.apistatusmonitor.monitoring.config.repository.ApiMonitoringConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiMonitoringConfigManagerTest {

    @Mock
    private ApiMonitoringConfigRepository apiMonitoringConfigRepository;

    @InjectMocks
    private ApiMonitoringConfigManager apiMonitoringConfigManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init() {
        // Given
        ApiMonitoringConfig config1 = new ApiMonitoringConfig();
        config1.setId(1L);
        ApiMonitoringConfig config2 = new ApiMonitoringConfig();
        config2.setId(2L);
        List<ApiMonitoringConfig> configs = List.of(config1, config2);
        when(apiMonitoringConfigRepository.findAll()).thenReturn(configs);

        // When
        apiMonitoringConfigManager.init();

        // Then
        List<ApiMonitoringConfig> configList = apiMonitoringConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertEquals(configs, configList, "캐시에 저장된 데이터가 올바르지 않습니다.");
        verify(apiMonitoringConfigRepository, times(1)).findAll();
    }

    @Test
    void loadConfig() {
        // Given
        ApiMonitoringConfig apiMonitoringConfig1 = new ApiMonitoringConfig();
        apiMonitoringConfig1.setId(1L);
        ApiMonitoringConfig apiMonitoringConfig2 = new ApiMonitoringConfig();
        apiMonitoringConfig2.setId(2L);
        List<ApiMonitoringConfig> mockList = List.of(apiMonitoringConfig1, apiMonitoringConfig2);
        when(apiMonitoringConfigRepository.findAll()).thenReturn(mockList);

        // When
        apiMonitoringConfigManager.loadConfig();

        // Then
        List<ApiMonitoringConfig> configList = apiMonitoringConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(apiMonitoringConfig1), "리스트에는 apiConfig1이 포함되어야 합니다.");
        assertTrue(configList.contains(apiMonitoringConfig2), "리스트에는 apiConfig2가 포함되어야 합니다.");
    }

    @Test
    void addConfig() {
        // Given
        ApiMonitoringConfig apiMonitoringConfig = new ApiMonitoringConfig();
        apiMonitoringConfig.setId(1L);
        when(apiMonitoringConfigRepository.save(apiMonitoringConfig)).thenReturn(apiMonitoringConfig);

        // When
        Long configId = apiMonitoringConfigManager.addConfig(apiMonitoringConfig);

        // Then
        assertEquals(1L, configId);
        List<ApiMonitoringConfig> configList = apiMonitoringConfigManager.getConfigList();
        assertEquals(1, configList.size(), "캐시에는 1개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(apiMonitoringConfig), "리스트에는 apiConfig가 포함되어야 합니다.");
    }

    @Test
    void updateConfig() {
        // Given
        ApiMonitoringConfig existingConfig = new ApiMonitoringConfig();
        existingConfig.setId(1L);
        existingConfig.setName("Old Config");
        when(apiMonitoringConfigRepository.findById(1L)).thenReturn(Optional.of(existingConfig));

        ApiMonitoringConfig newConfig = new ApiMonitoringConfig();
        newConfig.setId(1L);
        newConfig.setName("New Config");

        // When
        Long configId = apiMonitoringConfigManager.updateConfig(1L, newConfig);

        // Then
        assertEquals(1L, configId);
        verify(apiMonitoringConfigRepository, times(1)).save(existingConfig);
        assertEquals("New Config", existingConfig.getName());

        ApiMonitoringConfig cachedConfig = apiMonitoringConfigManager.getConfigList().stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst()
                .orElse(null);
        assertNotNull(cachedConfig);
        assertEquals("New Config", cachedConfig.getName());
    }

    @Test
    void deleteConfig() {
        // Given
        doNothing().when(apiMonitoringConfigRepository).deleteById(1L);

        // When
        apiMonitoringConfigManager.deleteConfig(1L);

        // Then
        verify(apiMonitoringConfigRepository, times(1)).deleteById(1L);
        List<ApiMonitoringConfig> configList = apiMonitoringConfigManager.getConfigList();
        assertTrue(configList.isEmpty(), "캐시에는 데이터가 없어야 합니다.");
    }

    @Test
    void getConfigList() {
        // Given
        ApiMonitoringConfig config1 = new ApiMonitoringConfig();
        config1.setId(1L);
        ApiMonitoringConfig config2 = new ApiMonitoringConfig();
        config2.setId(2L);
        List<ApiMonitoringConfig> configs = List.of(config1, config2);
        when(apiMonitoringConfigRepository.findAll()).thenReturn(configs);

        // When
        apiMonitoringConfigManager.loadConfig();
        List<ApiMonitoringConfig> resultList = apiMonitoringConfigManager.getConfigList();

        // Then
        assertEquals(2, resultList.size(), "구성 리스트에는 2개의 아이템이 있어야 합니다.");
        assertTrue(resultList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(resultList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
        verify(apiMonitoringConfigRepository, times(1)).findAll();
    }
}