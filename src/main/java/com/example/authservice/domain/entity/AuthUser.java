package com.example.authservice.domain.entity;


public class AuthUser {
    private Integer id;
    private Integer userId;
    private Long uuid;
    private String email;
    private String password;
    private Role role;
    public static final String TABLE_NAME = "auth_users";

    public AuthUser() {
    }

    public AuthUser(Integer id, Integer userID, Long uuid, String email, String password, Role role) {
        this.id = id;
        this.userId = userID;
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getUUID() {
        return uuid;
    }

    public void setUUID(Long uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
