package com.example.cs4514_jlpt_exam_helper.forum.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.ForumRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ForumViewModel extends ViewModel {
    private ForumRepository repository;

    private MutableLiveData<Boolean> postQuestionResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> postReplyResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isQuestionListLoading = new MutableLiveData<>(false);
    private MutableLiveData<Question> selectedQuestion = new MutableLiveData<>();

    private final MutableLiveData<String> currentScreen = new MutableLiveData<>();

    private final MutableLiveData<List<Question>> questionList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Question>> filteredQuestionList = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Reply>> replyList = new MutableLiveData<>(new ArrayList<>());

    private final MutableLiveData<String> toastText = new MutableLiveData<>();

    private int questionCurrentPage = 1;
    private int replyCurrentPage = 1;

    public ForumViewModel(){
        if(repository == null){
            repository = ForumRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getPostQuestionResult() {
        return postQuestionResult;
    }

    public void setPostQuestionResult(MutableLiveData<Boolean> postQuestionResult) {
        this.postQuestionResult = postQuestionResult;
    }

    public MutableLiveData<Boolean> getPostReplyResult() {
        return postReplyResult;
    }

    public void setPostReplyResult(MutableLiveData<Boolean> postReplyResult) {
        this.postReplyResult = postReplyResult;
    }

    public MutableLiveData<Boolean> getIsQuestionListLoading() {
        return isQuestionListLoading;
    }

    public void setIsQuestionListLoading(MutableLiveData<Boolean> isQuestionListLoading) {
        this.isQuestionListLoading = isQuestionListLoading;
    }

    public MutableLiveData<List<Question>> getQuestionList() {
        return questionList;
    }

    public MutableLiveData<List<Reply>> getReplyList() {
        return replyList;
    }

    public MutableLiveData<Question> getSelectedQuestion() {
        return selectedQuestion;
    }

    public void setSelectedQuestion(MutableLiveData<Question> selectedQuestion) {
        this.selectedQuestion = selectedQuestion;
    }

    public MutableLiveData<String> getToastText() {
        return toastText;
    }

    public MutableLiveData<String> getCurrentScreen() {
        return currentScreen;
    }

    public MutableLiveData<List<Question>> getFilteredQuestionList() {
        return filteredQuestionList;
    }

    public void resetQuestionPage(){
        questionCurrentPage = 1;
        questionList.setValue(new ArrayList<>());
    }

    public void resetReplyPage(){
        replyCurrentPage = 1;
        replyList.setValue(new ArrayList<>());
    }

    public void showForum() {
        currentScreen.setValue("QUESTION");
    }

    public void showPostQuestion() {
        currentScreen.setValue("POST_QUESTION");
    }

    public void showReply() {
        currentScreen.setValue("REPLY");
    }

    public void showPostReply() {
        currentScreen.setValue("POST_REPLY");
    }

    public void showSearchQuestion() {
        currentScreen.setValue("SEARCH_QUESTION");
    }

    public void getQuestion(){
        if(isQuestionListLoading == null || isQuestionListLoading.getValue()){
            return;
        }

        isQuestionListLoading.setValue(true);

        Single<ResponseBean<List<Question>>> response =  repository.getQuestion(questionCurrentPage);
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
                    questionCurrentPage++;
                }

                isQuestionListLoading.setValue(false);
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                isQuestionListLoading.setValue(false);
                d.dispose();
            }
        });
    }

    public void postQuestion(Context context, String question_title, String question_description){


        Single<ResponseBean<String>> response =  repository.postQuestion(context, question_title, question_description);
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

    public void postReply(Context context, String reply, int question_id){

        Single<ResponseBean<String>> response =  repository.postReply(context, reply, question_id);
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
                    postReplyResult.setValue(true);
                }else{
                    postReplyResult.setValue(false);
                }
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void getReply(int questionId) {

        Single<ResponseBean<List<Reply>>> response = repository.getReply(replyCurrentPage, questionId);
        response.subscribe(new SingleObserver<ResponseBean<List<Reply>>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<Reply>> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <= 299) {
                    replyList.setValue(bean.getData());
                    replyCurrentPage++;
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void searchQuestion(String keyword){
        if(isQuestionListLoading == null || isQuestionListLoading.getValue()){
            return;
        }

        isQuestionListLoading.setValue(true);

        Single<ResponseBean<List<Question>>> response =  repository.searchQuestion(keyword);
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
                    filteredQuestionList.setValue(bean.getData());
                }

                isQuestionListLoading.setValue(false);
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                isQuestionListLoading.setValue(false);
                d.dispose();
            }
        });
    }
}
