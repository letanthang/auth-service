package com.example.authservice.dto;

public class VerifyResponse {
    private boolean active;

    public VerifyResponse(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
} 