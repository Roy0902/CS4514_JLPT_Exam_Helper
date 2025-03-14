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

import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentBeginnerBinding;
import com.example.cs4514_jlpt_exam_helper.learning.activity.SubtopicActivity;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.CategoriesAdapter;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.CategoryViewModel;

public class BeginnerFragment extends Fragment implements View.OnClickListener, CategoriesAdapter.OnItemClickListener{
    private FragmentBeginnerBinding binding;
    private CategoryViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBeginnerBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        Thread thread = new Thread(() -> {
            try {
                setUpEventListener();
            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();

        setupViewModelObserver();
        viewModel.setUpBeginnerCategory();
    }

    public void setUpEventListener(){

    }

    public void setupViewModelObserver(){
        viewModel.getCategories().observe(requireActivity(), categories -> {
            binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.categoryRecyclerView.setAdapter(new CategoriesAdapter(categories, requireActivity(), this));
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
        intent.putExtra("LEVEL_NAME", Constant.level_beginner);
        startActivity(intent);
    }

}
