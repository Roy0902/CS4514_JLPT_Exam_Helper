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
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentGenerateStudyPlanEndBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectCurrentLevelBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

import java.io.FileReader;

public class ProcessEndFragment extends Fragment implements View.OnClickListener{
    private FragmentGenerateStudyPlanEndBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenerateStudyPlanEndBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setUpEventListener();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            requireActivity().finish();
        }
    }

}
