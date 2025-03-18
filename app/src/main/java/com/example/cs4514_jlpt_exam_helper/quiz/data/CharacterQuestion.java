package com.example.cs4514_jlpt_exam_helper.quiz.data;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;

import java.util.List;

public class CharacterQuestion extends Question{
    private List<JapaneseCharacter> optionList;
    private String question;
    private String correctAnswer;

    public CharacterQuestion(String question, String correctAnswer, List<JapaneseCharacter> optionList) {
        this.optionList = optionList;
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
    public List<JapaneseCharacter> getOptionList(){
        return optionList;
    }




}
