package com.example.cs4514_jlpt_exam_helper.network.request;

public class PostQuestionRequest {

    private String session_token;
    private String question_title;
    private String question_description;

    public PostQuestionRequest(String session_token, String question_title, String question_description) {
        this.session_token = session_token;
        this.question_title = question_title;
        this.question_description = question_description;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
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
}
