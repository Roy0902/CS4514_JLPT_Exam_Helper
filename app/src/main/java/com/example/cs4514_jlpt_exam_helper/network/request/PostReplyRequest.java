package com.example.cs4514_jlpt_exam_helper.network.request;

public class PostReplyRequest {
    private String session_token;
    private String reply_text;
    private int question_id;

    public PostReplyRequest(String session_token, String reply, int question_id) {
        this.session_token = session_token;
        this.reply_text = reply;
        this.question_id = question_id;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public String getReply_text() {
        return reply_text;
    }

    public void setReply_text(String reply_text) {
        this.reply_text = reply_text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}
