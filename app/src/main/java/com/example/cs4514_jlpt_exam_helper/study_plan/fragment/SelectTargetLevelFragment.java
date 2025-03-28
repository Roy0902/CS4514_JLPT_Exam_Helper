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
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectTargetLevelBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class SelectTargetLevelFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectTargetLevelBinding binding;
    private StudyPlanViewModel viewModel;
    String currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTargetLevelBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        currentLevel = viewModel.getCurrentLevel().getValue();

        setOption();
        setUpEventListener();
        setupViewModelObserver();
    }

    public void setOption(){
        if(currentLevel.equals(Constant.level_n4)){
            binding.optionN5.setVisibility(View.GONE);
        }else if(currentLevel.equals(Constant.level_n3)){
            binding.optionN5.setVisibility(View.GONE);
            binding.optionN4.setVisibility(View.GONE);
        }else if(currentLevel.equals(Constant.level_n2)) {
            binding.optionN5.setVisibility(View.GONE);
            binding.optionN4.setVisibility(View.GONE);
            binding.optionN3.setVisibility(View.GONE);
        }
    }

    public void setUpEventListener(){
        binding.optionN5.setOnClickListener(this);
        binding.optionN4.setOnClickListener(this);
        binding.optionN3.setOnClickListener(this);
        binding.optionN2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.option_N5){
            viewModel.setTargetLevel(Constant.level_n5);
        }else if(id == R.id.option_N4){
            viewModel.setTargetLevel(Constant.level_n4);
        }else if(id == R.id.option_N3){
            viewModel.setTargetLevel(Constant.level_n3);
        }else if(id == R.id.option_N2){
            viewModel.setTargetLevel(Constant.level_n2);
        }
    }

    public void setupViewModelObserver() {

    }
}
