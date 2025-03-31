package com.example.cs4514_jlpt_exam_helper.network.request;

public class UpdateStudyPlanProgressRequest {
    private String session_token;
    private int study_plan_item_id;

    public UpdateStudyPlanProgressRequest(String session_token, int study_plan_item_id) {
        this.session_token = session_token;
        this.study_plan_item_id = study_plan_item_id;
    }

    public String getSession_token() {
        return session_token;
    }

    public void setSession_token(String session_token) {
        this.session_token = session_token;
    }

    public int getStudy_plan_item_id() {
        return study_plan_item_id;
    }

    public void setStudy_plan_item_id(int study_plan_item_id) {
        this.study_plan_item_id = study_plan_item_id;
    }
}
