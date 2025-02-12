package com.example.apistatusmonitor.config;

import com.example.apistatusmonitor.config.repository.DBConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBConfigManagerTest {

    @Mock
    private DBConfigRepository dbConfigRepository;

    @InjectMocks
    private DBConfigManager dbConfigManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init() {
        // Given
        DBConfig config1 = new DBConfig();
        config1.setId(1L);
        DBConfig config2 = new DBConfig();
        config2.setId(2L);
        List<DBConfig> configs = List.of(config1, config2);
        when(dbConfigRepository.findAll()).thenReturn(configs);

        // When
        dbConfigManager.init();

        // Then
        List<DBConfig> configList = dbConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertEquals(configs, configList, "캐시에 저장된 데이터가 올바르지 않습니다.");
        verify(dbConfigRepository, times(1)).findAll();
    }

    @Test
    void loadConfig() {
        // Given
        DBConfig dbConfig1 = new DBConfig();
        dbConfig1.setId(1L);
        DBConfig dbConfig2 = new DBConfig();
        dbConfig2.setId(2L);
        List<DBConfig> mockList = List.of(dbConfig1, dbConfig2);
        when(dbConfigRepository.findAll()).thenReturn(mockList);

        // When
        dbConfigManager.loadConfig();

        // Then
        List<DBConfig> configList = dbConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(dbConfig1), "리스트에는 dbConfig1이 포함되어야 합니다.");
        assertTrue(configList.contains(dbConfig2), "리스트에는 dbConfig2가 포함되어야 합니다.");
    }

    @Test
    void addConfig() {
        // Given
        DBConfig dbConfig = new DBConfig();
        dbConfig.setId(1L);
        when(dbConfigRepository.save(dbConfig)).thenReturn(dbConfig);

        // When
        Long configId = dbConfigManager.addConfig(dbConfig);

        // Then
        assertEquals(1L, configId);
        List<DBConfig> configList = dbConfigManager.getConfigList();
        assertEquals(1, configList.size(), "캐시에는 1개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(dbConfig), "리스트에는 dbConfig가 ��함되어야 합니다.");
    }

    @Test
    void updateConfig() {
        // Given
        DBConfig existingConfig = new DBConfig();
        existingConfig.setId(1L);
        existingConfig.setName("Old Config");
        when(dbConfigRepository.findById(1L)).thenReturn(Optional.of(existingConfig));

        DBConfig newConfig = new DBConfig();
        newConfig.setId(1L);
        newConfig.setName("New Config");

        // When
        Long configId = dbConfigManager.updateConfig(1L, newConfig);

        // Then
        assertEquals(1L, configId);
        verify(dbConfigRepository, times(1)).save(existingConfig);
        assertEquals("New Config", existingConfig.getName());

        DBConfig cachedConfig = dbConfigManager.getConfigList().stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst()
                .orElse(null);
        assertNotNull(cachedConfig);
        assertEquals("New Config", cachedConfig.getName());
    }

    @Test
    void deleteConfig() {
        // Given
        doNothing().when(dbConfigRepository).deleteById(1L);

        // When
        dbConfigManager.deleteConfig(1L);

        // Then
        verify(dbConfigRepository, times(1)).deleteById(1L);
        List<DBConfig> configList = dbConfigManager.getConfigList();
        assertTrue(configList.isEmpty(), "캐시에는 데이터가 없어야 합니다.");
    }

    @Test
    void getConfigList() {
        // Given
        DBConfig config1 = new DBConfig();
        config1.setId(1L);
        DBConfig config2 = new DBConfig();
        config2.setId(2L);
        List<DBConfig> configs = List.of(config1, config2);
        when(dbConfigRepository.findAll()).thenReturn(configs);

        // When
        dbConfigManager.loadConfig();
        List<DBConfig> resultList = dbConfigManager.getConfigList();

        // Then
        assertEquals(2, resultList.size(), "구성 리스트에는 2개의 아이템이 있어야 합니다.");
        assertTrue(resultList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(resultList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
        verify(dbConfigRepository, times(1)).findAll();
    }
}