package com.example.cs4514_jlpt_exam_helper.validator;

import com.example.cs4514_jlpt_exam_helper.api.data.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatValidator implements BaseValidator{
    private String formatType;
    private String regex;
    private String errorMsg;

    public FormatValidator(String formatType){
        this.formatType = formatType;
    }

    @Override
    public ValidationResult validate(String input){
        ValidationResult result = new ValidationResult();
        if(formatType.equals("email")){
            regex = Constant.emailRegex;
            errorMsg = "*Invalid email format";
        }else if(formatType.equals("password")){
            regex = Constant.passwordRegex;
            errorMsg = "*Password must contain at least one number, lowercase letter and uppercase letter.";
        }else if(formatType.equals("userName")){
            regex = Constant.userNameRegex;
            errorMsg = "*User name can only contain letter and number.";
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(!matcher.matches()){
            result.setValid(false);
            result.setErrorMsg(errorMsg);
        }else{
            result.setValid(true);
        }

        return result;
    }
}
