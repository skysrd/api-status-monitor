package com.example.apistatusmonitor.config.repository;

import com.example.apistatusmonitor.config.NotificationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, Long> {
}
