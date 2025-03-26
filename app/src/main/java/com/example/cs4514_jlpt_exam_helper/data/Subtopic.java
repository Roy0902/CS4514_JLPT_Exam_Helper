package com.example.cs4514_jlpt_exam_helper.data;

public class Subtopic {
    private int category_id;
    private int level_id;
    private String name;
    private int is_completed;

    public Subtopic(){}

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIs_completed() {
        return is_completed > 0;
    }

    public void setIs_completed(int is_completed) {
        this.is_completed = is_completed;
    }
}
