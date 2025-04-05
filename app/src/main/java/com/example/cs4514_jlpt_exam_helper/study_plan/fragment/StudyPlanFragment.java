package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentStudyPlanBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.activity.GenerateStudyPlanActivity;
import com.example.cs4514_jlpt_exam_helper.study_plan.activity.StudyPlanActivity;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class StudyPlanFragment extends Fragment implements View.OnClickListener{
    private FragmentStudyPlanBinding binding;
    private StudyPlanViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudyPlanBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        String sessionToken = SessionManager.getInstance().getSessionToken(requireActivity());

        setUpEventListener();
        setupViewModelObserver();

        viewModel.getStudyPlanSummary(requireActivity());
    }

    public void setUpEventListener(){
        binding.btnGeneratePlan.setOnClickListener(this);
        binding.studyPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_generate_plan){
            goGenerateStudyPlanPage();
        }else if(id == R.id.study_plan){
            goStudyPlanPage();
        }
    }

    public void goGenerateStudyPlanPage(){
        Intent intent = new Intent(requireActivity(), GenerateStudyPlanActivity.class);
        startActivity(intent);
    }

    public void goStudyPlanPage(){
        Intent intent = new Intent(requireActivity(), StudyPlanActivity.class);
        startActivity(intent);
    }

    public void setupViewModelObserver() {
        viewModel.getIsPlanExisted().observe(getViewLifecycleOwner(), isPlanExisted -> {
            if (isPlanExisted) {
                binding.studyPlan.setVisibility(View.VISIBLE);
                binding.textNoStudyPlan.setVisibility(View.GONE);
                binding.progressStudyPlan.setMax(viewModel.getTotalPlan());
                binding.progressStudyPlan.setProgress(viewModel.getCompletedPlan());

                if(viewModel.isBadStudyPlan()){
                    binding.textWarningBadStudyPlan.setVisibility(View.VISIBLE);
                }else {
                    binding.textWarningBadStudyPlan.setVisibility(View.GONE);
                }
            }else{
                binding.studyPlan.setVisibility(View.GONE);
                binding.textWarningBadStudyPlan.setVisibility(View.GONE);
                binding.textNoStudyPlan.setVisibility(View.VISIBLE);
            }
        });

    }

}
