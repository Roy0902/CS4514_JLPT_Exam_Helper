package com.example.cs4514_jlpt_exam_helper.quiz.data;

import java.util.List;

public abstract class Question {

    public abstract boolean checkAnswer(String answer);

    public abstract String getQuestion();

    public abstract List<?> getOptionList();
}
