package com.example.authservice.exception;

public class NotFoundTokenException extends RuntimeException {
    public NotFoundTokenException(String message) {
        super(message);
    }
} 