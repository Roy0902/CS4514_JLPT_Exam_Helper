package com.example.cs4514_jlpt_exam_helper.data;

import java.io.Serializable;

public class Question implements Serializable {
    private int question_id;
    private String question_title;
    private String question_description;
    private String user_name;
    private int reply_number;

    public Question(int question_id, String question_title, String question_description, String user_name, int answer_number) {
        this.question_id = question_id;
        this.question_title = question_title;
        this.question_description = question_description;
        this.user_name = user_name;
        this.reply_number = answer_number;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_description() {
        return question_description;
    }

    public void setQuestion_description(String question_description) {
        this.question_description = question_description;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getReply_number() {
        return reply_number;
    }

    public void setReply_number(int reply_number) {
        this.reply_number = reply_number;
    }
}
