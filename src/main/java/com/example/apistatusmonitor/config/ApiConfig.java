package com.example.apistatusmonitor.config;

import jakarta.persistence.*;
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
@Setter
@Getter
@ToString
public class ApiConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_url")
    private String url;

    @Column(name = "check_interval")
    private int checkInterval;

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

    public void updateConfig(ApiConfig apiConfig) {
        this.name = apiConfig.getName();
        this.url = apiConfig.getUrl();
        this.checkInterval = apiConfig.getCheckInterval();
        this.restMethod = apiConfig.getRestMethod();
        this.requestHeader = apiConfig.getRequestHeader();
        this.requestBody = apiConfig.getRequestBody();
        this.expectResponse = apiConfig.getExpectResponse();
        this.token = apiConfig.getToken();
    }
}
