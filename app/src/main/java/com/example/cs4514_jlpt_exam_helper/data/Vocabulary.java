package com.example.cs4514_jlpt_exam_helper.data;

public class Vocabulary {
    private String kanji;
    private String hiragana;
    private String meaning;

    public Vocabulary(String kanji, String hiragana, String meaning) {
        this.kanji = kanji;
        this.hiragana = hiragana;
        this.meaning = meaning;
    }

    public String getKanji() {
        return kanji;
    }

    public void setKanji(String kanji) {
        this.kanji = kanji;
    }

    public String getHiragana() {
        return hiragana;
    }

    public void setHiragana(String hiragana) {
        this.hiragana = hiragana;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
