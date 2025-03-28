package com.example.cs4514_jlpt_exam_helper.learning.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityGrammarBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityVocabularyBinding;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.GrammarAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.JapaneseCharacterAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.VocabularyAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.GrammarViewModel;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.VocabularyViewModel;

public class VocabularyActivity extends AppCompatActivity implements View.OnClickListener, VocabularyAdapter.OnPlayClickListener {
    private VocabularyViewModel viewModel;
    private ActivityVocabularyBinding binding;
    private String subtopicName;

    private MediaPlayer player;
    private GoogleTTSManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVocabularyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(VocabularyViewModel.class);

        manager = GoogleTTSManager.getInstance();
        player = new MediaPlayer();

        Intent intent = getIntent();
        subtopicName = intent.getStringExtra("SUBTOPIC_NAME");
        binding.textCategory.setText(subtopicName);

        setUpEventListener();
        setupViewModelObserver();
        viewModel.getVocabularyItemList(subtopicName);
    }

    public void setUpEventListener() {
        binding.btnBack.setOnClickListener(this);
        binding.btnStartQuiz.setOnClickListener(this);
    }

    public void setupViewModelObserver() {
        viewModel.getVocabularyList().observe(this, vocabularyList -> {
            if (vocabularyList != null && !vocabularyList.isEmpty()) {
                binding.vocabularyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.vocabularyRecyclerView.setAdapter(new VocabularyAdapter( this, vocabularyList,this));
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            goBackDashboardPage();
        }else if(id == R.id.btn_start_quiz){
            goSubtopicQuizPage();
        }
    }

    public void goBackDashboardPage() {
        finish();
    }

    @Override
    public void onPlayClick(String reading){
        manager.getTTSService(reading, player, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void goSubtopicQuizPage(){
        Intent intent = new Intent(VocabularyActivity.this, SubtopicQuizActivity.class);
        intent.putExtra("SUBTOPIC_NAME", subtopicName);
        startActivity(intent);
    }
}
