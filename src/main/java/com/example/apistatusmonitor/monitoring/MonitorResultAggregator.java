package com.example.apistatusmonitor.monitoring;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MonitorResultAggregator {

    // configId별로 결과를 저장하는 Map. 스레드 안전한 ConcurrentHashMap 사용.
    private final Map<Long, List<MonitorResult>> resultsMap = new ConcurrentHashMap<>();

    /**
     * 지정된 모니터링 구성(configId)에 대한 결과를 추가합니다.
     * @param result 모니터링 결과
     */
    public void addResult(MonitorResult result) {
        // configId를 key로 사용하여 결과 리스트를 가져오거나 새로 생성
        resultsMap.computeIfAbsent(result.getConfigId(), id -> Collections.synchronizedList(new ArrayList<>()))
                .add(result);
    }

    /**
     * 특정 configId에 대한 결과 목록을 가져오고, 해당 항목을 제거합니다.
     * @param configId 모니터링 구성 ID
     * @return 해당 구성의 모니터링 결과 리스트 (없으면 빈 리스트)
     */
    public List<MonitorResult> getAndClearResultsForConfig(Long configId) {
        List<MonitorResult> results = resultsMap.getOrDefault(configId, new ArrayList<>());
        resultsMap.remove(configId);
        return results;
    }

    /**
     * 모든 구성에 대해 결과 목록을 가져오고, 맵을 초기화합니다.
     * @return configId와 결과 리스트의 맵
     */
    public Map<Long, List<MonitorResult>> getAndClearAllResults() {
        Map<Long, List<MonitorResult>> copy = new ConcurrentHashMap<>(resultsMap);
        resultsMap.clear();
        return copy;
    }
}