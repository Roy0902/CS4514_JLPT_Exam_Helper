package com.example.cs4514_jlpt_exam_helper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cs4514_jlpt_exam_helper.data.Constant;

public class SessionManager {

    public SessionManager(){

    }

    public static void setNoVerificationNeeded(Context context, boolean isVerified) {
        SharedPreferences prefs = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(Constant.key_is_verified, isVerified);
        editor.apply();
    }

    public static boolean isNoVerificationNeeded(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        return prefs.getBoolean(Constant.key_is_verified, false);
    }

    public static void clearSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constant.key_is_verified);
        editor.apply();
    }

    public static String getSessionToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        String sessionToken = pref.getString(Constant.key_session_token, Constant.error_not_found);
        return sessionToken;
    }
}