package com.example.authservice.domain.exception;

public class ConflictResourceException extends HttpException {
    public ConflictResourceException(String message) {
        super(message, 409);
    }
} 