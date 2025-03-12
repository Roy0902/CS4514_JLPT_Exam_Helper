package com.example.cs4514_jlpt_exam_helper.onboarding.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityOnboardingBinding;
import com.example.cs4514_jlpt_exam_helper.onboarding.adapter.OnboardingViewPagerAdapter;

public class OnboardingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityOnboardingBinding binding;
    private int[] imageResIDs;
    private int[] titleIDs;
    private int[] descriptionIDs;

    public OnboardingActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityOnboardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeIDs();
        setUpViewAdapter();
        setUpPageIndicator();
        setUpEventListener();
    }

    public void setUpViewAdapter(){
        OnboardingViewPagerAdapter adapter =  new OnboardingViewPagerAdapter(this, imageResIDs,
                titleIDs, descriptionIDs);
        binding.viewPager.setAdapter(adapter);
    }

    public void setUpPageIndicator(){
        binding.pageIndicator.attachTo(binding.viewPager);
    }

    public void setUpEventListener(){
        binding.btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_start) {
            goHomePage();
        }
    }

    public void goHomePage(){
        SharedPreferences pref = getSharedPreferences("Session", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constant.key_is_first_time_used, false);
        editor.apply();
        Intent intent = new Intent(OnboardingActivity.this, UserEntryActivity.class);
        startActivity(intent);
    }

    public void initializeIDs(){
        imageResIDs = new int[]{R.drawable.image_onboarding_1, R.drawable.image_onboarding_2, R.drawable.image_onboarding_3};
        titleIDs = new int[]{R.string.onboarding_title_1, R.string.onboarding_title_2, R.string.onboarding_title_3};
        descriptionIDs = new int[]{R.string.onboarding_desc_1, R.string.onboarding_desc_2, R.string.onboarding_desc_3};
    }

}
