package com.example.authservice.domain.usecase.result;

public sealed interface UpdatePasswordUseCaseResult permits UpdatePasswordUseCaseResult.Success, UpdatePasswordUseCaseResult.UserNotFound, UpdatePasswordUseCaseResult.OldPasswordMismatched {
    record Success() implements UpdatePasswordUseCaseResult {
    }

    record UserNotFound() implements UpdatePasswordUseCaseResult {
    }

    record OldPasswordMismatched() implements UpdatePasswordUseCaseResult {
    }
}

