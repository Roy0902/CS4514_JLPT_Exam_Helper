package com.example.cs4514_jlpt_exam_helper.validator;

import java.util.ArrayList;
import java.util.List;

public class PasswordValidator implements BaseValidator{
    private List<BaseValidator> validatorList;

    public PasswordValidator(){
        validatorList = new ArrayList<BaseValidator>();
        validatorList.add(new NullValidator());
        validatorList.add(new LengthValidator(8,30));
        validatorList.add(new FormatValidator("password"));
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
