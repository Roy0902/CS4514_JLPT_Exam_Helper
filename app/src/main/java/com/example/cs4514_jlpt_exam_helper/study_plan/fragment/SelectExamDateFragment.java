package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectExamDateBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectTargetLevelBinding;
import com.example.cs4514_jlpt_exam_helper.forum.adapter.QuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.ExamDateAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

import java.sql.Date;
import java.util.List;

public class SelectExamDateFragment extends Fragment implements ExamDateAdapter.OnItemClickListener{
    private FragmentSelectExamDateBinding binding;
    private StudyPlanViewModel viewModel;
    private ExamDateAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectExamDateBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        List<JLPTExamDate> examDateList = viewModel.getExamDateList();
        adapter = new ExamDateAdapter(examDateList, this);

        binding.optionRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.optionRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(JLPTExamDate exam_date) {
        viewModel.setSelectedExamDate(exam_date);
    }

}
