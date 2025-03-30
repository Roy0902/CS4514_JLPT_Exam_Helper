package com.example.cs4514_jlpt_exam_helper.network.request;

public class FirebaseTokenRequest {
    private String session_token;
    private String firebase_token;

    public FirebaseTokenRequest(String session_token, String firebase_token) {
        this.session_token = session_token;
        this.firebase_token = firebase_token;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }
}
