package com.example.apistatusmonitor.monitoring.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
    모니터링 타겟 API 설정 정보를 담는 Entity
 */

@Entity
@Table(name = "api_config")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiMonitoringConfig extends MonitoringConfig {

    @Column(name = "api_url")
    private String url;

    @Column(name = "rest_method")
    private String restMethod;

    @Column(name = "request_header")
    private String requestHeader;

    @Column(name = "request_body")
    private String requestBody;

    @Column(name = "expect_response")
    private String expectResponse;

    @Column(name = "token")
    private String token;

    @Override
    public void updateConfig(MonitoringConfig monitoringConfig) {
        if(monitoringConfig instanceof ApiMonitoringConfig) {
            ApiMonitoringConfig apiMonitoringConfig = (ApiMonitoringConfig) monitoringConfig;
            this.setName(apiMonitoringConfig.getName());
            this.setCheckInterval(apiMonitoringConfig.getCheckInterval());
            this.setUrl(apiMonitoringConfig.getUrl());
            this.setRestMethod(apiMonitoringConfig.getRestMethod());
            this.setRequestHeader(apiMonitoringConfig.getRequestHeader());
            this.setRequestBody(apiMonitoringConfig.getRequestBody());
            this.setExpectResponse(apiMonitoringConfig.getExpectResponse());
            this.setToken(apiMonitoringConfig.getToken());
        }
    }
}
