package com.example.authservice.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_users")
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userID;

    @Column(name = "uuid", nullable = false, unique = true)
    private Long uuid;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public AuthUser() {
    }

    public AuthUser(Integer id, Integer userID, Long uuid, String email, String password, Role role) {
        this.id = id;
        this.userID = userID;
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

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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
