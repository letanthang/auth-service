package com.example.authservice.config;

public class Config {
    public static String DB_URL;
    public static String DB_USER;
    public static String DB_PASS;
    public static int PORT;
    public static String JWT_SECRET;
    public static String USER_SERVICE_URL;

    public static void load() {
        DB_URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/authdb");
        DB_USER = System.getenv().getOrDefault("DB_USER", "root");
        DB_PASS = System.getenv().getOrDefault("DB_PASS", "");
        PORT = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        JWT_SECRET = System.getenv().getOrDefault("JWT_SECRET", "");
        USER_SERVICE_URL = System.getenv().getOrDefault("USER_SERVICE_URL", "");
    }
}