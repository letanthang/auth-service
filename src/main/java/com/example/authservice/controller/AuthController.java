package com.example.authservice.controller;

import com.example.authservice.domain.AuthUser;
import com.example.authservice.dto.*;
import com.example.authservice.exception.UnauthorizedUserException;
import com.example.authservice.usecase.*;
import io.javalin.http.Handler;

public class AuthController {
    public final Handler login;
    public final Handler logout;
    public final Handler register;
    public final Handler verify;

    public AuthController(LoginUseCase loginUseCase,
                         LogoutUseCase logoutUseCase,
                         RegisterUseCase registerUseCase,
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
                ctx.status(401);
            }
        };
    }
}
