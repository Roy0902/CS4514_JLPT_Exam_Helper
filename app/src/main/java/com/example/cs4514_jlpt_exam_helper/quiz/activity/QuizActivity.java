package com.example.cs4514_jlpt_exam_helper.quiz.activity;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.QuizFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;
import com.example.cs4514_jlpt_exam_helper.quiz.SelectLevelFragment;


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

        setUpEventListener();
        setupViewModelObserver();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_quiz, new SelectLevelFragment());

    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getSelectedLevel().observe(this, selectedLevel ->{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_quiz, new QuizFragment());
        });

    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            finish();
        }

    }
}
