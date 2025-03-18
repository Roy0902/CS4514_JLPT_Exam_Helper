package com.example.cs4514_jlpt_exam_helper.network.response;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;

import java.util.List;

public class LearningItemResponse {
    private List<JapaneseCharacter> characterList;
    private List<Grammar> grammarList;

    public LearningItemResponse(List<JapaneseCharacter> characterList, List<Grammar> grammarList) {
        this.characterList = characterList;
        this.grammarList = grammarList;
    }

    public List<JapaneseCharacter> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<JapaneseCharacter> characterList) {
        this.characterList = characterList;
    }

    public List<Grammar> getGrammarList() {
        return grammarList;
    }

    public void setGrammarList(List<Grammar> grammarList) {
        this.grammarList = grammarList;
    }
}
