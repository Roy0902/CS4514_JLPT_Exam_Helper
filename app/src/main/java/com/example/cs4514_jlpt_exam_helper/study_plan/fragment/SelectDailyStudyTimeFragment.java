package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectDailyStudyTimeBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

public class SelectDailyStudyTimeFragment extends Fragment implements View.OnClickListener{
    private FragmentSelectDailyStudyTimeBinding binding;
    private StudyPlanViewModel viewModel;
    String currentLevel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDailyStudyTimeBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        currentLevel = viewModel.getCurrentLevel().getValue();

        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.option5Minutes.setOnClickListener(this);
        binding.option10Minutes.setOnClickListener(this);
        binding.option15Minutes.setOnClickListener(this);
        binding.option30Minutes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.option_5_minutes){
            viewModel.setDailyStudyTime(5);
        }else if(id == R.id.option_10_minutes){
            viewModel.setDailyStudyTime(10);
        }else if(id == R.id.option_15_minutes){
            viewModel.setDailyStudyTime(15);
        }else if(id == R.id.option_30_minutes){
            viewModel.setDailyStudyTime(30);
        }
    }

    public void setupViewModelObserver() {

    }
}
