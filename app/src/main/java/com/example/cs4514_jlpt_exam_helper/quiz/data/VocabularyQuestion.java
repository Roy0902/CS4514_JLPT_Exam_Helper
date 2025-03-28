package com.example.cs4514_jlpt_exam_helper.quiz.data;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;

import java.util.List;

public class VocabularyQuestion extends Question{
    private List<Vocabulary> optionList;
    private String question;
    private String correctAnswer;

    public VocabularyQuestion(String question, String correctAnswer, List<Vocabulary> optionList) {
        this.optionList = optionList;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public VocabularyQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return correctAnswer.equals(answer);
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public List<Vocabulary> getOptionList(){
        return optionList;
    }

    @Override
    public String getAnswer(){
        return correctAnswer;
    }
}
