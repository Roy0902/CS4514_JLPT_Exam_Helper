package com.example.cs4514_jlpt_exam_helper.sign_up.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySignUpBinding;
import com.example.cs4514_jlpt_exam_helper.sign_up.viewmodel.SignUpViewModel;
import com.example.cs4514_jlpt_exam_helper.sign_up.adapter.SignUpViewPagerAdapter;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private SignUpViewModel viewModel;
    private ActivitySignUpBinding binding;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);
        progressBar = binding.progressBar;
        showLoadingEffect();

        setUpEventListener();
        setUpViewAdapter();
        setupViewModelObserver();

        hideLoadingEffect();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.viewPager.setUserInputEnabled(false);
    }

    public void setUpViewAdapter(){
        SignUpViewPagerAdapter adapter = new SignUpViewPagerAdapter(this);
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
            hideLoadingEffect();
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
            hideLoadingEffect();
            binding.btnBack.setVisibility(View.GONE);
            setCurrentFragment(1);
        });

        viewModel.getVerifyEmailSuccess().observe(this, sendOtpSuccess ->{
            hideLoadingEffect();
            setCurrentFragment(2);
        });

        viewModel.getSignUpSuccess().observe(this, signUpSuccess ->{
            hideLoadingEffect();
            setCurrentFragment(3);
        });

        viewModel.getLoading().observe(this, loading->{
            if(loading){
                showLoadingEffect();
            }else{
                hideLoadingEffect();
            }
        });
    }

    public void backPreviousPage(){
        finish();
    }

    public void setCurrentFragment(int position) {
        binding.viewPager.setCurrentItem(position, true);
    }

    private void showLoadingEffect() {
        binding.overlayView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingEffect() {
        binding.overlayView.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
    }
}
