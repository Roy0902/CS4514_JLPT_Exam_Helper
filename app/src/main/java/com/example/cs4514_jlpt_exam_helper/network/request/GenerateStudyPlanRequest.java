package com.example.cs4514_jlpt_exam_helper.network.request;

public class GenerateStudyPlanRequest {
    private String current_level;
    private String target_level;
    private int daily_study_time;
    private int days_to_exam;
    private String target_goal;
    private String session_token;

    public GenerateStudyPlanRequest(String current_level, String target_level,
                                    int daily_study_time, int days_to_exam,
                                    String target_score, String session_token) {
        this.current_level = current_level;
        this.target_level = target_level;
        this.daily_study_time = daily_study_time;
        this.days_to_exam = days_to_exam;
        this.target_goal = target_score;
        this.session_token = session_token;
    }

    public String getCurrent_level() {
        return current_level;
    }

    public void setCurrent_level(String current_level) {
        this.current_level = current_level;
    }

    public String getTarget_level() {
        return target_level;
    }

    public void setTarget_level(String target_level) {
        this.target_level = target_level;
    }

    public int getDaily_study_time() {
        return daily_study_time;
    }

    public void setDaily_study_time(int daily_study_time) {
        this.daily_study_time = daily_study_time;
    }

    public int getDays_to_exam() {
        return days_to_exam;
    }

    public void setDays_to_exam(int days_to_exam) {
        this.days_to_exam = days_to_exam;
    }

    public String getTarget_goal() {
        return target_goal;
    }

    public void setTarget_goal(String target_goal) {
        this.target_goal = target_goal;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }
}
