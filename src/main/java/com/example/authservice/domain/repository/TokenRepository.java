package com.example.authservice.domain.repository;

import com.example.authservice.domain.entity.Token;

import java.util.Optional;

public interface TokenRepository {
    void addToken(Token token);

    Optional<Token> getTokenById(Integer id);

    Optional<Token> getTokenByToken(String token);

    Optional<Token> getTokenByEmail(String email);

    boolean deleteToken(Integer id);

    int deleteExpiredTokens();
}
