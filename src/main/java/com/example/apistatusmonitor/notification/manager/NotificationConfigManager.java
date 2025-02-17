package com.example.apistatusmonitor.notification.manager;

import com.example.apistatusmonitor.notification.repository.NotificationConfigRepository;
import com.example.apistatusmonitor.notification.config.NotificationConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class NotificationConfigManager {
    private final NotificationConfigRepository notificationConfigRepository;
    private final ConcurrentHashMap<Long, NotificationConfig> notificationConfigMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        loadConfig();
    }

    public void loadConfig() {
        notificationConfigRepository.findAll()
                .forEach(notificationConfig ->
                        notificationConfigMap.put(notificationConfig.getId(), notificationConfig));
    }

    public Long addConfig(NotificationConfig notificationConfig) {
        notificationConfigRepository.save(notificationConfig);
        notificationConfigMap.put(notificationConfig.getId(), notificationConfig);
        return notificationConfig.getId();
    }

    public Long updateConfig(Long notificationId, NotificationConfig notificationConfig) {
        NotificationConfig updateConfig = notificationConfigRepository.findById(notificationId).orElseThrow(
                () -> new IllegalArgumentException("Notification config not found"));
        updateConfig.updateConfig(notificationConfig);
        notificationConfigRepository.save(updateConfig);
        notificationConfigMap.put(notificationConfig.getId(), notificationConfig);
        return updateConfig.getId();
    }

    public void deleteConfig(Long notificationId) {
        notificationConfigRepository.deleteById(notificationId);
        notificationConfigMap.remove(notificationId);
    }

    public List<NotificationConfig> getConfigList() {
        return new ArrayList<>(notificationConfigMap.values());
    }
}
