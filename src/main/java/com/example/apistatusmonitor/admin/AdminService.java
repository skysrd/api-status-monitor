package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.monitoring.config.*;
import com.example.apistatusmonitor.notification.config.NotificationConfig;
import com.example.apistatusmonitor.notification.manager.NotificationConfigManager;
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
    private final ApiMonitoringConfigManager apiMonitoringConfigManager;
    private final NotificationConfigManager notificationConfigManager;
    private final DBMonitoringConfigManager dbMonitoringConfigManager;

    public List<ApiMonitoringConfig> getApiConfigList() {
        return apiMonitoringConfigManager.getConfigList();
    }

    @Transactional
    public Long addApiConfig(ApiMonitoringConfig apiMonitoringConfig) {
        return apiMonitoringConfigManager.addConfig(apiMonitoringConfig);
    }

    @Transactional
    public Long updateApiConfig(Long configId, ApiMonitoringConfig apiMonitoringConfig) {
        return apiMonitoringConfigManager.updateConfig(configId, apiMonitoringConfig);
    }

    @Transactional
    public void deleteApiConfig(Long apiId) {
        apiMonitoringConfigManager.deleteConfig(apiId);
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

    public List<DBMonitoringConfig> getDbConfigList() {
        return dbMonitoringConfigManager.getConfigList();
    }

    @Transactional
    public Long addDbConfig(DBMonitoringConfig dbMonitoringConfig) {
        return dbMonitoringConfigManager.addConfig(dbMonitoringConfig);
    }

    @Transactional
    public Long updateDbConfig(Long configId, DBMonitoringConfig dbMonitoringConfig) {
        return dbMonitoringConfigManager.updateConfig(configId, dbMonitoringConfig);
    }

    @Transactional
    public void deleteDbConfig(Long dbId) {
        dbMonitoringConfigManager.deleteConfig(dbId);
    }
}
