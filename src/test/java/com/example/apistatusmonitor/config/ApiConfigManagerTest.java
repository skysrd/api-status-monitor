package com.example.apistatusmonitor.config;

import com.example.apistatusmonitor.config.repository.ApiConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApiConfigManagerTest {

    @Mock
    private ApiConfigRepository apiConfigRepository;

    @InjectMocks
    private ApiConfigManager apiConfigManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init() {
        // Given
        ApiConfig config1 = new ApiConfig();
        config1.setId(1L);
        ApiConfig config2 = new ApiConfig();
        config2.setId(2L);
        List<ApiConfig> configs = List.of(config1, config2);
        when(apiConfigRepository.findAll()).thenReturn(configs);

        // When
        apiConfigManager.init();

        // Then
        List<ApiConfig> configList = apiConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertEquals(configs, configList, "캐시에 저장된 데이터가 올바르지 않습니다.");
        verify(apiConfigRepository, times(1)).findAll();
    }

    @Test
    void loadConfig() {
        // Given
        ApiConfig apiConfig1 = new ApiConfig();
        apiConfig1.setId(1L);
        ApiConfig apiConfig2 = new ApiConfig();
        apiConfig2.setId(2L);
        List<ApiConfig> mockList = List.of(apiConfig1, apiConfig2);
        when(apiConfigRepository.findAll()).thenReturn(mockList);

        // When
        apiConfigManager.loadConfig();

        // Then
        List<ApiConfig> configList = apiConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(apiConfig1), "리스트에는 apiConfig1이 포함되어야 합니다.");
        assertTrue(configList.contains(apiConfig2), "리스트에는 apiConfig2가 포함되어야 합니다.");
    }

    @Test
    void addConfig() {
        // Given
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setId(1L);
        when(apiConfigRepository.save(apiConfig)).thenReturn(apiConfig);

        // When
        Long configId = apiConfigManager.addConfig(apiConfig);

        // Then
        assertEquals(1L, configId);
        List<ApiConfig> configList = apiConfigManager.getConfigList();
        assertEquals(1, configList.size(), "캐시에는 1개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(apiConfig), "리스트에는 apiConfig가 포함되어야 합니다.");
    }

    @Test
    void updateConfig() {
        // Given
        ApiConfig existingConfig = new ApiConfig();
        existingConfig.setId(1L);
        existingConfig.setName("Old Config");
        when(apiConfigRepository.findById(1L)).thenReturn(Optional.of(existingConfig));

        ApiConfig newConfig = new ApiConfig();
        newConfig.setId(1L);
        newConfig.setName("New Config");

        // When
        Long configId = apiConfigManager.updateConfig(1L, newConfig);

        // Then
        assertEquals(1L, configId);
        verify(apiConfigRepository, times(1)).save(existingConfig);
        assertEquals("New Config", existingConfig.getName());

        ApiConfig cachedConfig = apiConfigManager.getConfigList().stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst()
                .orElse(null);
        assertNotNull(cachedConfig);
        assertEquals("New Config", cachedConfig.getName());
    }

    @Test
    void deleteConfig() {
        // Given
        doNothing().when(apiConfigRepository).deleteById(1L);

        // When
        apiConfigManager.deleteConfig(1L);

        // Then
        verify(apiConfigRepository, times(1)).deleteById(1L);
        List<ApiConfig> configList = apiConfigManager.getConfigList();
        assertTrue(configList.isEmpty(), "캐시에는 데이터가 없어야 합니다.");
    }

    @Test
    void getConfigList() {
        // Given
        ApiConfig config1 = new ApiConfig();
        config1.setId(1L);
        ApiConfig config2 = new ApiConfig();
        config2.setId(2L);
        List<ApiConfig> configs = List.of(config1, config2);
        when(apiConfigRepository.findAll()).thenReturn(configs);

        // When
        apiConfigManager.loadConfig();
        List<ApiConfig> resultList = apiConfigManager.getConfigList();

        // Then
        assertEquals(2, resultList.size(), "구성 리스트에는 2개의 아이템이 있어야 합니다.");
        assertTrue(resultList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(resultList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
        verify(apiConfigRepository, times(1)).findAll();
    }
}