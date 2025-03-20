package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityHiraganaBinding;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.CategoriesAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.JapaneseCharacterAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.SubtopicsAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.JapaneseCharacterViewModel;

public class HiraganaActivity extends AppCompatActivity implements View.OnClickListener, JapaneseCharacterAdapter.OnItemClickListener{
    private JapaneseCharacterViewModel viewModel;
    private ActivityHiraganaBinding binding;
    private String subtopicName;
    private MediaPlayer player;
    private GoogleTTSManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHiraganaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(JapaneseCharacterViewModel.class);
        player = new MediaPlayer();
        manager = GoogleTTSManager.getInstance();

        Intent intent = getIntent();
        subtopicName = intent.getStringExtra("SUBTOPIC_NAME");
        binding.textCategory.setText(subtopicName);

        setUpEventListener();
        setupViewModelObserver();
        viewModel.getCharacterItemList(subtopicName);
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnStartTest.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getcharacterList().observe(this, characterList -> {
            if(characterList != null && !characterList.isEmpty()){
                binding.hiraganaRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
                binding.hiraganaRecyclerView.setAdapter(new JapaneseCharacterAdapter(characterList, this));
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }else if(id == R.id.btn_start_test){
            goSubtopicQuizPage();
        }
    }

    public void goBackDashboardPage(){
        finish();
    }

    public void goSubtopicQuizPage(){
        Intent intent = new Intent(this, SubtopicQuizActivity.class);
        intent.putExtra("SUBTOPIC_NAME", subtopicName);
        startActivity(intent);
    }

    @Override
    public void onItemClick(JapaneseCharacter japaneseCharacter){
        manager.getTTSService(japaneseCharacter.getJapanese_character(), player, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
