package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
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

    public LearningItemRepository(){

    }

    public static LearningItemRepository getInstance(){
        if(repository == null){
            repository = new LearningItemRepository();
        }

        return repository;
    }

    public Single<ResponseBean<Category>> getUserProgress(String level, String sessionToken){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getUserProgressByLevel(new UserProgressRequest(level, sessionToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<Subtopic>>> getSubtopicList(String category_name, String level_name, String session_token){
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

    public Single<ResponseBean<String>> updateUserProgress(String subtopic_name, String session_token){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.updateUserProgress(new UserProgressRequest(subtopic_name, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<ArrayList<CategoryProgressResponse>>> getCategoryProgress(String level_name, String session_token){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getCategoryProgress(new CategoryProgressRequest(level_name, session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
