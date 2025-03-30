package com.example.cs4514_jlpt_exam_helper.quiz.activity;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizResultFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.SelectLevelFragment;


public class QuizActivity extends AppCompatActivity implements View.OnClickListener {
    private QuizViewModel viewModel;
    private ActivityQuizBinding binding;
    private String currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        showLoadingEffect();
        setUpEventListener();
        setupViewModelObserver();

        hideLoadingEffect();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_quiz, new SelectLevelFragment()).commit();

    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getIsQuestionReady().observe(this, isReady->{
            if(isReady){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_quiz, new QuizFragment()).commit();
            }
        });

        viewModel.getIsQuizCompleted().observe(this, isCompleted->{
            if(isCompleted){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_quiz, new QuizResultFragment()).commit();
            }
        });

    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            finish();
        }
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
