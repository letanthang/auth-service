package com.example.authservice.infrastructure.controller;

import com.example.authservice.domain.entity.AuthUser;
import com.example.authservice.domain.exception.UnauthorizedUserException;
import com.example.authservice.domain.usecase.*;
import com.example.authservice.dto.*;
import com.example.authservice.service.JwtService;
import io.javalin.http.Handler;

public class AuthController {
    public final Handler login;
    public final Handler logout;
    public final Handler register;
    public final Handler updatePassword;
    public final Handler verify;

    public AuthController(LoginUseCase loginUseCase,
                          LogoutUseCase logoutUseCase,
                          RegisterUseCase registerUseCase,
                          UpdatePasswordUseCase updatePasswordUseCase,
                          VerifyTokenUseCase verifyTokenUseCase) {

        this.login = ctx -> {
            var request = ctx.bodyAsClass(LoginRequest.class);
            String token = loginUseCase.login(request.getEmail(), request.getPassword());
            ctx.json(new LoginResponse(token));
        };

        this.logout = ctx -> {
            String token = ctx.header("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                logoutUseCase.logout(token);
            } else {
                throw new UnauthorizedUserException();
            }
            ctx.status(200);
        };

        this.register = ctx -> {
            var request = ctx.bodyAsClass(UserRegisterRequest.class);
            AuthUser authUser = registerUseCase.register(request);
            ctx.json(new RegisterResponse(authUser.getId().toString(), authUser.getEmail()));
        };

        this.verify = ctx -> {
            String token = ctx.header("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                boolean active = verifyTokenUseCase.verifyToken(token);
                ctx.json(new VerifyResponse(active));
            } else {
                throw new UnauthorizedUserException();
            }
        };

        this.updatePassword = ctx -> {
            String token = ctx.header("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                var active = JwtService.validateToken(token);
                if (!active) {
                    throw new UnauthorizedUserException();
                }
            } else {
                throw new UnauthorizedUserException();
            }

            String email = JwtService.getEmailFromToken(token);

            var request = ctx.bodyAsClass(UpdatePasswordRequest.class);
            updatePasswordUseCase.update(email, request.getOldPassword(), request.getPassword());
            ctx.status(204);
        };
    }
}
