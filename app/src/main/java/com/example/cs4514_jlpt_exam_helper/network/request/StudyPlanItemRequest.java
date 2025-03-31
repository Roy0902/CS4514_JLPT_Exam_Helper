package com.example.cs4514_jlpt_exam_helper.network.request;

import java.util.List;

public class StudyPlanItemRequest {
    private List<String> item_id_list;

    public StudyPlanItemRequest(List<String> itemIDList) {
        this.item_id_list = itemIDList;
    }

    public List<String> getItem_id_list() {
        return item_id_list;
    }

    public void setItem_id_list(List<String> item_id_list) {
        this.item_id_list = item_id_list;
    }
}
