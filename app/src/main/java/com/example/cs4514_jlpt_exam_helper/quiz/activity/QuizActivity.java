package com.example.cs4514_jlpt_exam_helper.quiz.activity;

import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.QuizFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;
import com.example.cs4514_jlpt_exam_helper.quiz.SelectLevelFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


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
                .replace(R.id.fragment_quiz, new SelectLevelFragment()).commit();

    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getSelectedLevel().observe(this, selectedLevel ->{
            viewModel.getLearningItem();
        });

        viewModel.getIsQuestionReady().observe(this, isReady->{
            if(isReady){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_quiz, new QuizFragment()).commit();
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
}
