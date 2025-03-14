package com.example.cs4514_jlpt_exam_helper.data;

public class HiraganaItem {
    private int itemId;
    private String japaneseCharacter;
    private String romaji;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getJapaneseCharacter() {
        return japaneseCharacter;
    }

    public void setJapaneseCharacter(String japaneseCharacter) {
        this.japaneseCharacter = japaneseCharacter;
    }

    public String getRomaji() {
        return romaji;
    }

    public void setRomaji(String romaji) {
        this.romaji = romaji;
    }
}
