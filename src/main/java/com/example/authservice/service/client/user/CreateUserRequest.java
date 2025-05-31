package com.example.authservice.service.client.user;

import java.time.LocalDate;

public class CreateUserRequest {
    private String name;
    private String gender;
    private String nickname;
    private String avatar;
    private LocalDate birthdate;
    private String email;

    public CreateUserRequest(String name, String gender, String nickname, String avatar, LocalDate birthdate, String email) {
        this.name = name;
        this.gender = gender;
        this.nickname = nickname;
        this.avatar = avatar;
        this.birthdate = birthdate;
        this.email = email;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
