package com.example.cs4514_jlpt_exam_helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.onboarding.activity.OnboardingActivity;
import com.example.cs4514_jlpt_exam_helper.data.Constant;

public class SplashActivity extends AppCompatActivity {

    public SplashActivity(){}

    @Override
    protected void onCreate(Bundle savedBundleInstance){
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(() -> {
            try {
                SharedPreferences pref = getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
                boolean isFirstTimeUsed = pref.getBoolean(Constant.key_is_first_time_used, true);
                String sessionToken = pref.getString(Constant.key_session_token, Constant.error_not_found);

                if(isFirstTimeUsed){
                    goOnboardingPage();
                }else if(sessionToken.equals(Constant.error_not_found)){
                    goUserEntryPage();
                }else{
                    goDashboardPage();
                }

            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();

    }

    private void goOnboardingPage(){
        Intent intent = new Intent(SplashActivity.this, OnboardingActivity.class);
        startActivity(intent);
    }

    private void goUserEntryPage(){
        Intent intent = new Intent(SplashActivity.this, UserEntryActivity.class);
        startActivity(intent);
    }

    private void goDashboardPage(){
        Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(intent);
    }
}
