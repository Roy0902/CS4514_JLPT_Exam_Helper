package com.example.cs4514_jlpt_exam_helper.sign_in.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.reset_password.activity.ResetPasswordActivity;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySignInBinding;
import com.example.cs4514_jlpt_exam_helper.sign_in.viewmodel.SignInViewModel;
import com.example.cs4514_jlpt_exam_helper.sign_up.activity.SignUpActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignInBinding binding;
    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
        binding.btnForgotPw.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_signIn){
            signIn();
        }else if(id == R.id.btn_signUp){
            goSignUpPage();
        }else if(id == R.id.btn_forgotPw){
            goResetPasswordPage();
        }else if(id == R.id.btn_back){
            backPreviousPage();
        }
    }

    public void signIn(){
        binding.btnSignIn.setEnabled(false);
        String email = binding.etEmail.getText().toString().trim();
        String pw = binding.etPassword.getText().toString().trim();
        viewModel.signIn(email, pw, this);
    }

    public void goSignUpPage(){
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goResetPasswordPage(){
        Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    public void goDashboardPage(){
        Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    public void backPreviousPage(){
        finish();
    }

    public void setupViewModelObserver() {
        viewModel.getEmptyEmail().observe(this, emptyEmail -> {
            if (emptyEmail) {
                binding.errorEmail.setVisibility(View.VISIBLE);
                binding.btnSignIn.setEnabled(true);
            } else {
                binding.errorEmail.setVisibility(View.INVISIBLE);
            }

        });

        viewModel.getEmptyPw().observe(this, emptyPw -> {
            if (emptyPw) {
                binding.errorPassword.setVisibility(View.VISIBLE);
                binding.btnSignIn.setEnabled(true);
            } else {
                binding.errorPassword.setVisibility(View.INVISIBLE);
            }
        });

        viewModel.getSignInFailed().observe(this, signInFailed -> {
            binding.errorSignInFail.setText(signInFailed.getErrorMsg());
            binding.errorSignInFail.setVisibility(View.VISIBLE);
            binding.btnSignIn.setEnabled(true);
        });

        viewModel.getSignInSuccess().observe(this, message -> {
            binding.errorSignInFail.setText("");
            binding.errorSignInFail.setVisibility(View.INVISIBLE);
            goDashboardPage();
        });
    }
}
