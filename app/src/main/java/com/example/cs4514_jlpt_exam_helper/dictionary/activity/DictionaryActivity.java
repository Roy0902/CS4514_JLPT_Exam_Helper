package com.example.cs4514_jlpt_exam_helper.dictionary.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityDictionaryBinding;
import com.example.cs4514_jlpt_exam_helper.dictionary.adapter.DictionaryAdapter;
import com.example.cs4514_jlpt_exam_helper.dictionary.viewmodel.DictionaryViewModel;

public class DictionaryActivity extends AppCompatActivity implements View.OnClickListener, DictionaryAdapter.OnPlayClickListener {
    private DictionaryViewModel viewModel;
    private GoogleTTSManager manager;
    private ActivityDictionaryBinding binding;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(DictionaryViewModel.class);
        manager = GoogleTTSManager.getInstance();
        player = new MediaPlayer();

        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.etKeyword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    showProgressBar();
                    viewModel.searchWord(binding.etKeyword.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    public void setupViewModelObserver(){
        viewModel.getResponseList().observe(this, responseList -> {
            if(responseList != null && !responseList.isEmpty()){
                binding.dictionaryRecyclerView.setVisibility(View.VISIBLE);
                binding.textNoResult.setVisibility(View.GONE);
                binding.dictionaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                binding.dictionaryRecyclerView.setAdapter(new DictionaryAdapter(this, responseList, this));
            }else{
                binding.dictionaryRecyclerView.setVisibility(View.GONE);
                binding.textNoResult.setVisibility(View.VISIBLE);
            }

           hideProgressBar();
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }
    }

    public void goBackDashboardPage(){
        finish();
    }

    @Override
    public void onPlayClick(String reading){
        manager.getTTSService(reading, player, this);
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
