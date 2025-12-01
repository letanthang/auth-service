package com.example.authservice.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class PersistenceConfig {
    public static EntityManagerFactory createEntityManagerFactory() {
        AppConfig config = AppConfig.GetInstance();
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", config.getDbUrl());
        properties.put("javax.persistence.jdbc.user", config.getDbUser());
        properties.put("javax.persistence.jdbc.password", config.getDbPass());

        return Persistence.createEntityManagerFactory("auth_userPU", properties);
    }
} 