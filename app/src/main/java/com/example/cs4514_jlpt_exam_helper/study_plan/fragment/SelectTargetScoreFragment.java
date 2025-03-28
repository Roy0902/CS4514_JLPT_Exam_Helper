package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectCurrentLevelBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectTargetScoreBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class SelectTargetScoreFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectTargetScoreBinding binding;
    private StudyPlanViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTargetScoreBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.optionPassExam.setOnClickListener(this);
        binding.optionFullPreparation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.option_pass_exam){
            viewModel.setIsJustPass(true);
        }else if(id == R.id.option_full_preparation){
            viewModel.setIsJustPass(false);
        }
    }

    public void setupViewModelObserver() {

    }
}
