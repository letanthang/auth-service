package com.example.authservice.domain.entity;

import jakarta.persistence.*;

import java.time.Instant;


@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private String email;
    private Instant expired_at;

    public Token() {
    }

    public Token(String email, String token, Instant expired_at) {
        this.token = token;
        this.email = email;
        this.expired_at = expired_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Instant expired_at) {
        this.expired_at = expired_at;
    }
}
