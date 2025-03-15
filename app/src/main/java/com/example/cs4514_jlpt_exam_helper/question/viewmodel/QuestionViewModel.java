package com.example.cs4514_jlpt_exam_helper.question.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.QuestionRepository;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class QuestionViewModel extends ViewModel {
    private QuestionRepository repository;

    private MutableLiveData<Boolean> postQuestionResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final MutableLiveData<List<Question>> questionList = new MutableLiveData<>(new ArrayList<>());

    private int currentPage = 1;

    public QuestionViewModel(){
        if(repository == null){
            repository = QuestionRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getPostQuestionResult() {
        return postQuestionResult;
    }

    public void setPostQuestionResult(MutableLiveData<Boolean> postQuestionResult) {
        this.postQuestionResult = postQuestionResult;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public void resetPage(){
        currentPage = 1;
        questionList.setValue(new ArrayList<>());
    }

    public void getQuestion(){
        if(isLoading == null || isLoading.getValue()){
            return;
        }

        isLoading.setValue(true);

        Single<ResponseBean<List<Question>>> response =  repository.getQuestion(currentPage);
        response.subscribe(new SingleObserver<ResponseBean<List<Question>>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<Question>> bean) {
                int code = bean.getCode();
                if(code >= 200 && code <=299){
                    questionList.setValue(bean.getData());
                    currentPage++;
                }

                isLoading.setValue(false);
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                isLoading.setValue(false);
                d.dispose();
            }
        });
    }

    public void postQuestion(String session_token, String question_title, String question_description){

        Single<ResponseBean<String>> response =  repository.postQuestion(session_token, question_title, question_description);
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> responseBean) {
                int code = responseBean.getCode();
                if(code >= 200 && code <=299){
                    postQuestionResult.setValue(true);
                }else{
                    postQuestionResult.setValue(false);
                }
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }
}
