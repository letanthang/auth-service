package com.example.authservice.dto;

import java.time.LocalDate;

public class UserRegisterRequest {
    private String email;
    private String password;
    private String name;
    private String gender;
    private String nickname;
    private String avatar;
    private LocalDate birthdate;

    public UserRegisterRequest() {

    }

    public UserRegisterRequest(String email, String password, String name, String gender, String nickname, String avatar, LocalDate birthdate) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.nickname = nickname;
        this.avatar = avatar;
        this.birthdate = birthdate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
} 