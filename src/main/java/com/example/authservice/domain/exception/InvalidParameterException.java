package com.example.authservice.domain.exception;

public class InvalidParameterException extends HttpException {
    public InvalidParameterException(String message) {
        super(message, 400);
    }
} 