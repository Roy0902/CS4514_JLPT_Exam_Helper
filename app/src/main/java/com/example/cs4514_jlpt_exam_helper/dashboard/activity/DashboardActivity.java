package com.example.cs4514_jlpt_exam_helper.dashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.DashboardViewModel;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityDashboardBinding;
import com.example.cs4514_jlpt_exam_helper.learning.fragment.HomeFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.StudyPlanFragment;

public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        setUpEventListener();
        setupViewModelObserver();

        Thread thread = new Thread(() -> {
            try {
                if (!SessionManager.isNoVerificationNeeded(DashboardActivity.this)) {
                    viewModel.verifySessionToken(DashboardActivity.this);
                }
            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();
    }


    public void setUpEventListener(){
        loadFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_plans) {
                selectedFragment = new StudyPlanFragment();
            } else if (itemId == R.id.nav_settings) {

            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }

            return true;
        });

    }


    public void setupViewModelObserver(){
        viewModel.getValidToken().observe(this, validToken -> {
            if(!validToken){
                goUserEntryPage();
            }else{
                SessionManager.setNoVerificationNeeded(this, true);
            }
        });
    }

    public void goUserEntryPage(){
        Intent intent = new Intent(DashboardActivity.this, UserEntryActivity.class);
        startActivity(intent);
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.dashboard_fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SessionManager.clearSession(this);
    }
}