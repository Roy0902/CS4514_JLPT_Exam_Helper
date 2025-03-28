package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySubtopicBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySubtopicQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizResultFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

public class SubtopicQuizActivity extends AppCompatActivity implements View.OnClickListener {
    private QuizViewModel viewModel;
    private ActivitySubtopicQuizBinding binding;
    private String subtopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubtopicQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        Intent intent = getIntent();
        subtopicName = intent.getStringExtra("SUBTOPIC_NAME");
        binding.textQuiz.setText(subtopicName + " - Quiz");
        Toast.makeText(this, subtopicName, Toast.LENGTH_SHORT).show();

        setUpEventListener();
        setupViewModelObserver();

        viewModel.getLearningItem(subtopicName);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getIsQuestionReady().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.fragmentQuiz.getId(), new QuizFragment()).commit();

                }
            }
        });

        viewModel.getIsQuizCompleted().observe(this, isQuizCompleted -> {
            if(isQuizCompleted){
                if(viewModel.isPass()) {
                    viewModel.updateUserProgress(
                            subtopicName, SessionManager.getSessionToken(this));
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(binding.fragmentQuiz.getId(), new QuizResultFragment()).commit();
            }

        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }
    }

    public void goBackDashboardPage(){
        finish();
    }
}
