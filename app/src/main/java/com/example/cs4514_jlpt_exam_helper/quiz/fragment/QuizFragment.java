package com.example.cs4514_jlpt_exam_helper.quiz.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.adapter.QuizQuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener,
        QuizQuestionAdapter.OnPlayClickListener{
    private QuizViewModel viewModel;
    private FragmentQuizBinding binding;
    private List<CharacterQuestion> characterQuestionList;
    private List<GrammarQuestion> grammarQuestionList;
    private int progress;
    private int questionNumber;
    private int score;
    private QuizQuestionAdapter adapter;

    private MediaPlayer player;
    private GoogleTTSManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        characterQuestionList = new ArrayList<>();
        grammarQuestionList = new ArrayList<>();
        player = new MediaPlayer();
        manager = GoogleTTSManager.getInstance();

        progress = 0;
        score = 0;

        if(viewModel.getCharacterQuestionList().getValue() != null){
            characterQuestionList = viewModel.getCharacterQuestionList().getValue();
        }

        if(viewModel.getGrammarQuestionList().getValue() != null){
            grammarQuestionList = viewModel.getGrammarQuestionList().getValue();
        }

        adapter = new QuizQuestionAdapter(this);
        binding.optionRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.optionRecyclerView.setAdapter(adapter);

        questionNumber = characterQuestionList.size() + grammarQuestionList.size();
        binding.progressBar.setMax(questionNumber);

        setQuestion();
        setUpEventListener();

    }

    public void setUpEventListener(){
        binding.btnSpeaker.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnCheck.setOnClickListener(this);
    }

    public void setCharacterQuestion(CharacterQuestion question){
        ArrayList<String> tempOptionList = new ArrayList<>();
        tempOptionList.add(question.getCorrectAnswer());
        binding.textQuestion.setText(question.getQuestion());

        for(JapaneseCharacter q: question.getOptionList()){
            tempOptionList.add(q.getPronunciation());
        }

        Collections.shuffle(tempOptionList);
        adapter.changeQuestion(question.getCorrectAnswer(), tempOptionList);
        setQuizFragment();
    }

    public void setGrammarQuestion(GrammarQuestion question){
        ArrayList<String> tempOptionList = new ArrayList<String>();
        tempOptionList.add(question.getCorrectAnswer());
        binding.textQuestion.setText(question.getQuestion());

        for(Grammar q: question.getOptionList()){
            tempOptionList.add(q.getExplanation());
        }

        Collections.shuffle(tempOptionList);
        setQuizFragment();
    }

    public void setQuizFragment(){
        progress++;
        binding.progressBar.setProgress(progress);
        binding.textProgress.setText((progress) + " / " + questionNumber);
    }

    public void setQuestion(){
        if(progress == questionNumber - 1){
            viewModel.setIsQuizCompleted(true);
            viewModel.setScore(score);
        }else if(progress < characterQuestionList.size()){
            setCharacterQuestion(characterQuestionList.get(progress));
        }else{
            setGrammarQuestion(grammarQuestionList.get(questionNumber-progress));
        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_speaker){
            manager.getTTSService(binding.textQuestion.getText().toString().trim(), player, requireActivity());
        }else if(id == R.id.btn_check){
            checkAnswer();
        }else if(id == R.id.btn_next){
            setQuestion();
        }
    }

    public void checkAnswer(){
        if(adapter.checkAnswer()){
            score++;
        }
    }

    @Override
    public void onPlayClick(String reading){
        manager.getTTSService(reading, player, requireActivity());
    }
}
