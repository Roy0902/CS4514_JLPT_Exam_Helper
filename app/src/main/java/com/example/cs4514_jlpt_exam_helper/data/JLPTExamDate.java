package com.example.cs4514_jlpt_exam_helper.data;

import java.sql.Date;

public class JLPTExamDate {
    private Date exam_date;

    public JLPTExamDate(Date exam_date) {
        this.exam_date = exam_date;
    }

    public Date getExam_date() {
        return exam_date;
    }

    public void setExam_date(Date exam_date) {
        this.exam_date = exam_date;
    }
}
