package com.example.authservice.usecase.impl;

import com.example.authservice.domain.Token;
import com.example.authservice.usecase.LoginUseCase;
import com.example.authservice.service.JwtService;
import com.example.authservice.repository.TokenRepository;
import com.example.authservice.repository.AuthUserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class LoginUseCaseImpl implements LoginUseCase {
    private final AuthUserRepository authUserRepository;
    private final TokenRepository tokenRepository;
    public LoginUseCaseImpl(AuthUserRepository authUserRepository, TokenRepository tokenRepository) {
        this.authUserRepository = authUserRepository;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public String login(String email, String password) {
        var user = this.authUserRepository.getAuthUserByEmail(email);

        if (user.isEmpty()) {
            return "";
        }

        var authUser = user.get();
        if (!BCrypt.checkpw(password, authUser.getPassword())) {
            return "";
        }

        String strToken = JwtService.generateToken(email, authUser.getRole());

        Token token = new Token(email, strToken, JwtService.getExpirationDate());
        this.tokenRepository.addToken(token);

        return strToken;
    }
} 