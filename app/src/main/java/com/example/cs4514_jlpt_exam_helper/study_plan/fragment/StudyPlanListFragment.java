package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.DashboardViewModel;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectExamDateBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentStudyPlanListBinding;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.ExamDateAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.StudyPlanAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

import java.util.List;

public class StudyPlanListFragment extends Fragment implements StudyPlanAdapter.OnItemClickListener{
    private FragmentStudyPlanListBinding binding;
    private StudyPlanViewModel viewModel;
    private StudyPlanAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudyPlanListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUpViewModelObserver();

        List<StudyPlanItem> studyPlanList = viewModel.getStudyPlanList().getValue();
        adapter = new StudyPlanAdapter(studyPlanList, requireActivity(), this);

        binding.studyPlanRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.studyPlanRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(StudyPlanItem studyPlanItem, int position) {
        viewModel.setSelectedPosition(position);
        viewModel.setSelectedStudyPlan(studyPlanItem);
    }

    public void setUpViewModelObserver(){
        viewModel.getSortStudyPlan().observe(getViewLifecycleOwner(), isSort ->{
            if(isSort){
                adapter.sort();
            }else{
                adapter.reset();
            }
        });
    }

}
