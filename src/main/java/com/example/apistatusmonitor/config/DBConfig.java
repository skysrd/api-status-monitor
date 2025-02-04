package com.example.apistatusmonitor.config;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "db_config")
@Getter
public class DBConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    public void updateConfig(DBConfig dbConfig) {
    }
}
