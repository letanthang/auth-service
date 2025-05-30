package com.example.authservice.usecase.impl;

import com.example.authservice.domain.AuthUser;
import com.example.authservice.dto.UserRegisterRequest;
import com.example.authservice.usecase.RegisterUseCase;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterUseCaseImpl implements RegisterUseCase {
    @Override
    public AuthUser register(UserRegisterRequest request) {
        // TODO: Add email validation and duplicate check
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        return new AuthUser(null, request.getEmail(), hashedPassword);
    }
} 