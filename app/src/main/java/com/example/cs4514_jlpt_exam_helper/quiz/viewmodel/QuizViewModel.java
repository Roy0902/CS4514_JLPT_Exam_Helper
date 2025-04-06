package com.example.cs4514_jlpt_exam_helper.quiz.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.quiz.data.CharacterQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.GrammarQuestion;
import com.example.cs4514_jlpt_exam_helper.quiz.data.VocabularyQuestion;

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

    private List<CharacterQuestion> characterQuestionList;
    private List<GrammarQuestion> grammarQuestionList;
    private List<VocabularyQuestion> vocabularyQuestionList;

    private MutableLiveData<Boolean> isQuestionReady = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isQuizCompleted = new MutableLiveData<>(false);
    private boolean isLoading = false;

    private MutableLiveData<CharacterQuestion> currentCharacterQuestion = new MutableLiveData<>();
    private MutableLiveData<GrammarQuestion> currentGrammarQuestion = new MutableLiveData<>();
    private MutableLiveData<VocabularyQuestion> currentVocabularyQuestion = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateProgress = new MutableLiveData<>();;


    private int currentQuestion = 0;
    private int totalQuestions = 0;
    private int score = 0;


    public QuizViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }

        characterQuestionList = new ArrayList<>();
        grammarQuestionList= new ArrayList<>();
        vocabularyQuestionList= new ArrayList<>();
    }

    public MutableLiveData<Boolean> getUpdateProgress() {
        return updateProgress;
    }

    public void setUpdateProgress(MutableLiveData<Boolean> updateProgress) {
        this.updateProgress = updateProgress;
    }

    public void setSelectedLevel(MutableLiveData<String> selectedLevel) {
        this.selectedLevel = selectedLevel;
    }

    public MutableLiveData<Integer> getQuestionListNumber() {
        return questionListNumber;
    }

    public void setQuestionListNumber(MutableLiveData<Integer> questionListNumber) {
        this.questionListNumber = questionListNumber;
    }

    public void setIsQuizCompleted(MutableLiveData<Boolean> isQuizCompleted) {
        this.isQuizCompleted = isQuizCompleted;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public MutableLiveData<CharacterQuestion> getCurrentCharacterQuestion() {
        return currentCharacterQuestion;
    }

    public void setCurrentCharacterQuestion(MutableLiveData<CharacterQuestion> currentCharacterQuestion) {
        this.currentCharacterQuestion = currentCharacterQuestion;
    }

    public MutableLiveData<GrammarQuestion> getCurrentGrammarQuestion() {
        return currentGrammarQuestion;
    }

    public void setCurrentGrammarQuestion(MutableLiveData<GrammarQuestion> currentGrammarQuestion) {
        this.currentGrammarQuestion = currentGrammarQuestion;
    }

    public MutableLiveData<VocabularyQuestion> getCurrentVocabularyQuestion() {
        return currentVocabularyQuestion;
    }

    public void setCurrentVocabularyQuestion(MutableLiveData<VocabularyQuestion> currentVocabularyQuestion) {
        this.currentVocabularyQuestion = currentVocabularyQuestion;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
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

    public List<CharacterQuestion> getCharacterQuestionList() {
        return characterQuestionList;
    }

    public void setCharacterQuestionList(List<CharacterQuestion> characterQuestionList) {
        this.characterQuestionList = characterQuestionList;
    }

    public List<GrammarQuestion> getGrammarQuestionList() {
        return grammarQuestionList;
    }

    public void setGrammarQuestionList(List<GrammarQuestion> grammarQuestionList) {
        this.grammarQuestionList = grammarQuestionList;
    }

    public List<VocabularyQuestion> getVocabularyQuestionList() {
        return vocabularyQuestionList;
    }

    public void setVocabularyQuestionList(List<VocabularyQuestion> vocabularyQuestionList) {
        this.vocabularyQuestionList = vocabularyQuestionList;
    }

    public boolean isPass(){
        if (totalQuestions <= 0) {
            return false;
        }
        return score >= totalQuestions / 2;
    }

    public void addScore(){
        score++;
    }

    public void getLearningItem(){
        if(selectedLevel == null){
            return;
        }

        isLoading = true;

        Single<ResponseBean<LearningItemResponse>> response = repository.
                getLearningItemByLevel(selectedLevel.getValue());
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
                    if(bean.getData().getCharacterList() != null
                            && bean.getData().getCharacterList().size() > 0){
                        characterQuestionList = generateCharacterQuestion(
                                bean.getData().getCharacterList());
                        totalQuestions += characterQuestionList.size();
                    }

                    if(bean.getData().getGrammarList() != null
                            && bean.getData().getGrammarList().size() > 0){
                        grammarQuestionList = generateGrammarQuestion(
                                bean.getData().getGrammarList());

                        totalQuestions += grammarQuestionList.size();
                    }

                    if(bean.getData().getVocabularyList() != null
                            && bean.getData().getVocabularyList().size() > 0){
                        vocabularyQuestionList = generateVocabularyQuestion(
                                bean.getData().getVocabularyList());

                        totalQuestions += vocabularyQuestionList.size();
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
                        characterQuestionList = generateCharacterQuestion(
                                bean.getData().getCharacterList());
                        totalQuestions += characterQuestionList.size();
                    }

                    if(bean.getData().getGrammarList() != null){
                        grammarQuestionList = generateGrammarQuestion(
                                bean.getData().getGrammarList());

                        totalQuestions += grammarQuestionList.size();
                    }

                    if(bean.getData().getVocabularyList() != null){
                        vocabularyQuestionList = generateVocabularyQuestion(
                                bean.getData().getVocabularyList());

                        totalQuestions += vocabularyQuestionList.size();
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

        for (int i = 0; i < 5; i++) {
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

    public List<VocabularyQuestion> generateVocabularyQuestion(List<Vocabulary> learningItemList) {
        Collections.shuffle(learningItemList);
        ArrayList<VocabularyQuestion> questionList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int correct_answer_index = new Random().nextInt(learningItemList.size());
            Vocabulary correctAnswer = learningItemList.get(correct_answer_index);

            List<Vocabulary> questionChoice = new ArrayList<>();
            while (questionChoice.size() < 3) {
                int index = new Random().nextInt(learningItemList.size());
                if (index != correct_answer_index) {
                    questionChoice.add(learningItemList.get(index));
                }
            }

            VocabularyQuestion question = new VocabularyQuestion(correctAnswer.getQuestion(),
                    correctAnswer.getAnswer(), questionChoice);
            questionList.add(question);
        }

        return questionList;
    }


    public void updateUserProgress(String subtopicName, Context context){
        if(subtopicName == null){
            return;
        }


        Single<ResponseBean<String>> response = repository.
                updateUserProgress(context, subtopicName);
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {

                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void setQuestion(){
        updateProgress.setValue(false);

        if(totalQuestions == currentQuestion){
            setIsQuizCompleted(true);
        }else if(currentQuestion < characterQuestionList.size()){
            currentCharacterQuestion.setValue(characterQuestionList.get(currentQuestion));
        }else if(currentQuestion - characterQuestionList.size() < grammarQuestionList.size() ){
            currentGrammarQuestion.setValue(grammarQuestionList.get(currentQuestion - characterQuestionList.size()));
        }else{
            int index = currentQuestion- characterQuestionList.size() - grammarQuestionList.size();
            currentVocabularyQuestion.setValue(vocabularyQuestionList.get(index));
        }

        currentQuestion++;
        updateProgress.setValue(true);
    }
}
