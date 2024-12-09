package com.welcomehome.register;

public class LoginResponse {
    private boolean success;
    private String userName;
    private String message;

    // Constructor
    public LoginResponse(boolean success, String userName, String message) {
        this.success = success;
        this.userName = userName;
        this.message = message;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
