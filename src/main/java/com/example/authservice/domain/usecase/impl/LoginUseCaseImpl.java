package com.example.authservice.domain.usecase.impl;

import com.example.authservice.domain.entity.Token;
import com.example.authservice.domain.exception.InternalServerException;
import com.example.authservice.domain.exception.UnauthorizedUserException;
import com.example.authservice.domain.repository.AuthUserRepository;
import com.example.authservice.domain.repository.TokenRepository;
import com.example.authservice.domain.usecase.LoginUseCase;
import com.example.authservice.infrastructure.nats.AuthUserLoginEvent;
import com.example.authservice.infrastructure.nats.NatsJetStreamClient;
import com.example.authservice.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;

public class LoginUseCaseImpl implements LoginUseCase {
    private final AuthUserRepository authUserRepository;
    private final TokenRepository tokenRepository;
    private final NatsJetStreamClient natBroker;
    private final String userLoginSubject;

    public LoginUseCaseImpl(AuthUserRepository authUserRepository, TokenRepository tokenRepository, NatsJetStreamClient natBroker, String userLoginSubject) {
        this.authUserRepository = authUserRepository;
        this.tokenRepository = tokenRepository;
        this.natBroker = natBroker;
        this.userLoginSubject = userLoginSubject;
    }

    @Override
    public String login(String email, String password) {
        var user = this.authUserRepository.getAuthUserByEmail(email);

        if (user.isEmpty()) {
            throw new UnauthorizedUserException();
        }

        var authUser = user.get();
        if (!BCrypt.checkpw(password, authUser.getPassword())) {
            throw new UnauthorizedUserException();
        }

        String strToken = JwtService.generateToken(email, authUser.getUserId(), authUser.getUUID(), authUser.getRole());

        Token token = new Token(email, strToken, JwtService.getExpirationDate());
        this.tokenRepository.addToken(token);

        try {
            AuthUserLoginEvent event = new AuthUserLoginEvent(authUser.getUserId(), email);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(event);
            natBroker.publish(this.userLoginSubject, json);
        } catch (Exception e) {
            throw new InternalServerException("Nat error: " + e.getMessage());
        }
        return strToken;
    }
} 