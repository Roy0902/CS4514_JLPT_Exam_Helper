package com.example.cs4514_jlpt_exam_helper.api.data;

public class Category {

    private String category_name;
    private int progress;
    private String level ;

    public Category(String title, int imageResId, int progress, String level) {
        this.category_name = title;
        this.progress = progress;
        this.level = level;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
