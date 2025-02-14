package com.example.apistatusmonitor.monitoring.config;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "monitoring_config")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MonitoringConfig {
    // 모니터링 대상 API 설정 정보를 담는 Entity

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "check_interval")
    private Integer checkInterval;

    @Column(name = "enabled")
    private boolean enabled;

    public abstract void updateConfig(MonitoringConfig monitoringConfig);
}
