package com.example.cs4514_jlpt_exam_helper.dashboard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.DashboardViewModel;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityDashboardBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivitySelectLevelBinding;
import com.example.cs4514_jlpt_exam_helper.learning.fragment.HomeFragment;

public class SelectLevelActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySelectLevelBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectLevelBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        setContentView(binding.getRoot());

        showLoadingEffect();
        setUpEventListener();
        setupViewModelObserver();
        hideLoadingEffect();
    }


    public void setUpEventListener() {
        binding.optionBeginner.setOnClickListener(this);
        binding.optionN5.setOnClickListener(this);
        binding.optionN4.setOnClickListener(this);
        binding.optionN3.setOnClickListener(this);
        binding.optionN2.setOnClickListener(this);
        binding.optionN1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.option_beginner){
            changeLevel(Constant.level_beginner);
        }else if(id == R.id.option_N5){
            changeLevel(Constant.level_n5);
        }else if(id == R.id.option_N4){
            changeLevel(Constant.level_n4);
        }else if(id == R.id.option_N3){
            changeLevel(Constant.level_n3);
        }else if(id == R.id.option_N2){
            changeLevel(Constant.level_n2);
        }else if(id == R.id.option_N1){
            changeLevel(Constant.level_n1);
        }
    }

    public void changeLevel(String level_name){
        viewModel.saveSelectedLevel(this, level_name);

        Intent intent = new Intent(SelectLevelActivity.this, DashboardActivity.class);
        startActivity(intent);
    }


    public void setupViewModelObserver(){
        viewModel.getToastText().observe(
                this, text->{
                    hideLoadingEffect();
                    showToast(text);
                });
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
