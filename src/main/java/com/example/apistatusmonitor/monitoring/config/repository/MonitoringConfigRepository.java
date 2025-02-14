package com.example.apistatusmonitor.monitoring.config.repository;

import com.example.apistatusmonitor.monitoring.config.MonitoringConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoringConfigRepository extends JpaRepository<MonitoringConfig, Long> {
}
