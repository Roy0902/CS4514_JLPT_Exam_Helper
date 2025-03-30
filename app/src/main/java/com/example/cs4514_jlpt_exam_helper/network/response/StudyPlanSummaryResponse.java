package com.example.cs4514_jlpt_exam_helper.network.response;

public class StudyPlanSummaryResponse {
    private int completed_study_plan;
    private int total_study_plan;
    private int fitness_score = -11111;

    public StudyPlanSummaryResponse(int completedStudyPlan, int totalStudyPlan, int fitness_score) {
        this.completed_study_plan = completedStudyPlan;
        this.total_study_plan = totalStudyPlan;
        this.fitness_score = fitness_score;
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

    public int getFitness_score() {
        return fitness_score;
    }

    public void setFitness_score(int fitness_score) {
        this.fitness_score = fitness_score;
    }
}
