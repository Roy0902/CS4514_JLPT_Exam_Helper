package com.example.cs4514_jlpt_exam_helper;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cs4514_jlpt_exam_helper.data.Constant;

public class SessionManager {

    private static SessionManager sessionManager;
    private boolean isVerified;

    public SessionManager(){
        isVerified = false;
    }

    public static SessionManager getInstance(){
        if(sessionManager == null){
            sessionManager = new SessionManager();
        }
        return sessionManager;
    }

    public void setNoVerificationNeeded(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isNoVerificationNeeded() {
        return isVerified;
    }

    public void clearSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(Constant.key_session_token);
        editor.remove(Constant.key_is_verified);
        editor.apply();
    }

    public void updateSessionToken(Context context, String sessionToken){
        SharedPreferences prefs = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constant.key_session_token, sessionToken);
        editor.apply();
    }

    public String getSessionToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        return pref.getString(Constant.key_session_token, Constant.error_not_found);
    }
}