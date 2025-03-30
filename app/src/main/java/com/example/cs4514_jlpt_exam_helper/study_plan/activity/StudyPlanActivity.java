package com.example.cs4514_jlpt_exam_helper.study_plan.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.DailyStudyPlan;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityStudyPlanBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySubtopicBinding;
import com.example.cs4514_jlpt_exam_helper.learning.activity.GrammarActivity;
import com.example.cs4514_jlpt_exam_helper.learning.activity.HiraganaActivity;
import com.example.cs4514_jlpt_exam_helper.learning.activity.VocabularyActivity;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.SubtopicsAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.SubtopicViewModel;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.StudyPlanAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class StudyPlanActivity extends AppCompatActivity implements View.OnClickListener, StudyPlanAdapter.OnItemClickListener{
    private ActivityStudyPlanBinding binding;
    private StudyPlanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(StudyPlanViewModel.class);

        showLoadingEffect();

        String sessionToken = SessionManager.getSessionToken(this);

        setUpEventListener();
        setupViewModelObserver();

        viewModel.getStudyPlan(sessionToken);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getStudyPlanList().observe(this, studyPlanList->{
            if(studyPlanList != null && studyPlanList.size() > 0){
                binding.studyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.studyPlanRecyclerView.setAdapter(new StudyPlanAdapter(studyPlanList, this, this));
            }
            hideLoadingEffect();
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

    @Override
    public void onItemClick(DailyStudyPlan dailyStudyPlan) {

    }

    public void goItemPage(Class<?> activity, String subtopicName) {
        Intent intent = new Intent(StudyPlanActivity.this, activity);
        intent.putExtra("SUBTOPIC_NAME", subtopicName);
        startActivity(intent);
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