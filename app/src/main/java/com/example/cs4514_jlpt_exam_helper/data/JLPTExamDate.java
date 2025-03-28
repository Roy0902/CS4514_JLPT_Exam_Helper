package com.example.cs4514_jlpt_exam_helper.data;

import java.sql.Date;

public class JLPTExamDate {
    private String exam_date;

    public JLPTExamDate(String exam_date) {
        this.exam_date = exam_date;
    }

    public String getExam_date() {
        return exam_date;
    }

    public void setExam_date(String exam_date) {
        this.exam_date = exam_date;
    }
}
