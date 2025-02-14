package com.example.apistatusmonitor.monitoring.config.repository;

import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBMonitoringConfigRepository extends JpaRepository<DBMonitoringConfig, Long> {
}
