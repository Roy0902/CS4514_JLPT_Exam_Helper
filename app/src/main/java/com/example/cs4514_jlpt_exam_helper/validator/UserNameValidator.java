package com.example.cs4514_jlpt_exam_helper.validator;

import java.util.ArrayList;
import java.util.List;

public class UserNameValidator implements BaseValidator {
    private List<BaseValidator> validatorList;

    public UserNameValidator(){
        validatorList = new ArrayList<BaseValidator>();
        validatorList.add(new NullValidator());
        validatorList.add(new FormatValidator("userName"));
    }

    @Override
    public ValidationResult validate(String input){
        for(BaseValidator validator: validatorList){
            ValidationResult result = validator.validate(input);
            if(!result.isValid()) {
                return result;
            }
        }

        return new ValidationResult(true);
    }
}
