package com.example.apistatusmonitor.admin;

import com.example.apistatusmonitor.config.ApiConfig;
import com.example.apistatusmonitor.config.NotificationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/api")
    public ResponseEntity<List<ApiConfig>> getApiConfigList() {
        return ResponseEntity.ok(adminService.getApiConfigList());
    }

    @PutMapping("/api/{api-id}")
    public ResponseEntity<Long> updateApiConfig(@PathVariable("api-id") Long apiId,
                                             @RequestBody ApiConfig apiConfig) {
        return ResponseEntity.ok(adminService.updateApiConfig(apiId, apiConfig));
    }

    @PostMapping("/api")
    public ResponseEntity<Long> addApiConfig(@RequestBody ApiConfig apiConfig) {
        return ResponseEntity.ok(adminService.addApiConfig(apiConfig));
    }

    @DeleteMapping("/api/{api-id}")
    public ResponseEntity<?> deleteApiConfig(@PathVariable("api-id") Long apiId) {
        adminService.deleteApiConfig(apiId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationConfig>> getNotificationConfigList() {
        return ResponseEntity.ok(adminService.getNotificationConfigList());
    }

    @PutMapping("/notification/{notification-id}")
    public ResponseEntity<Long> updateNotificationConfig(
            @PathVariable("notification-id") Long notificationId,
            @RequestBody NotificationConfig notificationConfig) {
        return ResponseEntity.ok(adminService.updateNotificationConfig(notificationId, notificationConfig));
    }

    @PostMapping("/notification")
    public ResponseEntity<Long> addNotificationConfig(@RequestBody NotificationConfig notificationConfig) {
        return ResponseEntity.ok(adminService.addNotificationConfig(notificationConfig));
    }

    @DeleteMapping("/notification/{notification-id}")
    public ResponseEntity<?> deleteNotificationConfig(@PathVariable("notification-id") Long notificationId) {
        adminService.deleteNotificationConfig(notificationId);
        return ResponseEntity.ok(null);
    }

    /*@GetMapping("/db")
    public ResponseEntity<?> getDbConfigList() {
        return ResponseEntity.ok(adminService.getDbConfigList());
    }

    @PutMapping("/db/{db-id}")
    public ResponseEntity<?> updateDbConfig(@RequestBody DBConfig dbConfig) {
        return ResponseEntity.ok(adminService.updateDbConfig(dbConfig));
    }

    @PostMapping("/db")
    public ResponseEntity<?> addDbConfig(@RequestBody DBConfig dbConfig) {
        return ResponseEntity.ok(adminService.addDbConfig(dbConfig));
    }

    @DeleteMapping("/db/{db-id}")
    public ResponseEntity<?> deleteDbConfig(@PathVariable("db-id") Long dbId) {
        return ResponseEntity.ok(adminService.deleteDbConfig(dbId));
    }*/
}
