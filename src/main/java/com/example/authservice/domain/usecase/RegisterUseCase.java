package com.example.authservice.domain.usecase;

import com.example.authservice.domain.entity.AuthUser;
import com.example.authservice.dto.UserRegisterRequest;

public interface RegisterUseCase {
    AuthUser register(UserRegisterRequest request);
} 