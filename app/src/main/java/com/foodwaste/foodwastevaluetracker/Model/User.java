package com.foodwaste.foodwastevaluetracker.Model;

public class User {
    String Username;
    String UserId;
    String Email;

    public User() {
    }

    public User(String username, String UserId, String email) {
        Username = username;
        this.UserId = UserId;
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        this.UserId = userId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}