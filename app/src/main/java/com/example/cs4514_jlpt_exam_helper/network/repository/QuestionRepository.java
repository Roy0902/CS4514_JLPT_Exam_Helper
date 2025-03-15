package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.api.JishoAPI;
import com.example.cs4514_jlpt_exam_helper.network.api.QuestionAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.PostQuestionRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.JishoResponse;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QuestionRepository {
    private static QuestionRepository repository;

    public QuestionRepository(){

    }

    public static QuestionRepository getInstance(){
        if(repository == null){
            repository = new QuestionRepository();
        }

        return repository;
    }

    public Single<ResponseBean<String>> postQuestion(String session_token, String question_title, String question_description){
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

    public Single<ResponseBean<List<Reply>>> getReply(int question_id){
        QuestionAPI questionAPI = RetrofitManager.getInstance().getQuestionAPI();
        return questionAPI.getReply(question_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
