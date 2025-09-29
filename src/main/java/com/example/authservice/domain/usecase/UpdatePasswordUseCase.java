package com.example.authservice.domain.usecase;

public interface UpdatePasswordUseCase {
    void update(String email, String oldPassword, String newPassword);
}