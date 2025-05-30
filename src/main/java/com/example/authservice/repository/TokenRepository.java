package com.example.authservice.repository;

import com.example.authservice.domain.Token;

import java.util.Optional;

public interface TokenRepository {
    void addToken(Token token);
    Optional<Token> getTokenById(Integer id);
    Optional<Token> getTokenByToken(String token);
    Optional<Token> getTokenByEmail(String email);
    boolean deleteToken(Integer id);
}
