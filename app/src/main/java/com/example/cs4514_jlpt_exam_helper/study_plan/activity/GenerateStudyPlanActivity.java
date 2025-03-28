package com.example.cs4514_jlpt_exam_helper.study_plan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityGenerateStudyPlanBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.QuizResultFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.fragment.SelectLevelFragment;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.SelectCurrentLevelFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.SelectExamDateFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.SelectTargetLevelFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.SelectTargetScoreFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class GenerateStudyPlanActivity extends AppCompatActivity implements View.OnClickListener {
    private StudyPlanViewModel viewModel;
    private ActivityGenerateStudyPlanBinding binding;
    private String currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGenerateStudyPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(StudyPlanViewModel.class);

        setUpEventListener();
        setupViewModelObserver();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_generate_study_plan, new SelectCurrentLevelFragment()).commit();

    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getCurrentLevel().observe(this, currentLevel ->{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_generate_study_plan, new SelectTargetLevelFragment()).commit();
        });

        viewModel.getTargetLevel().observe(this, targetLevel ->{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_generate_study_plan, new SelectTargetScoreFragment()).commit();
        });

        viewModel.getIsJustPass().observe(this, isJustPass ->{
            viewModel.getJLPTExamDate();
        });

        viewModel.getExamDateListReady().observe(this, isReady ->{
            if(isReady){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_generate_study_plan, new SelectExamDateFragment()).commit();
            }else{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
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
