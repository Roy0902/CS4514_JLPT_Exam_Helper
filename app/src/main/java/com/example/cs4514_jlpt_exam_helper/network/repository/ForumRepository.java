package com.example.cs4514_jlpt_exam_helper.network.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.api.QuestionAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.PostQuestionRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.PostReplyRequest;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ForumRepository {
    private static ForumRepository repository;
    private static SessionManager sessionManager;

    public ForumRepository(){
        if(sessionManager ==null){
            sessionManager = SessionManager.getInstance();
        }
    }

    public static ForumRepository getInstance(){
        if(repository == null){
            repository = new ForumRepository();
        }

        return repository;
    }

    public Single<ResponseBean<String>> postQuestion(Context context, String question_title, String question_description){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<String>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.postQuestion(new PostQuestionRequest(session_token, question_title, question_description))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<Question>>> getQuestion(int page){
        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.getQuestion(page, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<Reply>>> getReply(int page, int question_id){
        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.getReply(page, 10, question_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> postReply(Context context, String reply, int question_id){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<String>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.postReply(new PostReplyRequest(session_token, reply, question_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<Question>>> searchQuestion(String keyword){
        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.searchQuestion(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
