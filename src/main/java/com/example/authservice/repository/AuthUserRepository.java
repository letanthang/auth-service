package com.example.authservice.repository;

import java.util.Optional;

import com.example.authservice.domain.AuthUser;

import java.util.List;

public interface AuthUserRepository {
    void addAuthUser(AuthUser authUser);
    Optional<AuthUser> getAuthUserById(Integer id);
    Optional<AuthUser> getAuthUserByEmail(String email);
    boolean deleteAuthUser(Integer id);
    boolean updateAuthUser(Integer id, AuthUser authUser);
}
