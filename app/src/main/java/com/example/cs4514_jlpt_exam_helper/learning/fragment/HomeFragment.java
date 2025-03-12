package com.example.cs4514_jlpt_exam_helper.learning.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = requireActivity().getSharedPreferences("Session", MODE_PRIVATE);
        String currentLevel = pref.getString(Constant.key_selected_current_level, Constant.level_beginner);

        setUpEventListener();
        loadLevelFragment(currentLevel);
    }

    public void setUpEventListener(){
        binding.btnChangeLevel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_change_level){
            goChangeLevelPage();
        }
    }

    public void goChangeLevelPage(){
        Intent intent = new Intent(requireActivity(), UserEntryActivity.class);
        startActivity(intent);
    }

    private void loadLevelFragment(String currentLevel) {
        binding.textLevel.setText(currentLevel);
        Fragment fragment = null;
        if(currentLevel.equals(Constant.level_beginner)){
            fragment = new BeginnerLevelFragment();
        }

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.levelContentContainer.getId(), fragment)
                .commit();
    }

}
