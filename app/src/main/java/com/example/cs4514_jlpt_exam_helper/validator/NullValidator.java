package com.example.cs4514_jlpt_exam_helper.validator;

public class NullValidator implements BaseValidator{

    public NullValidator(){

    }

    @Override
    public ValidationResult validate(String input){
        ValidationResult result = new ValidationResult();
        if(input == null || input.isEmpty()){
            result.setValid(false);
            result.setErrorMsg("*Required");
        }else{
            result.setValid(true);
        }

        return result;
    }
}
