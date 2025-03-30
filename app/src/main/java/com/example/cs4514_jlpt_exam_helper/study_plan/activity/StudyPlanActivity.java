package com.example.cs4514_jlpt_exam_helper.study_plan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityStudyPlanBinding;
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
        viewModel.getStudyPlanReady().observe(this, isReady->{

            if(isReady){
                binding.studyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.studyPlanRecyclerView.setAdapter(new StudyPlanAdapter(viewModel.getStudyPlanList().getValue(),
                        this, this));
            }else{
                Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
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
    public void onItemClick(StudyPlanItem dailyStudyPlan) {

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