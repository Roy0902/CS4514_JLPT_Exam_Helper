package com.example.cs4514_jlpt_exam_helper.quiz.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class QuizViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<String> selectedLevel = new MutableLiveData<>();
    private MutableLiveData<Integer> questionListNumber = new MutableLiveData<>();

    private MutableLiveData<List<CharacterQuestion>> characterQuestionList = new MutableLiveData<>();
    private MutableLiveData<List<GrammarQuestion>> grammarQuestionList = new MutableLiveData<>();

    private MutableLiveData<Boolean> isQuestionReady = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isQuizCompleted = new MutableLiveData<>(false);
    private boolean isLoading = false;
    private int score = 0;
    private int totalQuestions = 0;


    public QuizViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<String> getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(String level){
        selectedLevel.setValue(level);
        if(!isLoading && !isQuestionReady.getValue()){
            getLearningItem();
        }
    }

    public MutableLiveData<List<CharacterQuestion>> getCharacterQuestionList() {
        return characterQuestionList;
    }

    public void setCharacterQuestionList(MutableLiveData<List<CharacterQuestion>> characterQuestionList) {
        this.characterQuestionList = characterQuestionList;
    }

    public MutableLiveData<List<GrammarQuestion>> getGrammarQuestionList() {
        return grammarQuestionList;
    }

    public void setGrammarQuestionList(MutableLiveData<List<GrammarQuestion>> grammarQuestionList) {
        this.grammarQuestionList = grammarQuestionList;
    }

    public MutableLiveData<Boolean> getIsQuestionReady() {
        return isQuestionReady;
    }

    public void setIsQuestionReady(MutableLiveData<Boolean> isQuestionReady) {
        this.isQuestionReady = isQuestionReady;
    }

    public MutableLiveData<Boolean> getIsQuizCompleted() {
        return isQuizCompleted;
    }

    public void setIsQuizCompleted(boolean isQuizCompleted) {
        this.isQuizCompleted.setValue(isQuizCompleted);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalScore(){
        return totalQuestions;
    }

    public boolean isPass(){
        return score > (totalQuestions / 2);
    }

    public void getLearningItem(){
        if(selectedLevel == null){
            return;
        }

        isLoading = true;

        Single<ResponseBean<LearningItemResponse>> response = repository.getLearningItemByLevel(selectedLevel.getValue());
        response.subscribe(new SingleObserver<ResponseBean<LearningItemResponse>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<LearningItemResponse> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <= 299) {
                    if(bean.getData().getCharacterList() != null){
                        characterQuestionList.setValue(generateCharacterQuestion(
                                bean.getData().getCharacterList()));
                        totalQuestions += characterQuestionList.getValue().size();
                    }

                    if(bean.getData().getGrammarList() != null){
                        grammarQuestionList.setValue(generateGrammarQuestion(
                                bean.getData().getGrammarList()));

                        totalQuestions += grammarQuestionList.getValue().size();
                    }

                    isQuestionReady.setValue(true);
                }

                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }
        });

    }

    public void getLearningItem(String subtopic){
        if(subtopic == null){
            return;
        }

        isLoading = true;

        Single<ResponseBean<LearningItemResponse>> response = repository.
                getLearningItemBySubtopic(subtopic);
        response.subscribe(new SingleObserver<ResponseBean<LearningItemResponse>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<LearningItemResponse> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <= 299) {
                    if(bean.getData().getCharacterList() != null){
                        characterQuestionList.setValue(generateCharacterQuestion(
                                bean.getData().getCharacterList()));
                        totalQuestions += characterQuestionList.getValue().size();
                    }

                    if(bean.getData().getGrammarList() != null){
                        grammarQuestionList.setValue(generateGrammarQuestion(
                                bean.getData().getGrammarList()));

                        totalQuestions += grammarQuestionList.getValue().size();
                    }

                    isQuestionReady.setValue(true);
                }

                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }
        });

    }

    public List<CharacterQuestion> generateCharacterQuestion(
            List<JapaneseCharacter> learningItemList) {
        Collections.shuffle(learningItemList);
        ArrayList<CharacterQuestion> questionList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int correct_answer_index = new Random().nextInt(learningItemList.size());
            JapaneseCharacter correctAnswer = learningItemList.get(correct_answer_index);

            List<JapaneseCharacter> questionChoice = new ArrayList<>();
            while (questionChoice.size() < 3) {
                int index = new Random().nextInt(learningItemList.size());
                if (index != correct_answer_index) {
                    questionChoice.add(learningItemList.get(index));
                }
            }

            CharacterQuestion question = new CharacterQuestion(correctAnswer.getJapanese_character(),
                    correctAnswer.getPronunciation(), questionChoice);
            questionList.add(question);
        }

        return questionList;
    }

    public List<GrammarQuestion> generateGrammarQuestion(List<Grammar> learningItemList) {
        Collections.shuffle(learningItemList);
        ArrayList<GrammarQuestion> questionList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int correct_answer_index = new Random().nextInt(learningItemList.size());
            Grammar correctAnswer = learningItemList.get(correct_answer_index);

            List<Grammar> questionChoice = new ArrayList<>();
            while (questionChoice.size() < 3) {
                int index = new Random().nextInt(learningItemList.size());
                if (index != correct_answer_index) {
                    questionChoice.add(learningItemList.get(index));
                }
            }

            GrammarQuestion question = new GrammarQuestion(correctAnswer.getQuestion(),
                    correctAnswer.getAnswer(), questionChoice);
            questionList.add(question);
        }

        return questionList;
    }
}
