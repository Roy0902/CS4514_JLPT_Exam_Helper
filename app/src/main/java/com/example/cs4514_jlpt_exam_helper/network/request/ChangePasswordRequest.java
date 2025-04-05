package com.example.cs4514_jlpt_exam_helper.network.request;

public class ChangePasswordRequest {
    private String session_token;
    private String old_password;
    private String new_password;

    public ChangePasswordRequest(String session_token, String old_password, String new_password) {
        this.session_token = session_token;
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
