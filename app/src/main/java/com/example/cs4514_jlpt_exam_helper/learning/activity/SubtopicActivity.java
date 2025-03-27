package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySubtopicBinding;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.SubtopicsAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.SubtopicViewModel;

public class SubtopicActivity extends AppCompatActivity implements View.OnClickListener, SubtopicsAdapter.OnItemClickListener{
    private ActivitySubtopicBinding binding;
    private SubtopicViewModel viewModel;
    private String categoryName;
    private String levelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubtopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SubtopicViewModel.class);

        SharedPreferences pref = getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        String sessionToken = pref.getString(Constant.key_session_token, Constant.error_not_found);

        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CATEGORY_NAME");
        levelName = intent.getStringExtra("LEVEL_NAME");
        binding.textCategory.setText(categoryName);

        setUpEventListener();
        setupViewModelObserver();
        viewModel.getSubtopicItemList(categoryName, levelName, sessionToken);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getSubtopics().observe(this, subtopics -> {
            if(subtopics != null && subtopics.size() > 0){
                binding.subtopicRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.subtopicRecyclerView.setAdapter(new SubtopicsAdapter(subtopics, this, this));
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }
    }

    public void goBackDashboardPage(){
        Intent intent = new Intent(SubtopicActivity.this, DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(Subtopic subtopic) {
        if(categoryName.equals(Constant.category_hiragana_katakana)){
            goItemPage(HiraganaActivity.class, subtopic.getName());
        }else if(categoryName.equals(Constant.category_grammar)){
            goItemPage(GrammarActivity.class, subtopic.getName());
        }else if(categoryName.equals(Constant.category_vocabulary)){
            goItemPage(VocabularyActivity.class, subtopic.getName());
        }

    }

    public void goItemPage(Class<?> activity, String subtopicName) {
        Intent intent = new Intent(SubtopicActivity.this, activity);
        intent.putExtra("SUBTOPIC_NAME", subtopicName);
        startActivity(intent);
    }
}