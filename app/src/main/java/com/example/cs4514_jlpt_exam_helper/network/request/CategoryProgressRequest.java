package com.example.cs4514_jlpt_exam_helper.network.request;

public class CategoryProgressRequest {
    private String level_name;
    private String session_token;

    public CategoryProgressRequest(String level_name, String session_token) {
        this.level_name = level_name;
        this.session_token = session_token;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }
}
