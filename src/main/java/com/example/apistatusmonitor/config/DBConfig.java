package com.example.apistatusmonitor.config;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "db_config")
@Getter
@Setter
@ToString
public class DBConfig {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "type")
    private String type;

    @Column(name = "port")
    private int port;

    @Column(name = "test_query")
    private String testQuery;

    public void updateConfig(DBConfig dbConfig) {
        this.name = dbConfig.getName();
        this.url = dbConfig.getUrl();
        this.username = dbConfig.getUsername();
        this.password = dbConfig.getPassword();
        this.type = dbConfig.getType();
        this.port = dbConfig.getPort();
    }
}
