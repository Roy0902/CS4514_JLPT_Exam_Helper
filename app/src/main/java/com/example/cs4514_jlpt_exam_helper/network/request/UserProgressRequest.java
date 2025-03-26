package com.example.cs4514_jlpt_exam_helper.network.request;

public class UserProgressRequest {
    private String subtopic_name;
    private String session_token;

    public UserProgressRequest(String subtopic_name, String session_token) {
        this.subtopic_name = subtopic_name;
        this.session_token = session_token;
    }

    public String getSubtopic_name() {
        return subtopic_name;
    }

    public void setSubtopic_name(String subtopic_name) {
        this.subtopic_name = subtopic_name;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }
}
