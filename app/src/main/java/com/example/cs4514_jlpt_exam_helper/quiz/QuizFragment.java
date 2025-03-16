package com.example.cs4514_jlpt_exam_helper.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectLevelBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

public class QuizFragment extends Fragment implements View.OnClickListener{
    private QuizViewModel viewModel;
    private FragmentSelectLevelBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectLevelBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel.getLearningItemList();
        setUpEventListener();
    }

    public void setUpEventListener(){

    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.option_beginner){
            viewModel.setSelectedLevel(Constant.level_beginner);
        }else if(id == R.id.option_N5){
            viewModel.setSelectedLevel(Constant.level_n5);
        }
    }
}
