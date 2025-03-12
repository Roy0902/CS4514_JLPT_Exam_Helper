package com.example.cs4514_jlpt_exam_helper.api.data;

public class Otp {
    private String email;
    private String otp_code;
    private boolean is_used;

    public Otp(String email, String otp_code){
        this.email = email;
        this.otp_code = otp_code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp_code() {
        return otp_code;
    }

    public void setOtp_code(String otp_code) {
        this.otp_code = otp_code;
    }

    public boolean isIs_used() {
        return is_used;
    }

    public void setIs_used(boolean is_used) {
        this.is_used = is_used;
    }
}
