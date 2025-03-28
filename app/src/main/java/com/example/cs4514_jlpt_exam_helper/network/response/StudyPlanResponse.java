package com.example.cs4514_jlpt_exam_helper.network.response;

public class StudyPlanResponse {
    private int completedStudyPlan;
    private int totalStudyPlan;

    public StudyPlanResponse(int completedStudyPlan, int totalStudyPlan) {
        this.completedStudyPlan = completedStudyPlan;
        this.totalStudyPlan = totalStudyPlan;
    }

    public int getCompletedStudyPlan() {
        return completedStudyPlan;
    }

    public void setCompletedStudyPlan(int completedStudyPlan) {
        this.completedStudyPlan = completedStudyPlan;
    }

    public int getTotalStudyPlan() {
        return totalStudyPlan;
    }

    public void setTotalStudyPlan(int totalStudyPlan) {
        this.totalStudyPlan = totalStudyPlan;
    }


}
