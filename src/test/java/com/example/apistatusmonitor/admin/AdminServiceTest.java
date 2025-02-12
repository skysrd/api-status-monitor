package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.config.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private ApiConfigManager apiConfigManager;

    @Mock
    private NotificationConfigManager notificationConfigManager;

    @Mock
    private DBConfigManager dbConfigManager;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getApiConfigList() {
        List<ApiConfig> mockList = List.of(new ApiConfig());
        when(apiConfigManager.getConfigList()).thenReturn(mockList);

        List<ApiConfig> result = adminService.getApiConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        when(apiConfigManager.addConfig(apiConfig)).thenReturn(1L);

        Long result = adminService.addApiConfig(apiConfig);
        assertEquals(1L, result);
    }

    @Test
    void updateApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        apiConfig.setId(1L);
        when(apiConfigManager.updateConfig(1L, apiConfig)).thenReturn(1L);

        Long result = adminService.updateApiConfig(1L, apiConfig);
        assertEquals(1L, result);
    }

    @Test
    void deleteApiConfig() {
        doNothing().when(apiConfigManager).deleteConfig(1L);

        adminService.deleteApiConfig(1L);
        verify(apiConfigManager, times(1)).deleteConfig(1L);
    }

    @Test
    void getNotificationConfigList() {
        List<NotificationConfig> mockList = List.of(new NotificationConfig());
        when(notificationConfigManager.getConfigList()).thenReturn(mockList);

        List<NotificationConfig> result = adminService.getNotificationConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addNotificationConfig() {
        NotificationConfig notificationConfig = new NotificationConfig();
        when(notificationConfigManager.addConfig(notificationConfig)).thenReturn(1L);

        Long result = adminService.addNotificationConfig(notificationConfig);
        assertEquals(1L, result);
    }

    @Test
    void updateNotificationConfig() {
        NotificationConfig notificationConfig = new NotificationConfig();
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
        List<DBConfig> mockList = List.of(new DBConfig());
        when(dbConfigManager.getConfigList()).thenReturn(mockList);

        List<DBConfig> result = adminService.getDbConfigList();
        assertEquals(mockList, result);
    }

    @Test
    void addDbConfig() {
        DBConfig dbConfig = new DBConfig();
        doNothing().when(dbConfigManager).addConfig(dbConfig);

        DBConfig result = adminService.addDbConfig(dbConfig);
        assertNull(result);
    }

    @Test
    void updateDbConfig() {
        DBConfig dbConfig = new DBConfig();
        dbConfig.setId(1L);
        doReturn(dbConfig).when(dbConfigManager).updateConfig(1L, dbConfig);

        DBConfig result = adminService.updateDbConfig(1L, dbConfig);
        assertEquals(dbConfig, result);
    }

    @Test
    void deleteDbConfig() {
        doNothing().when(dbConfigManager).deleteConfig(1L);

        adminService.deleteDbConfig(1L);
        verify(dbConfigManager, times(1)).deleteConfig(1L);
    }
}