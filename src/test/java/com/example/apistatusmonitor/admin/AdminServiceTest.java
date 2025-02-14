package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfigManager;
import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfigManager;
import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.manager.NotificationConfigManager;
import com.example.apistatusmonitor.notification.config.SMSNotificationConfig;
import com.example.apistatusmonitor.notification.config.SlackNotificationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private ApiMonitoringConfigManager apiMonitoringConfigManager;

    @Mock
    private NotificationConfigManager notificationConfigManager;

    @Mock
    private DBMonitoringConfigManager dbMonitoringConfigManager;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getApiConfigList() {
        List<ApiMonitoringConfig> mockList = List.of(new ApiMonitoringConfig());
        when(apiMonitoringConfigManager.getConfigList()).thenReturn(mockList);

        List<ApiMonitoringConfig> result = adminService.getApiConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addApiConfig() {
        ApiMonitoringConfig apiMonitoringConfig = new ApiMonitoringConfig();
        when(apiMonitoringConfigManager.addConfig(apiMonitoringConfig)).thenReturn(1L);

        Long result = adminService.addApiConfig(apiMonitoringConfig);
        assertEquals(1L, result);
    }

    @Test
    void updateApiConfig() {
        ApiMonitoringConfig apiMonitoringConfig = new ApiMonitoringConfig();
        apiMonitoringConfig.setId(1L);
        when(apiMonitoringConfigManager.updateConfig(1L, apiMonitoringConfig)).thenReturn(1L);

        Long result = adminService.updateApiConfig(1L, apiMonitoringConfig);
        assertEquals(1L, result);
    }

    @Test
    void deleteApiConfig() {
        doNothing().when(apiMonitoringConfigManager).deleteConfig(1L);

        adminService.deleteApiConfig(1L);
        verify(apiMonitoringConfigManager, times(1)).deleteConfig(1L);
    }

    @Test
    void getNotificationConfigList() {
        NotificationConfig config1 = new SlackNotificationConfig();
        config1.setId(1L);
        NotificationConfig config2 = new SMSNotificationConfig();
        config2.setId(2L);
        List<NotificationConfig> mockList = List.of(config1, config2);
        when(notificationConfigManager.getConfigList()).thenReturn(mockList);

        List<NotificationConfig> result = adminService.getNotificationConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addNotificationConfig() {
        NotificationConfig notificationConfig = new SlackNotificationConfig();
        when(notificationConfigManager.addConfig(notificationConfig)).thenReturn(1L);

        Long result = adminService.addNotificationConfig(notificationConfig);
        assertEquals(1L, result);
    }

    @Test
    void updateNotificationConfig() {
        NotificationConfig notificationConfig = new SlackNotificationConfig();
        when(notificationConfigManager.updateConfig(1L, notificationConfig)).thenReturn(1L);

        Long result = adminService.updateNotificationConfig(1L, notificationConfig);
        assertEquals(1L, result);
    }

    @Test
    void deleteNotificationConfig() {
        doNothing().when(notificationConfigManager).deleteConfig(1L);

        adminService.deleteNotificationConfig(1L);
        verify(notificationConfigManager, times(1)).deleteConfig(1L);
    }

    @Test
    void getDbConfigList() {
        List<DBMonitoringConfig> mockList = List.of(new DBMonitoringConfig());
        when(dbMonitoringConfigManager.getConfigList()).thenReturn(mockList);

        List<DBMonitoringConfig> result = adminService.getDbConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addDbConfig() {
        DBMonitoringConfig dbMonitoringConfig = new DBMonitoringConfig();
        doReturn(1L).when(dbMonitoringConfigManager).addConfig(dbMonitoringConfig);
        Long result = adminService.addDbConfig(dbMonitoringConfig);
        assertEquals(1L, result);
    }

    @Test
    void updateDbConfig() {
        DBMonitoringConfig dbMonitoringConfig = new DBMonitoringConfig();
        dbMonitoringConfig.setId(1L);
        doReturn(1L).when(dbMonitoringConfigManager).updateConfig(1L, dbMonitoringConfig);

        Long result = adminService.updateDbConfig(1L, dbMonitoringConfig);
        assertEquals(1L, result);
    }

    @Test
    void deleteDbConfig() {
        doNothing().when(dbMonitoringConfigManager).deleteConfig(1L);

        adminService.deleteDbConfig(1L);
        verify(dbMonitoringConfigManager, times(1)).deleteConfig(1L);
    }
}