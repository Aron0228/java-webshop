package com.example.javawebshop.dto;

public class LoginResponse {
    private Long userId;
    private String responseMessage;

    public LoginResponse(Long userId, String responseMessage) {
        this.userId = userId;
        this.responseMessage = responseMessage;
    }

    public Long getUserId() { return userId; }
    public String getResponseMessage() { return responseMessage; }
}
