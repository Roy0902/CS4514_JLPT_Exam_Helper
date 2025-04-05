package com.example.cs4514_jlpt_exam_helper.quiz.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuizBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.adapter.QuizQuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.Question;
import com.example.cs4514_jlpt_exam_helper.quiz.data.VocabularyQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.StudyPlanItemListFragment;
import com.example.cs4514_jlpt_exam_helper.study_plan.fragment.StudyPlanListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener,
        QuizQuestionAdapter.OnPlayClickListener{
    private QuizViewModel viewModel;
    private FragmentQuizBinding binding;
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

        player = new MediaPlayer();
        manager = GoogleTTSManager.getInstance();

        adapter = new QuizQuestionAdapter(this);
        binding.optionRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.optionRecyclerView.setAdapter(adapter);


        binding.progressBar.setMax(viewModel.getTotalQuestions());

        setUpEventListener();
        setupViewModelObserver();

        viewModel.setQuestion();
    }

    public void setupViewModelObserver(){
        viewModel.getCurrentCharacterQuestion().observe(getViewLifecycleOwner(), question->{
            if(question != null){
                setCharacterQuestion(question);
            }
        });

        viewModel.getCurrentGrammarQuestion().observe(getViewLifecycleOwner(), question->{
            if(question != null){
                setGrammarQuestion(question);
            }
        });

        viewModel.getCurrentVocabularyQuestion().observe(getViewLifecycleOwner(), question->{
            if(question != null){
                setVocabularyQuestion(question);
            }
        });

        viewModel.getUpdateProgress().observe(getViewLifecycleOwner(), isUpdate->{
            if(isUpdate){
                setQuizFragment();
            }
        });
    }

    public void setUpEventListener(){
        binding.btnSpeaker.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnCheck.setOnClickListener(this);
    }

    public void setCharacterQuestion(CharacterQuestion question){
        ArrayList<Question> tempOptionList = new ArrayList<>();
        tempOptionList.add(question);
        binding.textQuestion.setText(question.getQuestion());

        for(JapaneseCharacter q: question.getOptionList()){
            tempOptionList.add(new CharacterQuestion(q.getQuestion(), q.getAnswer()));
        }

        Collections.shuffle(tempOptionList);
        adapter.changeQuestion(question.getCorrectAnswer(), tempOptionList);
    }

    public void setGrammarQuestion(GrammarQuestion question){
        ArrayList<Question> tempOptionList = new ArrayList<>();
        tempOptionList.add(question);
        binding.textQuestion.setText(question.getQuestion());

        for(Grammar q: question.getOptionList()){
            tempOptionList.add(new GrammarQuestion(q.getQuestion(), q.getAnswer()));
        }

        Collections.shuffle(tempOptionList);
        adapter.changeQuestion(question.getAnswer(), tempOptionList);
    }

    public void setVocabularyQuestion(VocabularyQuestion question){
        ArrayList<Question> tempOptionList = new ArrayList<>();
        tempOptionList.add(question);
        binding.textQuestion.setText(question.getQuestion());

        for(Vocabulary v: question.getOptionList()){
            tempOptionList.add(new VocabularyQuestion(v.getQuestion(), v.getAnswer()));
        }

        Collections.shuffle(tempOptionList);
        adapter.changeQuestion(question.getCorrectAnswer(), tempOptionList);
    }

    public void setQuizFragment(){
        int currentQuestion = viewModel.getCurrentQuestion();
        int totalQuestion = viewModel.getTotalQuestions();

        binding.progressBar.setProgress(currentQuestion);
        binding.textProgress.setText((currentQuestion) + " / " + totalQuestion);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_speaker){
            manager.getTTSService(binding.textQuestion.getText().toString().trim(), player, requireActivity());
        }else if(id == R.id.btn_check){
            checkAnswer();
        }else if(id == R.id.btn_next){
            viewModel.setQuestion();
        }
    }

    public void checkAnswer(){
        if(adapter.checkAnswer()){
            viewModel.addScore();
        }
    }

    @Override
    public void onPlayClick(Question question){
        manager.getTTSService(question.getQuestion(), player, requireActivity());
    }

    public void showToast(String text){
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
