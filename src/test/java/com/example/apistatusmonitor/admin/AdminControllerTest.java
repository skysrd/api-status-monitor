package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.config.ApiConfig;
import com.example.apistatusmonitor.config.DBConfig;
import com.example.apistatusmonitor.config.NotificationConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        List<ApiConfig> mockList = List.of(new ApiConfig());
        when(adminService.getApiConfigList()).thenReturn(mockList);

        ResponseEntity<List<ApiConfig>> response = adminController.getApiConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        when(adminService.updateApiConfig(1L, apiConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.updateApiConfig(1L, apiConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void addApiConfig() {
        ApiConfig apiConfig = new ApiConfig();
        when(adminService.addApiConfig(apiConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.addApiConfig(apiConfig);
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
        List<NotificationConfig> mockList = List.of(new NotificationConfig());
        when(adminService.getNotificationConfigList()).thenReturn(mockList);

        ResponseEntity<List<NotificationConfig>> response = adminController.getNotificationConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateNotificationConfig() {
        NotificationConfig notificationConfig = new NotificationConfig();
        when(adminService.updateNotificationConfig(1L, notificationConfig)).thenReturn(1L);

        ResponseEntity<Long> response = adminController.updateNotificationConfig(1L, notificationConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody());
    }

    @Test
    void addNotificationConfig() {
        NotificationConfig notificationConfig = new NotificationConfig();
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
        List<DBConfig> mockList = List.of(new DBConfig());
        when(adminService.getDbConfigList()).thenReturn(mockList);

        ResponseEntity<List<DBConfig>> response = adminController.getDbConfigList();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockList, response.getBody());
    }

    @Test
    void updateDbConfig() {
        DBConfig dbConfig = new DBConfig();
        dbConfig.setId(1L);
        doNothing().when(adminService).updateDbConfig(1L, dbConfig);

        ResponseEntity<?> response = adminController.updateDbConfig(1L, dbConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void addDbConfig() {
        DBConfig dbConfig = new DBConfig();
        doNothing().when(adminService).addDbConfig(dbConfig);

        ResponseEntity<?> response = adminController.addDbConfig(dbConfig);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteDbConfig() {
        doNothing().when(adminService).deleteDbConfig(1L);

        ResponseEntity<?> response = adminController.deleteDbConfig(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).deleteDbConfig(1L);
    }
}