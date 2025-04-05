package com.example.cs4514_jlpt_exam_helper.study_plan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityStudyPlanBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.StudyPlanAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.StudyPlanItemListFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.StudyPlanListFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudyPlanActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityStudyPlanBinding binding;
    private StudyPlanViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudyPlanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(StudyPlanViewModel.class);

        showLoadingEffect();

        setUpEventListener();
        setupViewModelObserver();


        viewModel.getStudyPlan(this);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnCompleted.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getStudyPlanReady().observe(this, isReady->{

            if(isReady) {
                loadFragment(new StudyPlanListFragment(), 1);
            }

            hideLoadingEffect();
        });


        viewModel.getSelectedStudyPlan().observe(this, selectedStudyPlan->{
            if(selectedStudyPlan != null){
                showLoadingEffect();
                String item_id_string = selectedStudyPlan.getItem_id_string();
                List<String> item_id_list = new ArrayList<String>(
                        Arrays.asList(item_id_string.split(",")));

                viewModel.getStudyPlanItemList(item_id_list);
            }
        });

        viewModel.getStudyPlanItemReady().observe(this, isReady ->{
            if(isReady){
                loadFragment(new StudyPlanItemListFragment(), 2);
            }

            hideLoadingEffect();
        });

        viewModel.getSelectedStudyPlan().observe(this, selectedStudyPlan->{
            binding.textStudyPlan.setText("Day " + viewModel.getSelectedPosition());
            binding.btnCompleted.setVisibility(View.VISIBLE);
            binding.btnCompleted.setEnabled(true);
        });

        viewModel.getUpdateProgressSuccess().observe(this, isSuccess ->{
            if(isSuccess){
                viewModel.getStudyPlan(this);
                loadFragment(new StudyPlanListFragment(), 1);
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            back();
        }else if(id == R.id.btn_completed){
            String sessionToken = SessionManager.getInstance().getSessionToken(this);
            viewModel.updateStudyPlanProgress(sessionToken);
        }
    }

    public void back(){
        int currentFragment = viewModel.getCurrentFragment();
        if(currentFragment == 2){
            binding.btnCompleted.setEnabled(false);
            binding.btnCompleted.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.study_plan_list_fragment, new StudyPlanListFragment()).commit();
            viewModel.setCurrentFragment(1);
        }else if(currentFragment == 1){
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

    public void loadFragment(Fragment fragment, int currentFragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.study_plan_list_fragment, fragment).commit();
        viewModel.setCurrentFragment(currentFragment);
    }
}