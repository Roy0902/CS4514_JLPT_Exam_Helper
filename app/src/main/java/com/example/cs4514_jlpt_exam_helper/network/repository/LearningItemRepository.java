package com.example.cs4514_jlpt_exam_helper.network.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.network.request.CategoryProgressRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.SubtopicRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.UserProgressRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.CategoryProgressResponse;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.network.api.LearningItemAPI;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LearningItemRepository {
    private static LearningItemRepository repository;
    private SessionManager sessionManager;

    public LearningItemRepository(){
        if(sessionManager == null){
            sessionManager = SessionManager.getInstance();
        }
    }

    public static LearningItemRepository getInstance(){
        if(repository == null){
            repository = new LearningItemRepository();
        }

        return repository;
    }

    public Single<ResponseBean<Category>> getUserProgress(String level, Context context){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<Category>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getUserProgressByLevel(new UserProgressRequest(level, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<Subtopic>>> getSubtopicList(Context context, String category_name, String level_name){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<ArrayList<Subtopic>>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getSubtopicList(new SubtopicRequest(category_name, level_name, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<JapaneseCharacter>>> getCharacterList(String subtopic_name){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getCharacterList(subtopic_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<Grammar>>> getGrammarList(String subtopic_name){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getGrammarList(subtopic_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<Vocabulary>>> getVocabularyList(String subtopic_name){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getVocabularyList(subtopic_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<LearningItemResponse>> getLearningItemByLevel(String level){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getLearningItemListByLevel(level)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<LearningItemResponse>> getLearningItemBySubtopic(String subtopic_name) {
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getLearningItemListBySubtopic(subtopic_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> updateUserProgress(Context context, String subtopic_name){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<String>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.updateUserProgress(new UserProgressRequest(subtopic_name, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<CategoryProgressResponse>>> getCategoryProgress(Context context, String level_name){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<ArrayList<CategoryProgressResponse>>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getCategoryProgress(new CategoryProgressRequest(level_name, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
