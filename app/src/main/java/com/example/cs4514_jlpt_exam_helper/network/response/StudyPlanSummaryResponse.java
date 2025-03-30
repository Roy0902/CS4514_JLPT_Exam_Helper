package com.example.cs4514_jlpt_exam_helper.network.response;

public class StudyPlanSummaryResponse {
    private int completed_study_plan;
    private int total_study_plan;

    public StudyPlanSummaryResponse(int completedStudyPlan, int totalStudyPlan) {
        this.completed_study_plan = completedStudyPlan;
        this.total_study_plan = totalStudyPlan;
    }

    public int getCompleted_study_plan() {
        return completed_study_plan;
    }

    public void setCompleted_study_plan(int completed_study_plan) {
        this.completed_study_plan = completed_study_plan;
    }

    public int getTotal_study_plan() {
        return total_study_plan;
    }

    public void setTotal_study_plan(int total_study_plan) {
        this.total_study_plan = total_study_plan;
    }


}
