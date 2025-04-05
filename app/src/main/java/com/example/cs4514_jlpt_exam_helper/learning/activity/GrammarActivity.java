package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        showLoadingEffect();

        Intent intent = getIntent();
        subtopicName = intent.getStringExtra("SUBTOPIC_NAME");
        binding.textCategory.setText(subtopicName.trim());

        setUpEventListener();
        setupViewModelObserver();
        viewModel.getGrammarItemList(subtopicName);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnCompleted.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getGrammarList().observe(this, grammarList -> {
            if(grammarList != null && !grammarList.isEmpty()){
                binding.grammarRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.grammarRecyclerView.setAdapter(new GrammarAdapter(grammarList));
            }else{
                Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
            }

            hideLoadingEffect();
        });

        viewModel.getUpdateSuccess().observe(this, isSuccess ->{
            showToast("Your progress is updated.");
            finish();
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }else if(id == R.id.btn_completed){
            viewModel.updateUserProgress(this, subtopicName);
        }
    }

    public void goBackDashboardPage(){
        finish();
    }

    private void showLoadingEffect() {
        binding.overlayView.setVisibility(View.VISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoadingEffect() {
        binding.overlayView.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.GONE);
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
