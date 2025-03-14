package com.example.cs4514_jlpt_exam_helper.data;

public class Category {

    private String category_name;
    private int progress;
    private String level ;
    private int imageResId;
    private int colorResId;

    public Category(String category_name, int progress, String level, int imageResId, int colorResId) {
        this.category_name = category_name;
        this.progress = progress;
        this.level = level;

        this.imageResId = imageResId;
        this.colorResId = colorResId;
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


}
