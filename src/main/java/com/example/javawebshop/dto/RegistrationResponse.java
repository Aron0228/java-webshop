package com.example.javawebshop.dto;

public class RegistrationResponse {
    private String responseMessage;

    public RegistrationResponse(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
