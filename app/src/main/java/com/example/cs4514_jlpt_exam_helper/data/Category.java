package com.example.cs4514_jlpt_exam_helper.data;

public class Category {

    private String category_name;
    private String level_name ;
    private int completed_subtopics;
    private int total_subtopics;
    private int imageResId;
    private int colorResId;

    public Category(String category_name, String level_name, int completed_subtopics,
                    int total_subtopics, int imageResId, int colorResId) {
        this.category_name = category_name;
        this.level_name = level_name;
        this.completed_subtopics = completed_subtopics;
        this.total_subtopics = total_subtopics;
        this.imageResId = imageResId;
        this.colorResId = colorResId;
    }

    public Category(String category_name, String level_name, int completed_subtopics,
                    int total_subtopics) {
        this.category_name = category_name;
        this.level_name = level_name;
        this.completed_subtopics = completed_subtopics;
        this.total_subtopics = total_subtopics;
    }

    public Category(String category_name){
        this.category_name = category_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(int colorResId) {
        this.colorResId = colorResId;
    }

    public String getLevel_name() {
        return level_name;
    }

    public void setLevel_name(String level_name) {
        this.level_name = level_name;
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
