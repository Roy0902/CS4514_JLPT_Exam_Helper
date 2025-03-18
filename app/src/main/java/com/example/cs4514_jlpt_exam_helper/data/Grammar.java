package com.example.cs4514_jlpt_exam_helper.data;

public class Grammar extends BaseLearningItem{
    private String rule;
    private String explanation;

    public Grammar(String grammar, String explanation){
        this.rule = grammar;
        this.explanation = explanation;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String getQuestion(){
        return rule;
    }

    @Override
    public String getAnswer(){
        return explanation;
    }
}
