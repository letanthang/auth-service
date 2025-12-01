package com.example.authservice.service.client.user;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserResponse {
    public UserResponse data;
}


