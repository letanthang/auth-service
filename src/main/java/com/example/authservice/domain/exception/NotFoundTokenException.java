package com.example.authservice.domain.exception;

public class NotFoundTokenException extends RuntimeException {
    public NotFoundTokenException(String message) {
        super(message);
    }
} 