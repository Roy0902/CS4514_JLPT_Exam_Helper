package com.example.cs4514_jlpt_exam_helper.data;

public class JapaneseCharacter {

    private String japanese_character;
    private String pronunciation;

    public JapaneseCharacter(String character, String pronunciation){
        this.japanese_character = character;
        this.pronunciation =  pronunciation;
    }

    public String getJapanese_character() {
        return japanese_character;
    }

    public void setJapanese_character(String japanese_character) {
        this.japanese_character = japanese_character;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }
}
