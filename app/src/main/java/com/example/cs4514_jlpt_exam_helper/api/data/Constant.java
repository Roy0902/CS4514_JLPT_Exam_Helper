package com.example.cs4514_jlpt_exam_helper.api.data;

public class Constant {
    public final static String baseURL = "http://192.168.50.215:8080/";
    public final static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public final static String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,30}$";
    public final static String userNameRegex = "^[a-zA-Z0-9]+$";

    public final static String key_is_first_time_used = "IS_FIRST_TIME_USED";
    public final static String key_session_token = "SESSION_TOKEN";
    public final static String key_token = "TOKEN";
    public final static String key_selected_current_level = "SELECTED_CURRENT_LEVEL";

    public final static String error_not_found = "*NOT FOUND";
    public final static String confirmPasswordErrorMsg = "*Passwords do not match.";

    public final static String level_beginner = "Beginner";
    public final static String level_n5 = "N5";
    public final static String level_n4 = "N4";
}
