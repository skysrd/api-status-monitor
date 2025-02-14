package com.example.apistatusmonitor.monitoring;

import com.example.apistatusmonitor.event.EventPublisher;
import com.example.apistatusmonitor.event.MonitoringAnomalyEvent;
import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.DBMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.MonitoringConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MonitoringService {
    private final EventPublisher eventPublisher;
    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // WebClient.Builder를 사용하여 WebClient 인스턴스 생성
        this.webClient = webClientBuilder.build();
    }

    public MonitorResult doMonitor(MonitoringConfig config) {
        if(config instanceof ApiMonitoringConfig) {
            return checkApi((ApiMonitoringConfig) config);
        } else if(config instanceof DBMonitoringConfig) {
            return checkDB((DBMonitoringConfig) config);
        } else {
            throw new IllegalArgumentException("Unsupported config type: " + config.getClass());
        }
    }

    /**
     * 주어진 ApiConfig의 URL로 GET 요청을 보내고, 응답 시간과 결과를 측정하여 MonitorResult를 반환합니다.
     * @param apiMonitoringConfig API 설정 정보 (여기에는 URL, id 등 포함)
     * @return 모니터링 결과
     */
    public MonitorResult checkApi(ApiMonitoringConfig apiMonitoringConfig) {
        MonitorResult result = new MonitorResult();
        result.setConfigId(apiMonitoringConfig.getId());

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();
        try {
            // WebClient를 사용해 GET 요청을 보내고, 응답을 String으로 블로킹 방식으로 받음
            String response = webClient.get()
                    .uri(apiMonitoringConfig.getUrl())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            long elapsedTime = System.currentTimeMillis() - startTime;
            result.setResponseTime(elapsedTime);
            result.setStatus("SUCCESS");
            result.setResponse(response);
        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            result.setResponseTime(elapsedTime);
            result.setStatus("FAILURE");
            result.setErrorMessage(e.getMessage());

            eventPublisher.publishMonitorResultEvent(new MonitoringAnomalyEvent(result));
        }

        return result;
    }

    /**
     * 주어진 DBConfig를 이용하여 DB 모니터링을 수행합니다.
     * JdbcTemplate을 사용하여 DB 연결 테스트 쿼리를 실행하고, 응답 시간 및 결과를 기록합니다.
     *
     * @param dbMonitoringConfig DB 설정 정보 (DB URL, 사용자, 테스트 쿼리 등 포함)
     * @return MonitorResult 모니터링 결과
     */
    public MonitorResult checkDB(DBMonitoringConfig dbMonitoringConfig) {
        MonitorResult result = new MonitorResult();
        // MonitorResult의 apiConfigId 필드를 재사용하거나, 별도의 필드(targetId)를 추가할 수 있습니다.
        result.setConfigId(dbMonitoringConfig.getId());
        long startTime = System.currentTimeMillis();
        try {
            // DBConfig에 정의된 테스트 쿼리를 실행합니다.
            String testQuery = dbMonitoringConfig.getTestQuery();
            Object queryResult = jdbcTemplate.queryForObject(testQuery, Object.class);
            long elapsedTime = System.currentTimeMillis() - startTime;
            result.setResponseTime(elapsedTime);
            result.setStatus("SUCCESS");
            result.setResponse(queryResult != null ? queryResult.toString() : "No result");
        } catch (Exception e) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            result.setResponseTime(elapsedTime);
            result.setStatus("FAILURE");
            result.setErrorMessage(e.getMessage());

            eventPublisher.publishMonitorResultEvent(new MonitoringAnomalyEvent(result));
        }
        return result;
    }
}
