package com.example.apistatusmonitor.config.repository;

import com.example.apistatusmonitor.config.ApiConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiConfigRepository extends JpaRepository<ApiConfig, Long> {
    Optional<ApiConfig> findByName(String name);
}
