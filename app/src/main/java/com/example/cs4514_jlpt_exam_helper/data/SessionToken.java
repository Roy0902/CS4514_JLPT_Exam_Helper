package com.example.cs4514_jlpt_exam_helper.data;

public class SessionToken {

    private String email;
    private String token;
    private int is_revoked;

    public SessionToken(String token){
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_revoked() {
        return is_revoked;
    }

    public void setIs_revoked(int is_revoked) {
        this.is_revoked = is_revoked;
    }
}
