package com.example.cs4514_jlpt_exam_helper.api.data;

import java.sql.Timestamp;

public class Account {

    private String account_id;
    private String email;
    private String user_name;
    private String password;
    private int is_active;
    private int failed_login_attempts;
    private Timestamp last_login_at;

    public Account(String email){
        this.email = email;
    }

    public Account(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Account(String email, String user_name, String password){
        this.user_name = user_name;
        this.email = email;
        this.password = password;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
