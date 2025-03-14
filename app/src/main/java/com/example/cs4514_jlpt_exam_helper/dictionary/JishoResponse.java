package com.example.cs4514_jlpt_exam_helper.dictionary;

import java.util.List;

public class JishoResponse {
    private String word;
    private String reading;
    private String english_definition;
    private String part_of_speech;
    private String jlpt;

    public JishoResponse(String word, String reading, String english_definition,
                         String partsOfSpeech, String jlpt) {
        this.word = word;
        this.reading = reading;
        this.english_definition = english_definition;
        this.part_of_speech = partsOfSpeech;
        this.jlpt = jlpt;
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

    public String getEnglish_definition() {
        return english_definition;
    }

    public void setEnglish_definition(String english_definition) {
        this.english_definition = english_definition;
    }

    public String getPart_of_speech() {
        return part_of_speech;
    }

    public void setPart_of_speech(String part_of_speech) {
        this.part_of_speech = part_of_speech;
    }

    public String getJlpt() {
        return jlpt;
    }

    public void setJlpt(String jlpt) {
        this.jlpt = jlpt;
    }


}
