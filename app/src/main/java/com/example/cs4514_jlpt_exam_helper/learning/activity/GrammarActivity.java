package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityGrammarBinding;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.GrammarAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.JapaneseCharacterAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.GrammarViewModel;

public class GrammarActivity extends AppCompatActivity implements View.OnClickListener{
    private GrammarViewModel viewModel;
    private ActivityGrammarBinding binding;
    private String subtopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGrammarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(GrammarViewModel.class);

        Intent intent = getIntent();
        subtopicName = intent.getStringExtra("SUBTOPIC_NAME");
        binding.textCategory.setText(subtopicName);

        setUpEventListener();
        setupViewModelObserver();
        viewModel.getGrammarItemList(subtopicName);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getGrammarList().observe(this, grammarList -> {
            if(grammarList != null && !grammarList.isEmpty()){
                binding.grammarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.grammarRecyclerView.setAdapter(new GrammarAdapter(grammarList));
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
        finish();
    }
}
