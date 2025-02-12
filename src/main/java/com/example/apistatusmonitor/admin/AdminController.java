package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.config.ApiConfig;
import com.example.apistatusmonitor.config.DBConfig;
import com.example.apistatusmonitor.config.NotificationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
    전체 API 설정, 알림 설정, DB 설정을 관리하는 컨트롤러 클래스
 */

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/api")
    public ResponseEntity<List<ApiConfig>> getApiConfigList() {
        log.info("Getting API config list");
        return ResponseEntity.ok(adminService.getApiConfigList());
    }

    @PutMapping("/api/{config-id}")
    public ResponseEntity<Long> updateApiConfig(@PathVariable("config-id") Long configId,
                                             @RequestBody ApiConfig apiConfig) {
        log.info("Updating API config with id: {}", configId);
        return ResponseEntity.ok(adminService.updateApiConfig(configId, apiConfig));
    }

    @PostMapping("/api")
    public ResponseEntity<Long> addApiConfig(@RequestBody ApiConfig apiConfig) {
        log.info("Adding new API config");
        return ResponseEntity.ok(adminService.addApiConfig(apiConfig));
    }

    @DeleteMapping("/api/{config-id}")
    public ResponseEntity<?> deleteApiConfig(@PathVariable("config-id") Long configId) {
        log.info("Deleting API config with id: {}", configId);
        adminService.deleteApiConfig(configId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationConfig>> getNotificationConfigList() {
        log.info("Getting notification config list");
        return ResponseEntity.ok(adminService.getNotificationConfigList());
    }

    @PutMapping("/notification/{config-id}")
    public ResponseEntity<Long> updateNotificationConfig(
            @PathVariable("config-id") Long configId,
            @RequestBody NotificationConfig notificationConfig) {
        log.info("Updating notification config with id: {}", configId);
        return ResponseEntity.ok(adminService.updateNotificationConfig(configId, notificationConfig));
    }

    @PostMapping("/notification")
    public ResponseEntity<Long> addNotificationConfig(@RequestBody NotificationConfig notificationConfig) {
        log.info("Adding new notification config");
        return ResponseEntity.ok(adminService.addNotificationConfig(notificationConfig));
    }

    @DeleteMapping("/notification/{config-id}")
    public ResponseEntity<?> deleteNotificationConfig(@PathVariable("config-id") Long configId) {
        log.info("Deleting notification config with id: {}", configId);
        adminService.deleteNotificationConfig(configId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/db")
    public ResponseEntity<List<DBConfig>>getDbConfigList() {
        log.info("Getting DB config list");
        return ResponseEntity.ok(adminService.getDbConfigList());
    }

    @PutMapping("/db/{config-id}")
    public ResponseEntity<?> updateDbConfig(
            @PathVariable("config-id") Long configId,
            @RequestBody DBConfig dbConfig) {
        log.info("Updating DB config");
        return ResponseEntity.ok(adminService.updateDbConfig(configId, dbConfig));
    }

    @PostMapping("/db")
    public ResponseEntity<?> addDbConfig(@RequestBody DBConfig dbConfig) {
        return ResponseEntity.ok(adminService.addDbConfig(dbConfig));
    }

    @DeleteMapping("/db/{config-id}")
    public ResponseEntity<?> deleteDbConfig(@PathVariable("config-id") Long configId) {
        adminService.deleteDbConfig(configId);
        return ResponseEntity.ok(null);
    }
}
