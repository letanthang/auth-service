package com.example.authservice.domain.usecase.impl;

import com.example.authservice.domain.exception.InvalidParameterException;
import com.example.authservice.domain.exception.NotFoundUserException;
import com.example.authservice.domain.repository.AuthUserRepository;
import com.example.authservice.domain.usecase.UpdatePasswordUseCase;
import org.mindrot.jbcrypt.BCrypt;

public class UpdatePasswordUseCaseImpl implements UpdatePasswordUseCase {
    private final AuthUserRepository authUserRepository;

    public UpdatePasswordUseCaseImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public void update(String email, String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw new InvalidParameterException("The new password cannot be the same as the old password");
        }

        var user = this.authUserRepository.getAuthUserByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundUserException("User with email " + email + " not found");
        }

        var authUser = user.get();
        if (!BCrypt.checkpw(oldPassword, authUser.getPassword())) {
            throw new InvalidParameterException("The old password is incorrect");
        }

        authUser.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        authUserRepository.updateAuthUser(authUser);
    }
} 