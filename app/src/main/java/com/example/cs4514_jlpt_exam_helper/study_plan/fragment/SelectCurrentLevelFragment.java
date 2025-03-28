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
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class SelectCurrentLevelFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectCurrentLevelBinding binding;
    private StudyPlanViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectCurrentLevelBinding.inflate(inflater, container, false);
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
        binding.optionN5.setOnClickListener(this);
        binding.optionN4.setOnClickListener(this);
        binding.optionN3.setOnClickListener(this);
        binding.optionN2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.option_N5){
            viewModel.setCurrentLevel(Constant.level_n5);
        }else if(id == R.id.option_N4){
            viewModel.setCurrentLevel(Constant.level_n4);
        }else if(id == R.id.option_N3){
            viewModel.setCurrentLevel(Constant.level_n3);
        }else if(id == R.id.option_N2){
            viewModel.setCurrentLevel(Constant.level_n2);
        }
    }

    public void setupViewModelObserver() {

    }
}
