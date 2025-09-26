package com.example.authservice.domain.usecase.impl;

import com.example.authservice.domain.repository.AuthUserRepository;
import com.example.authservice.domain.usecase.UpdatePasswordUseCase;
import com.example.authservice.domain.usecase.result.UpdatePasswordUseCaseResult;
import org.mindrot.jbcrypt.BCrypt;

public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {
    private final AuthUserRepository authUserRepository;

    public UpdatePasswordUseCaseImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public UpdatePasswordUseCaseResult update(String email, String oldPassword, String newPassword) {
        var user = this.authUserRepository.getAuthUserByEmail(email);

        if (user.isEmpty()) {
            return new UpdatePasswordUseCaseResult.UserNotFound();
        }

        var authUser = user.get();
        if (!BCrypt.checkpw(oldPassword, authUser.getPassword())) {
            return new UpdatePasswordUseCaseResult.OldPasswordMismatched();
        }

        authUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

        authUserRepository.updateAuthUser(authUser);

        return new UpdatePasswordUseCaseResult.Success();
    }
} 