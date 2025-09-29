package com.example.authservice.domain.exception;

public class NotFoundUserException extends HttpException {
    public NotFoundUserException(String message) {
        super(message, 404);
    }
} 