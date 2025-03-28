package com.example.cs4514_jlpt_exam_helper.quiz.data;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;

import java.util.List;

public class GrammarQuestion extends Question{
    private List<Grammar> optionList;
    private String question;
    private String correctAnswer;

    public GrammarQuestion(String question, String correctAnswer, List<Grammar> optionList) {
        this.optionList = optionList;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public GrammarQuestion(String question, String correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String getAnswer(){
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
    public List<Grammar> getOptionList(){
        return optionList;
    }


}
