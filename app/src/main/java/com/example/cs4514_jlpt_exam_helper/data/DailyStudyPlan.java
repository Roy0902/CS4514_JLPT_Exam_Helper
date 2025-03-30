package com.example.cs4514_jlpt_exam_helper.data;

public class DailyStudyPlan {
    private int daily_plan_id;
    private boolean is_completed;

    public DailyStudyPlan(int daily_plan_id, boolean is_completed) {
        this.daily_plan_id = daily_plan_id;
        this.is_completed = is_completed;
    }

    public int getDaily_plan_id() {
        return daily_plan_id;
    }

    public void setDaily_plan_id(int daily_plan_id) {
        this.daily_plan_id = daily_plan_id;
    }

    public boolean isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(boolean is_completed) {
        this.is_completed = is_completed;
    }
}
