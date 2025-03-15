package com.example.cs4514_jlpt_exam_helper.learning.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentHomeBinding;
import com.example.cs4514_jlpt_exam_helper.dictionary.activity.DictionaryActivity;
import com.example.cs4514_jlpt_exam_helper.question.activity.QuestionActivity;

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

        Thread thread = new Thread(() -> {
            try {
                SharedPreferences pref = requireActivity().getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE);
                pref.edit().remove(Constant.key_selected_current_level).apply();
                String currentLevel = pref.getString(Constant.key_selected_current_level, Constant.level_beginner);
                binding.textLevel.setText(currentLevel);
                setUpFragment(currentLevel);
                setUpEventListener();
            }catch (Error e){
                Log.d("ERROR", "ERROR: " + e.getMessage());
            }
        });
        thread.start();
    }

    public void setUpFragment(String currentLevel){
        if(currentLevel.equals(Constant.level_beginner)){
            loadFragment(new BeginnerFragment());

        }

    }

    public void setUpEventListener(){
        binding.btnChangeLevel.setOnClickListener(this);
        binding.imageDictionary.setOnClickListener(this);
        binding.imageForum.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_change_level){
            goChangeLevelPage();
        }else if(id == R.id.image_dictionary){
            goDictionaryPage();
        }else if(id == R.id.image_forum){
            goQuestionPage();
        }
    }

    public void goChangeLevelPage(){
        Intent intent = new Intent(requireActivity(), UserEntryActivity.class);
        startActivity(intent);
    }

    public void goDictionaryPage(){
        Intent intent = new Intent(requireActivity(), DictionaryActivity.class);
        startActivity(intent);
    }

    public void goQuestionPage(){
        Intent intent = new Intent(requireActivity(), QuestionActivity.class);
        startActivity(intent);
    }

    private void loadFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_container, fragment)
                .commit();
    }

}
