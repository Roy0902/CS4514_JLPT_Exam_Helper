package com.example.cs4514_jlpt_exam_helper.data;

public class StudyPlanItem {
    private int study_plan_item_id;
    private String item_id_string;
    private int is_completed;

    public StudyPlanItem(int study_plan_item_id, String item_id_string, int is_completed) {
        this.study_plan_item_id = study_plan_item_id;
        this.item_id_string = item_id_string;
        this.is_completed = is_completed;
    }

    public int getStudy_plan_item_id() {
        return study_plan_item_id;
    }

    public void setStudy_plan_item_id(int study_plan_item_id) {
        this.study_plan_item_id = study_plan_item_id;
    }

    public String getItem_id_string() {
        return item_id_string;
    }

    public void setItem_id_string(String item_id_string) {
        this.item_id_string = item_id_string;
    }

    public int isIs_completed() {
        return is_completed;
    }

    public void setIs_completed(int is_completed) {
        this.is_completed = is_completed;
    }
}
