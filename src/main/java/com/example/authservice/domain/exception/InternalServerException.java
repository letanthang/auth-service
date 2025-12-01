package com.example.authservice.domain.exception;

public class InternalServerException extends HttpException {
    public InternalServerException(String message) {
        super(message, 500);
    }
} 