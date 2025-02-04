package com.example.apistatusmonitor.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "api_config")
@NoArgsConstructor
@Getter
public class ApiConfig {

    @Id
    private Long id;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "api_url")
    private String apiUrl;

    @Column(name = "check_interval")
    private int checkInterval;

    public void updateConfig(ApiConfig apiConfig) {
        this.apiName = apiConfig.getApiName();
        this.apiUrl = apiConfig.getApiUrl();
        this.checkInterval = apiConfig.getCheckInterval();
    }
}
