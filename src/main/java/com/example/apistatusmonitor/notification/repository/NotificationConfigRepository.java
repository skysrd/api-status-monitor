package com.example.apistatusmonitor.notification.repository;

import com.example.apistatusmonitor.notification.config.NotificationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, Long> {
}
