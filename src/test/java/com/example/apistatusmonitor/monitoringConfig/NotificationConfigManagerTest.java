package com.example.apistatusmonitor.monitoringConfig;

import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.manager.NotificationConfigManager;
import com.example.apistatusmonitor.notification.repository.NotificationConfigRepository;
import com.example.apistatusmonitor.notification.config.SMSNotificationConfig;
import com.example.apistatusmonitor.notification.config.SlackNotificationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationConfigManagerTest {

    @Mock
    private NotificationConfigRepository notificationConfigRepository;

    @InjectMocks
    private NotificationConfigManager notificationConfigManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void init() {
        // Given
        NotificationConfig config1 = new SlackNotificationConfig();
        config1.setId(1L);
        NotificationConfig config2 = new SMSNotificationConfig();
        config2.setId(2L);
        List<NotificationConfig> configs = List.of(config1, config2);
        when(notificationConfigRepository.findAll()).thenReturn(configs);

        // When
        notificationConfigManager.init();

        // Then
        List<NotificationConfig> configList = notificationConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertEquals(configs, configList, "캐시에 저장된 데이터가 올바르지 않습니다.");
        verify(notificationConfigRepository, times(1)).findAll();
    }

    @Test
    void loadConfig() {
        // Given
        NotificationConfig config1 = new SlackNotificationConfig();
        config1.setId(1L);
        NotificationConfig config2 = new SMSNotificationConfig();
        config2.setId(2L);
        List<NotificationConfig> mockList = List.of(config1, config2);
        when(notificationConfigRepository.findAll()).thenReturn(mockList);

        // When
        notificationConfigManager.loadConfig();

        // Then
        List<NotificationConfig> configList = notificationConfigManager.getConfigList();
        assertEquals(2, configList.size(), "캐시에는 2개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(configList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
    }

    @Test
    void addConfig() {
        // Given
        NotificationConfig config = new SlackNotificationConfig();
        config.setId(1L);
        when(notificationConfigRepository.save(config)).thenReturn(config);

        // When
        Long configId = notificationConfigManager.addConfig(config);

        // Then
        assertEquals(1L, configId);
        List<NotificationConfig> configList = notificationConfigManager.getConfigList();
        assertEquals(1, configList.size(), "캐시에는 1개의 구성 데이터가 있어야 합니다.");
        assertTrue(configList.contains(config), "리스트에는 config가 포함되어야 합니다.");
    }

    @Test
    void updateConfig() {
        // Given
        NotificationConfig existingConfig = new SlackNotificationConfig();
        existingConfig.setId(1L);
        existingConfig.setName("Old Config");
        when(notificationConfigRepository.findById(1L)).thenReturn(Optional.of(existingConfig));

        NotificationConfig newConfig = new SlackNotificationConfig();
        newConfig.setId(1L);
        newConfig.setName("New Config");

        notificationConfigManager.loadConfig();

        // When
        Long configId = notificationConfigManager.updateConfig(1L, newConfig);

        // Then
        assertEquals(1L, configId);
        verify(notificationConfigRepository, times(1)).save(existingConfig);
        assertEquals("New Config", existingConfig.getName());

        NotificationConfig cachedConfig = notificationConfigManager.getConfigList().stream()
                .filter(config -> config.getId().equals(configId))
                .findFirst()
                .orElse(null);
        assertNotNull(cachedConfig);
        assertEquals("New Config", cachedConfig.getName());
    }

    @Test
    void deleteConfig() {
        // Given
        doNothing().when(notificationConfigRepository).deleteById(1L);

        // When
        notificationConfigManager.deleteConfig(1L);

        // Then
        verify(notificationConfigRepository, times(1)).deleteById(1L);
        List<NotificationConfig> configList = notificationConfigManager.getConfigList();
        assertTrue(configList.isEmpty(), "캐시에는 데이터가 없어야 합니다.");
    }

    @Test
    void getConfigList() {
        // Given
        NotificationConfig config1 = new SlackNotificationConfig();
        config1.setId(1L);
        NotificationConfig config2 = new SMSNotificationConfig();
        config2.setId(2L);
        List<NotificationConfig> configs = List.of(config1, config2);
        when(notificationConfigRepository.findAll()).thenReturn(configs);

        // When
        notificationConfigManager.loadConfig();
        List<NotificationConfig> resultList = notificationConfigManager.getConfigList();

        // Then
        assertEquals(2, resultList.size(), "구성 리스트에는 2개의 아이템이 있어야 합니다.");
        assertTrue(resultList.contains(config1), "리스트에는 config1이 포함되어야 합니다.");
        assertTrue(resultList.contains(config2), "리스트에는 config2가 포함되어야 합니다.");
        verify(notificationConfigRepository, times(1)).findAll();
    }
}