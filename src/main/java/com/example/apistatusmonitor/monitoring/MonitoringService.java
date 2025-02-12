package com.example.apistatusmonitor.monitoring;

import com.example.apistatusmonitor.config.ApiConfig;
import com.example.apistatusmonitor.config.DBConfig;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class MonitoringService {
    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        // WebClient.Builder를 사용하여 WebClient 인스턴스 생성
        this.webClient = webClientBuilder.build();
    }

    /**
     * 주어진 ApiConfig의 URL로 GET 요청을 보내고, 응답 시간과 결과를 측정하여 MonitorResult를 반환합니다.
     * @param apiConfig API 설정 정보 (여기에는 URL, id 등 포함)
     * @return 모니터링 결과
     */
    public MonitorResult checkApi(ApiConfig apiConfig) {
        MonitorResult result = new MonitorResult();
        result.setApiConfigId(apiConfig.getId());

        // 시작 시간 기록
        long startTime = System.currentTimeMillis();
        try {
            // WebClient를 사용해 GET 요청을 보내고, 응답을 String으로 블로킹 방식으로 받음
            String response = webClient.get()
                    .uri(apiConfig.getUrl())
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
        }

        return result;
    }

    /**
     * 주어진 DBConfig를 이용하여 DB 모니터링을 수행합니다.
     * JdbcTemplate을 사용하여 DB 연결 테스트 쿼리를 실행하고, 응답 시간 및 결과를 기록합니다.
     *
     * @param dbConfig DB 설정 정보 (DB URL, 사용자, 테스트 쿼리 등 포함)
     * @return MonitorResult 모니터링 결과
     */
    public MonitorResult checkDB(DBConfig dbConfig) {
        MonitorResult result = new MonitorResult();
        // MonitorResult의 apiConfigId 필드를 재사용하거나, 별도의 필드(targetId)를 추가할 수 있습니다.
        result.setApiConfigId(dbConfig.getId());
        long startTime = System.currentTimeMillis();
        try {
            // DBConfig에 정의된 테스트 쿼리를 실행합니다.
            String testQuery = dbConfig.getTestQuery();
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
        }
        return result;
    }
}
