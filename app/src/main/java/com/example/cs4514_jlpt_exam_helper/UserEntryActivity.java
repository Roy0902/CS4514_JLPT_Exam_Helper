package com.example.cs4514_jlpt_exam_helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.sign_in.activity.SignInActivity;
import com.example.cs4514_jlpt_exam_helper.sign_up.activity.SignUpActivity;

public class UserEntryActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_entry);

        Thread thread = new Thread(() -> {
            try {
                SharedPreferences pref = getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
                String token = pref.getString(Constant.key_session_token, Constant.error_not_found);
                if(!token.equals(Constant.error_not_found)){
                    goDashboard();
                }

            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();


        setUpEventListener();
    }

    public void setUpEventListener(){
        findViewById(R.id.btn_signIn).setOnClickListener(this);
        findViewById(R.id.btn_signUp).setOnClickListener(this);
        findViewById(R.id.btn_guest).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_signIn)
            goSignInPage();
        else if(id == R.id.btn_signUp)
            goSignUpPage();
        else if(id == R.id.btn_guest)
            goDashboard();

    }

    public void goSignInPage(){
        Intent intent = new Intent(UserEntryActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void goSignUpPage(){
        Intent intent = new Intent(UserEntryActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goDashboard(){
        Intent intent = new Intent(UserEntryActivity.this, DashboardActivity.class);
        startActivity(intent);
    }
}
