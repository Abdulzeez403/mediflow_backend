package com.mediflow.mediflow.user.entity;

public class UserPrincipal {

    private String userId;
    private String email;

    public UserPrincipal(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}