package com.example.apistatusmonitor.config.repository;

import com.example.apistatusmonitor.config.DBConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBConfigRepository extends JpaRepository<DBConfig, Long> {
}
