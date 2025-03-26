package com.example.cs4514_jlpt_exam_helper.dashboard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectLevelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpEventListener();
    }


    public void setUpEventListener() {
        binding.optionBeginner.setOnClickListener(this);
        binding.optionN5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.option_beginner){
            changeLevel(Constant.level_beginner);
        }else if(id == R.id.option_N5){
            changeLevel(Constant.level_n5);
        }
    }

    public void changeLevel(String level_name){
        SharedPreferences pref = getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constant.key_selected_current_level, level_name);
        editor.apply();

        Intent intent = new Intent(SelectLevelActivity.this, DashboardActivity.class);
        startActivity(intent);

    }
}
