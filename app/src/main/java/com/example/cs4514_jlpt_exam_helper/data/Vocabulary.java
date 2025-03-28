package com.example.cs4514_jlpt_exam_helper.data;

public class Vocabulary extends BaseLearningItem {
    private String word;
    private String reading;
    private String meaning;

    public Vocabulary(String word, String reading, String meaning) {
        this.word = word;
        this.reading = reading;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getReading() {
        return reading;
    }

    public void setReading(String reading) {
        this.reading = reading;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String getQuestion(){
        return (word + "(" + reading + ")").trim();
    }

    @Override
    public String getAnswer(){
        return meaning;
    }
}
