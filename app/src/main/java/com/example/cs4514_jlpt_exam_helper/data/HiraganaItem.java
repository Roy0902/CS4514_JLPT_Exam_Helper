package com.example.cs4514_jlpt_exam_helper.data;

public class HiraganaItem {
    private int itemId;
    private String japanese_character;
    private String pronunciation;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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
