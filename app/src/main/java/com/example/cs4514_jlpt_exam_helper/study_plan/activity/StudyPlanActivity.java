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
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityStudyPlanBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySubtopicBinding;
import com.example.cs4514_jlpt_exam_helper.learning.activity.GrammarActivity;
import com.example.cs4514_jlpt_exam_helper.learning.activity.HiraganaActivity;
import com.example.cs4514_jlpt_exam_helper.learning.activity.VocabularyActivity;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.SubtopicsAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.SubtopicViewModel;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class StudyPlanActivity extends AppCompatActivity implements View.OnClickListener, SubtopicsAdapter.OnItemClickListener{
    private ActivityStudyPlanBinding binding;
    private StudyPlanViewModel viewModel;
    private String categoryName;
    private String levelName;

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
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getStudyPlan().observe(this, subtopics -> {
            if(subtopics != null && subtopics.size() > 0){
                binding.subtopicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.subtopicRecyclerView.setAdapter(new SubtopicsAdapter(subtopics, this, this));
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
        Intent intent = new Intent(StudyPlanActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Subtopic subtopic) {
        if(categoryName.equals(Constant.category_hiragana_katakana)){
            goItemPage(HiraganaActivity.class, subtopic.getName());
        }else if(categoryName.equals(Constant.category_grammar)){
            goItemPage(GrammarActivity.class, subtopic.getName());
        }else if(categoryName.equals(Constant.category_vocabulary)){
            goItemPage(VocabularyActivity.class, subtopic.getName());
        }

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