package com.example.cs4514_jlpt_exam_helper.validator;

public class LengthValidator implements BaseValidator{
    private int minLength;
    private int maxLength;

    public LengthValidator(int minLength, int maxLength){
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(String input){
        ValidationResult result = new ValidationResult();
        if(input.length() < minLength){
            result.setValid(false);
            result.setErrorMsg("*Length must be at least " + minLength + " characters long.");
        }else if(input.length() > maxLength){
            result.setValid(false);
            result.setErrorMsg("*Length must not longer than " + maxLength);
        }else{
            result.setValid(true);
        }

        return result;
    }
}
