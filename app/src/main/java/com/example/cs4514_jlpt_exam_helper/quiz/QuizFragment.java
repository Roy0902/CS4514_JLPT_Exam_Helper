package com.example.cs4514_jlpt_exam_helper.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.L;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuizBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSelectLevelBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.Question;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment implements View.OnClickListener{
    private QuizViewModel viewModel;
    private FragmentQuizBinding binding;
    private List<CharacterQuestion> characterQuestionList;
    private List<GrammarQuestion> grammarQuestionList;
    private int progress;
    private int questionNumber;

    private int questionListNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuizBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel.getLearningItem();
        setUpEventListener();
        initViewModelObserver();
        characterQuestionList = new ArrayList<>();
        grammarQuestionList = new ArrayList<>();

        progress = 1;

        if(viewModel.getCharacterQuestionList().getValue() != null){
            characterQuestionList = viewModel.getCharacterQuestionList().getValue();
        }

        if(viewModel.getGrammarQuestionList().getValue() != null){
            grammarQuestionList = viewModel.getGrammarQuestionList().getValue();
        }

        binding.btnSubmit.setOnClickListener(this);
        questionNumber = characterQuestionList.size() + grammarQuestionList.size();

        setQuestion();

    }

    public void setUpEventListener(){

    }

    public void initViewModelObserver() {


    }

    public void setCharacterQuestion(CharacterQuestion question){
        ArrayList<String> tempOptionList = new ArrayList<String>();
        tempOptionList.add(question.getCorrectAnswer());
        binding.textQuestion.setText(question.getQuestion());

        for(JapaneseCharacter q: question.getOptionList()){
            tempOptionList.add(q.getPronunciation());
        }

        Collections.shuffle(tempOptionList);
        setQuizFragment(tempOptionList);
    }

    public void setGrammarQuestion(GrammarQuestion question){
        ArrayList<String> tempOptionList = new ArrayList<String>();
        tempOptionList.add(question.getCorrectAnswer());
        binding.textQuestion.setText(question.getQuestion());

        for(Grammar q: question.getOptionList()){
            tempOptionList.add(q.getExplanation());
        }

        Collections.shuffle(tempOptionList);
        setQuizFragment(tempOptionList);
    }

    public void setQuizFragment(List<String> tempOptionList){
        binding.progressBar.setProgress(progress);
        binding.textProgress.setText(progress + " / " + questionNumber);
        binding.optionOne.setText(tempOptionList.get(0));
        binding.optionTwo.setText(tempOptionList.get(1));
        binding.optionThree.setText(tempOptionList.get(2));
        binding.optionFour.setText(tempOptionList.get(3));
    }

    public void setQuestion(){
        if(progress < characterQuestionList.size()){
            setCharacterQuestion(characterQuestionList.get(progress));
        }else{

        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_submit){
            progress++;
            setQuestion();
        }
    }
}
