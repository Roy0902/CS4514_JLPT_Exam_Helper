package com.example.cs4514_jlpt_exam_helper.dashboard.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.DashboardViewModel;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityDashboardBinding;
import com.example.cs4514_jlpt_exam_helper.learning.fragment.HomeFragment;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        SharedPreferences pref = getSharedPreferences("Session", MODE_PRIVATE);
        String sessionToken = pref.getString(Constant.key_session_token, Constant.error_not_found);

        setUpEventListener();
        setupViewModelObserver();
        viewModel.verifySessionToken(sessionToken, this);
        NavController navController = Navigation.findNavController(this, R.id.fragment_container);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }

    public void setUpEventListener(){

    }


    public void setupViewModelObserver(){
        viewModel.getValidToken().observe(this, validToken -> {
            if(!validToken){
                goUserEntryPage();
            }
        });
    }

    public void goUserEntryPage(){
        Intent intent = new Intent(DashboardActivity.this, UserEntryActivity.class);
        startActivity(intent);
    }
}