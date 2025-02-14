package com.example.apistatusmonitor.integration;

import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfig;
import com.example.apistatusmonitor.monitoring.config.ApiMonitoringConfigManager;
import com.example.apistatusmonitor.monitoring.config.repository.ApiMonitoringConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApiMonitoringConfigRepository apiMonitoringConfigRepository;

    @Autowired
    private ApiMonitoringConfigManager apiMonitoringConfigManager;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        // 기존 데이터 삭제
        apiMonitoringConfigRepository.deleteAll();
        apiMonitoringConfigRepository.flush();

        // 샘플 API 설정 데이터를 저장
        ApiMonitoringConfig apiMonitoringConfig1 = new ApiMonitoringConfig();
        apiMonitoringConfig1.setName("API One");
        // 필요한 다른 필드도 설정

        ApiMonitoringConfig apiMonitoringConfig2 = new ApiMonitoringConfig();
        apiMonitoringConfig2.setName("API Two");
        // 필요한 다른 필드도 설정

        List<ApiMonitoringConfig> savedConfigs = apiMonitoringConfigRepository.saveAll(List.of(apiMonitoringConfig1, apiMonitoringConfig2));

        apiMonitoringConfigManager.loadConfig();
    }

    // 1. API Config 조회 테스트 (순서에 의존하지 않는 검증)
    @Test
    public void testGetApiConfigList() throws Exception {
        mockMvc.perform(get("/admin/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // 배열의 크기를 검증
                .andExpect(jsonPath("$", hasSize(2)))
                // API One과 API Two가 존재하는지 검증 (순서 무관)
                .andExpect(jsonPath("$[?(@.name=='API One')]").exists())
                .andExpect(jsonPath("$[?(@.name=='API Two')]").exists());
    }

    // 2. API Config 업데이트 테스트
    @Test
    public void testUpdateApiConfig() throws Exception {
        // "API One" 이름을 가진 기존 API Config 엔티티를 조회
        ApiMonitoringConfig existingConfig = apiMonitoringConfigRepository.findByName("API One")
                .orElseThrow(() -> new IllegalArgumentException("API One not found"));
        Long expectedId = existingConfig.getId();

        // 업데이트할 데이터 생성 (이름만 변경)
        ApiMonitoringConfig updatedConfig = new ApiMonitoringConfig();
        updatedConfig.setName("Updated API One");

        // PUT 요청을 수행하고, 반환된 ID가 expectedId와 일치하는지 검증
        mockMvc.perform(put("/admin/api/{api-id}", expectedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedConfig)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedId)));
    }


        // 3. API Config 추가 테스트
        @Test
        public void testAddApiConfig() throws Exception {
            // 현재 저장된 API Config의 개수를 확인
            long countBefore = apiMonitoringConfigRepository.count();

            // 새로운 API Config 생성 (ID는 자동 생성되도록 설정)
            ApiMonitoringConfig newConfig = new ApiMonitoringConfig();
            newConfig.setName("New API");

            // POST 요청 수행하여 새로운 API Config 추가
            String response = mockMvc.perform(post("/admin/api")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(newConfig)))
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();

            // 응답으로 새로 추가된 API Config의 ID가 반환된다고 가정 (예: "3")
            Long newId = Long.valueOf(response);

            // Repository의 API Config 개수가 1 증가했는지 확인
            long countAfter = apiMonitoringConfigRepository.count();
            assertEquals(countBefore + 1, countAfter, "API Config가 하나 추가되어야 합니다.");

            // GET 요청으로 전체 API Config 목록을 조회하고, JSON 배열의 크기가 Repository의 개수와 일치하는지 검증
            mockMvc.perform(get("/admin/api"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$", hasSize((int) countAfter)));
        }

    // 4. API Config 삭제 테스트
    @Test
    public void testDeleteApiConfig() throws Exception {
        mockMvc.perform(delete("/admin/api/{api-id}", 1))
                .andExpect(status().isOk());
    }
}