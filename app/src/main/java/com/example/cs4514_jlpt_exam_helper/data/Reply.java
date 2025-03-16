package com.example.cs4514_jlpt_exam_helper.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reply implements Serializable {
    private int question_id;
    private int reply_id;
    private String reply;
    private String user_name;
    private Timestamp reply_at;

    public Reply(int question_id, int reply_id, String reply, String user_name, Timestamp reply_at) {
        this.question_id = question_id;
        this.reply_id = reply_id;
        this.reply = reply;
        this.user_name = user_name;
        this.reply_at = reply_at;
    }

    public Reply(String reply, String user_name) {
        this.reply = reply;
        this.user_name = user_name;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Timestamp getReply_at() {
        return reply_at;
    }

    public void setReply_at(Timestamp reply_at) {
        this.reply_at = reply_at;
    }
}
