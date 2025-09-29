package com.example.authservice.domain.exception;

public class NotFoundTokenException extends HttpException {
    public NotFoundTokenException(String message) {
        super(message, 404);
    }
} 