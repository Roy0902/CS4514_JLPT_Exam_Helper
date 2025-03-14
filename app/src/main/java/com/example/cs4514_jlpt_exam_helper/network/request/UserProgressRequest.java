package com.example.cs4514_jlpt_exam_helper.network.request;

public class UserProgressRequest {
    private String level;
    private String sessionToken;

    public UserProgressRequest(String level, String sessionToken) {
        this.level = level;
        this.sessionToken = sessionToken;
    }

    // Getters and setters (optional, but good practice)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
