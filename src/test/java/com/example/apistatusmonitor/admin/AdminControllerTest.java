package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfig;
import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.config.SMSNotificationConfig;
import com.example.apistatusmonitor.notification.config.SlackNotificationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getApiConfigList() {
        List<ApiMonitoringConfig> mockList = List.of(new ApiMonitoringConfig());
        when(adminService.getApiConfigList()).thenReturn(mockList);

        ResponseEntity<List<ApiMonitoringConfig>> response = adminController.getApiConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateApiConfig() {
        ApiMonitoringConfig apiMonitoringConfig = new ApiMonitoringConfig();
        when(adminService.updateApiConfig(1L, apiMonitoringConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.updateApiConfig(1L, apiMonitoringConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void addApiConfig() {
        ApiMonitoringConfig apiMonitoringConfig = new ApiMonitoringConfig();
        when(adminService.addApiConfig(apiMonitoringConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.addApiConfig(apiMonitoringConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void deleteApiConfig() {
        doNothing().when(adminService).deleteApiConfig(1L);

        ResponseEntity<?> response = adminController.deleteApiConfig(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).deleteApiConfig(1L);
    }

    @Test
    void getNotificationConfigList() {
        NotificationConfig config1 = new SlackNotificationConfig();
        config1.setId(1L);
        NotificationConfig config2 = new SMSNotificationConfig();
        config2.setId(2L);
        List<NotificationConfig> mockList = List.of(config1, config2);
        when(adminService.getNotificationConfigList()).thenReturn(mockList);

        ResponseEntity<List<NotificationConfig>> response = adminController.getNotificationConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateNotificationConfig() {
        NotificationConfig notificationConfig = new SlackNotificationConfig();
        when(adminService.updateNotificationConfig(1L, notificationConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.updateNotificationConfig(1L, notificationConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void addNotificationConfig() {
        NotificationConfig notificationConfig = new SlackNotificationConfig();
        when(adminService.addNotificationConfig(notificationConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.addNotificationConfig(notificationConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void deleteNotificationConfig() {
        doNothing().when(adminService).deleteNotificationConfig(1L);

        ResponseEntity<?> response = adminController.deleteNotificationConfig(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).deleteNotificationConfig(1L);
    }

    @Test
    void getDbConfigList() {
        List<DBMonitoringConfig> mockList = List.of(new DBMonitoringConfig());
        when(adminService.getDbConfigList()).thenReturn(mockList);

        ResponseEntity<List<DBMonitoringConfig>> response = adminController.getDbConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateDbConfig() {
        DBMonitoringConfig dbMonitoringConfig = new DBMonitoringConfig();
        dbMonitoringConfig.setId(1L);
        doReturn(dbMonitoringConfig.getId()).when(adminService).updateDbConfig(1L, dbMonitoringConfig);

        ResponseEntity<?> response = adminController.updateDbConfig(1L, dbMonitoringConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dbMonitoringConfig.getId(), response.getBody());
    }

    @Test
    void addDbConfig() {
        DBMonitoringConfig dbMonitoringConfig = new DBMonitoringConfig();
        doReturn(dbMonitoringConfig.getId()).when(adminService).addDbConfig(dbMonitoringConfig);

        ResponseEntity<?> response = adminController.addDbConfig(dbMonitoringConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dbMonitoringConfig.getId(), response.getBody());
    }

    @Test
    void deleteDbConfig() {
        doNothing().when(adminService).deleteDbConfig(1L);

        ResponseEntity<?> response = adminController.deleteDbConfig(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).deleteDbConfig(1L);
    }
}