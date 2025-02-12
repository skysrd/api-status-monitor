package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.config.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    전체 API 설정, 알림 설정, DB 설정을 관리하는 서비스 클래스
 */

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ApiConfigManager apiConfigManager;
    private final NotificationConfigManager notificationConfigManager;
    private final DBConfigManager dbConfigManager;

    public List<ApiConfig> getApiConfigList() {
        return apiConfigManager.getConfigList();
    }

    @Transactional
    public Long addApiConfig(ApiConfig apiConfig) {
        return apiConfigManager.addConfig(apiConfig);
    }

    @Transactional
    public Long updateApiConfig(Long configId, ApiConfig apiConfig) {
        return apiConfigManager.updateConfig(configId, apiConfig);
    }

    @Transactional
    public void deleteApiConfig(Long apiId) {
        apiConfigManager.deleteConfig(apiId);
    }

    public List<NotificationConfig> getNotificationConfigList() {
        return notificationConfigManager.getConfigList();
    }

    @Transactional
    public Long addNotificationConfig(NotificationConfig notificationConfig) {
        return notificationConfigManager.addConfig(notificationConfig);
    }

    @Transactional
    public Long updateNotificationConfig(Long notificationId, NotificationConfig notificationConfig) {
        return notificationConfigManager.updateConfig(notificationId, notificationConfig);
    }

    @Transactional
    public void deleteNotificationConfig(Long notificationId) {
        notificationConfigManager.deleteConfig(notificationId);
    }

    public List<DBConfig> getDbConfigList() {
        return dbConfigManager.getConfigList();
    }

    @Transactional
    public DBConfig addDbConfig(DBConfig dbConfig) {
        dbConfigManager.addConfig(dbConfig);
        return null;
    }

    public DBConfig updateDbConfig(Long configId, DBConfig dbConfig) {
        dbConfigManager.updateConfig(configId, dbConfig);
        return null;
    }

    public void deleteDbConfig(Long dbId) {
        dbConfigManager.deleteConfig(dbId);
    }
}
