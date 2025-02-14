package com.example.apistatusmonitor.monitoringConfig;

import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfigManager;
import com.example.apistatusmonitor.monitoring.config.repository.DBMonitoringConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBMonitoringConfigManagerTest {

    @Mock
    private DBMonitoringConfigRepository dbMonitoringConfigRepository;

    @InjectMocks
    private DBMonitoringConfigManager dbMonitoringConfigManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init() {
        // Given
        DBMonitoringConfig config1 = new DBMonitoringConfig();
        config1.setId(1L);
        DBMonitoringConfig config2 = new DBMonitoringConfig();
        config2.setId(2L);
        List<DBMonitoringConfig> configs = List.of(config1, config2);
        when(dbMonitoringConfigRepository.findAll()).thenReturn(configs);

        // When
        dbMonitoringConfigManager.init();

        // Then
        List<DBMonitoringConfig> configList = dbMonitoringConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertEquals(configs, configList, "캐시에 저장된 데이터가 올바르지 않습니다.");
        verify(dbMonitoringConfigRepository, times(1)).findAll();
    }

    @Test
    void loadConfig() {
        // Given
        DBMonitoringConfig dbMonitoringConfig1 = new DBMonitoringConfig();
        dbMonitoringConfig1.setId(1L);
        DBMonitoringConfig dbMonitoringConfig2 = new DBMonitoringConfig();
        dbMonitoringConfig2.setId(2L);
        List<DBMonitoringConfig> mockList = List.of(dbMonitoringConfig1, dbMonitoringConfig2);
        when(dbMonitoringConfigRepository.findAll()).thenReturn(mockList);

        // When
        dbMonitoringConfigManager.loadConfig();

        // Then
        List<DBMonitoringConfig> configList = dbMonitoringConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(dbMonitoringConfig1), "리스트에는 dbConfig1이 포함되어야 합니다.");
        assertTrue(configList.contains(dbMonitoringConfig2), "리스트에는 dbConfig2가 포함되어야 합니다.");
    }

    @Test
    void addConfig() {
        // Given
        DBMonitoringConfig dbMonitoringConfig = new DBMonitoringConfig();
        dbMonitoringConfig.setId(1L);
        when(dbMonitoringConfigRepository.save(dbMonitoringConfig)).thenReturn(dbMonitoringConfig);

        // When
        Long configId = dbMonitoringConfigManager.addConfig(dbMonitoringConfig);

        // Then
        assertEquals(1L, configId);
        List<DBMonitoringConfig> configList = dbMonitoringConfigManager.getConfigList();
        assertEquals(1, configList.size(), "캐시에는 1개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(dbMonitoringConfig), "리스트에는 dbConfig가 ��함되어야 합니다.");
    }

    @Test
    void updateConfig() {
        // Given
        DBMonitoringConfig existingConfig = new DBMonitoringConfig();
        existingConfig.setId(1L);
        existingConfig.setName("Old Config");
        when(dbMonitoringConfigRepository.findById(1L)).thenReturn(Optional.of(existingConfig));

        DBMonitoringConfig newConfig = new DBMonitoringConfig();
        newConfig.setId(1L);
        newConfig.setName("New Config");

        // When
        Long configId = dbMonitoringConfigManager.updateConfig(1L, newConfig);

        // Then
        assertEquals(1L, configId);
        verify(dbMonitoringConfigRepository, times(1)).save(existingConfig);
        assertEquals("New Config", existingConfig.getName());

        DBMonitoringConfig cachedConfig = dbMonitoringConfigManager.getConfigList().stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst()
                .orElse(null);
        assertNotNull(cachedConfig);
        assertEquals("New Config", cachedConfig.getName());
    }

    @Test
    void deleteConfig() {
        // Given
        doNothing().when(dbMonitoringConfigRepository).deleteById(1L);

        // When
        dbMonitoringConfigManager.deleteConfig(1L);

        // Then
        verify(dbMonitoringConfigRepository, times(1)).deleteById(1L);
        List<DBMonitoringConfig> configList = dbMonitoringConfigManager.getConfigList();
        assertTrue(configList.isEmpty(), "캐시에는 데이터가 없어야 합니다.");
    }

    @Test
    void getConfigList() {
        // Given
        DBMonitoringConfig config1 = new DBMonitoringConfig();
        config1.setId(1L);
        DBMonitoringConfig config2 = new DBMonitoringConfig();
        config2.setId(2L);
        List<DBMonitoringConfig> configs = List.of(config1, config2);
        when(dbMonitoringConfigRepository.findAll()).thenReturn(configs);

        // When
        dbMonitoringConfigManager.loadConfig();
        List<DBMonitoringConfig> resultList = dbMonitoringConfigManager.getConfigList();

        // Then
        assertEquals(2, resultList.size(), "구성 리스트에는 2개의 아이템이 있어야 합니다.");
        assertTrue(resultList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(resultList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
        verify(dbMonitoringConfigRepository, times(1)).findAll();
    }
}