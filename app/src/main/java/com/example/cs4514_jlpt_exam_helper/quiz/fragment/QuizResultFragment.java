package com.example.cs4514_jlpt_exam_helper.quiz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuizResultBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

public class QuizResultFragment extends Fragment implements View.OnClickListener{
    private QuizViewModel viewModel;
    private FragmentQuizResultBinding binding;
    private int score;
    private int totalQuestions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizResultBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        score = viewModel.getScore();
        totalQuestions = viewModel.getTotalScore();

        if(viewModel.isPass()){
            binding.textResultDescription.setText("You have passed the quiz!");
            binding.textResultDescription.setTextColor(getResources().getColor(R.color.green_1, null));
        }else{
            binding.textResultDescription.setText("You failed the quiz.");
            binding.textResultDescription.setTextColor(getResources().getColor(R.color.red_1, null));
        }

        binding.textResult.setText(score + " / " + totalQuestions);
        binding.btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            Intent intent = new Intent(requireActivity(), DashboardActivity.class);
            startActivity(intent);
        }
    }
}
