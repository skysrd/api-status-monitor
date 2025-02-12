package com.example.apistatusmonitor.monitoring;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MonitorResult {
    private Long apiConfigId;
    private LocalDateTime timestamp;
    private String status;       // "SUCCESS" 또는 "FAILURE"
    private long responseTime;   // 밀리초 단위 응답 시간
    private String response;     // API 응답 내용 (성공 시)
    private String errorMessage; // 실패 시 예외 메시지
}