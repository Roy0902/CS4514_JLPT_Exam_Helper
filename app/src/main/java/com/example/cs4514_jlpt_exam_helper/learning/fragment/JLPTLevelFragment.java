package com.example.cs4514_jlpt_exam_helper.learning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentBeginnerBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentJlptLevelBinding;
import com.example.cs4514_jlpt_exam_helper.learning.activity.SubtopicActivity;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.CategoriesAdapter;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.CategoryViewModel;

import java.util.ArrayList;

public class JLPTLevelFragment extends Fragment implements View.OnClickListener, CategoriesAdapter.OnItemClickListener{
    private FragmentJlptLevelBinding binding;
    private CategoryViewModel viewModel;
    private String levelName;

    public static JLPTLevelFragment newInstance(String level_name) {
        JLPTLevelFragment fragment = new JLPTLevelFragment();
        Bundle args = new Bundle();
        args.putString("LEVEL_NAME", level_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJlptLevelBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            levelName = getArguments().getString("LEVEL_NAME");
        }


        Thread thread = new Thread(() -> {
            try {
                setUpEventListener();
            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();

        setupViewModelObserver();
        String session_token = SessionManager.getInstance().getSessionToken(requireActivity());
        viewModel.getUserProgress(levelName, requireActivity());
    }

    public void setUpEventListener(){

    }

    public void setupViewModelObserver(){
        viewModel.getIsReady().observe(getViewLifecycleOwner(), isReady -> {
            if (isReady) {
                ArrayList<Category> categories = viewModel.getCategories().getValue();
                binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                binding.categoryRecyclerView.setAdapter(new CategoriesAdapter(categories, requireActivity(), this));
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
    }

    @Override
    public void onItemClick(Category category) {
        Intent intent = new Intent(getActivity(), SubtopicActivity.class);
        intent.putExtra("CATEGORY_NAME", category.getCategory_name());
        intent.putExtra("LEVEL_NAME", levelName);
        startActivity(intent);
    }

}
