package com.example.apistatusmonitor.config.repository;

import com.example.apistatusmonitor.config.ApiConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiConfigRepository extends JpaRepository<ApiConfig, Long> {
}
