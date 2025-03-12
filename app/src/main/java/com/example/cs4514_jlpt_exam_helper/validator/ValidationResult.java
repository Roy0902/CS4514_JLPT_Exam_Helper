package com.example.cs4514_jlpt_exam_helper.validator;

public class ValidationResult {

    private boolean isValid;
    private String errorMsg;

    public ValidationResult(){

    }

    public ValidationResult(boolean isValid){
        this.isValid = isValid;
    }

    public ValidationResult(boolean isValid, String errorMsg){
        this.isValid = isValid;
        this.errorMsg = errorMsg;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
