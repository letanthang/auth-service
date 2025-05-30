package com.example.authservice.usecase.impl;

import com.example.authservice.repository.TokenRepository;
import com.example.authservice.service.JwtService;
import com.example.authservice.usecase.LogoutUseCase;
import java.util.concurrent.ConcurrentHashMap;

public class LogoutUseCaseImpl implements LogoutUseCase {
    private final TokenRepository tokenRepository;
    public LogoutUseCaseImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(String token) {
        JwtService.validateToken(token);
        var obj = this.tokenRepository.getTokenByToken(token);
        obj.ifPresent(tokenObj -> this.tokenRepository.deleteToken(tokenObj.getId()));
    }
}