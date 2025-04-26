package com.example.javawebshop.auth;

public class RegistrationResponse {
    private int responseCode;
    private String responseMessage;

    public RegistrationResponse(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
    public int getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
