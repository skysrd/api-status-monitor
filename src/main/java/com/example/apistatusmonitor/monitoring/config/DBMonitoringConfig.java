package com.example.apistatusmonitor.monitoring.config;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "db_config")
@Getter
@Setter
@ToString
public class DBMonitoringConfig extends MonitoringConfig {

    @Column(name = "url")
    private String url;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    @Column(name = "port")
    private int port;

    @Column(name = "test_query")
    private String testQuery;

    @Override
    public void updateConfig(MonitoringConfig monitoringConfig) {
        if(monitoringConfig instanceof DBMonitoringConfig) {
            DBMonitoringConfig dbMonitoringConfig = (DBMonitoringConfig) monitoringConfig;
            this.setName(dbMonitoringConfig.getName());
            this.setCheckInterval(dbMonitoringConfig.getCheckInterval());
            this.setUrl(dbMonitoringConfig.getUrl());
            this.setUsername(dbMonitoringConfig.getUsername());
            this.setPassword(dbMonitoringConfig.getPassword());
            this.setType(dbMonitoringConfig.getType());
            this.setPort(dbMonitoringConfig.getPort());
            this.setTestQuery(dbMonitoringConfig.getTestQuery());
        } else {
            throw new IllegalArgumentException("Invalid config type");
        }
    }
}
