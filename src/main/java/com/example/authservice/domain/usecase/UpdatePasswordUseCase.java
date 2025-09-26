package com.example.authservice.domain.usecase;

import com.example.authservice.domain.usecase.result.UpdatePasswordUseCaseResult;

public interface UpdatePasswordUseCase {
    UpdatePasswordUseCaseResult update(String email, String oldPassword, String newPassword);
} 