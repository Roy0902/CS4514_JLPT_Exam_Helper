package com.example.cs4514_jlpt_exam_helper.reset_password.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityResetPasswordBinding;
import com.example.cs4514_jlpt_exam_helper.reset_password.viewmodel.ResetPasswordViewModel;
import com.example.cs4514_jlpt_exam_helper.reset_password.adapter.ResetPasswordViewPagerAdapter;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityResetPasswordBinding binding;
    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);

        setUpEventListener();
        setUpViewAdapter();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.viewPager.setUserInputEnabled(false);
    }

    public void setUpViewAdapter(){
        ResetPasswordViewPagerAdapter adapter = new ResetPasswordViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            backPreviousPage();
        }
    }

    public void setupViewModelObserver(){
        viewModel.getProgressIndex().observe(this, progressIndex -> {
            if(progressIndex == 1){
                binding.linearProgressBar.setProgress(33);
                binding.textStepCounter.setText(R.string.step_counter_2_3);
            }else if(progressIndex == 2){
                binding.linearProgressBar.setProgress(66);
                binding.textStepCounter.setText(R.string.step_counter_3_3);
            }else if(progressIndex == 3){
                binding.linearProgressBar.setProgress(100);
                binding.textStepCounter.setVisibility(View.GONE);
            }
        });

        viewModel.getSendOtpSuccess().observe(this, sendOtpSuccess ->{
            binding.btnBack.setVisibility(View.GONE);
            setCurrentFragment(1);
        });

        viewModel.getVerifyEmailSuccess().observe(this, sendOtpSuccess ->{
            setCurrentFragment(2);
        });

        viewModel.getResetPasswordSuccess().observe(this, resetPasswordSuccess -> {
            setCurrentFragment(3);
        });
    }

    public void backPreviousPage(){
        finish();
    }

    public void setCurrentFragment(int position) {
        binding.viewPager.setCurrentItem(position, true);
    }

}
