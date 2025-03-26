package com.example.cs4514_jlpt_exam_helper.network.response;

public class CategoryProgressResponse {
    private String category_name;
    private int completed_subtopics;
    private int total_subtopics;

    public CategoryProgressResponse(String category_name, int completed_subtopics, int total_subtopics) {
        this.category_name = category_name;
        this.completed_subtopics = completed_subtopics;
        this.total_subtopics = total_subtopics;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getCompleted_subtopics() {
        return completed_subtopics;
    }

    public void setCompleted_subtopics(int completed_subtopics) {
        this.completed_subtopics = completed_subtopics;
    }

    public int getTotal_subtopics() {
        return total_subtopics;
    }

    public void setTotal_subtopics(int total_subtopics) {
        this.total_subtopics = total_subtopics;
    }
}
