package com.example.authservice.usecase;

import com.example.authservice.domain.AuthUser;
import com.example.authservice.dto.UserRegisterRequest;

public interface RegisterUseCase {
    AuthUser register(UserRegisterRequest request);
} 