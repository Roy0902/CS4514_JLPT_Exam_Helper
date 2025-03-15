package com.example.cs4514_jlpt_exam_helper.data;

public class Reply {
    private int question_id;
    private int reply_id;
    private String reply;
    private String user_name;


    public Reply(int question_id, int reply_id, String reply, String user_name) {
        this.question_id = question_id;
        this.reply_id = reply_id;
        this.reply = reply;
        this.user_name = user_name;
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

}
