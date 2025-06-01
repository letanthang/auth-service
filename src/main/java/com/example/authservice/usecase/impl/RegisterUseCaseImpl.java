package com.example.authservice.usecase.impl;

import com.example.authservice.domain.AuthUser;
import com.example.authservice.dto.UserRegisterRequest;
import com.example.authservice.repository.AuthUserRepository;
import com.example.authservice.service.client.UserServiceClient;
import com.example.authservice.service.client.user.CreateUserRequest;
import com.example.authservice.usecase.RegisterUseCase;
import io.javalin.http.HttpResponseException;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterUseCaseImpl implements RegisterUseCase {
    private final AuthUserRepository authUserRepository;
    public RegisterUseCaseImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }
    @Override
    public AuthUser register(UserRegisterRequest request) {
        CreateUserRequest createUserRequest = new CreateUserRequest(request.getName(), request.getGender(), request.getNickname(), request.getAvatar(), request.getBirthdate(), request.getEmail());
        UserServiceClient userServiceClient = new UserServiceClient();
        try {
            var id = userServiceClient.addUser(createUserRequest);
        } catch (HttpResponseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        var authUser =  new AuthUser(null, request.getEmail(), hashedPassword);
        this.authUserRepository.addAuthUser(authUser);

        return authUser;
    }
} 