package com.example.apistatusmonitor.monitoring.config.repository;

import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiMonitoringConfigRepository extends JpaRepository<ApiMonitoringConfig, Long> {
    Optional<ApiMonitoringConfig> findByName(String name);
}
