package com.example.cs4514_jlpt_exam_helper.network.api;

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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LearningItemAPI {

    @POST("/learning-item/get-user-progress")
    Single<ResponseBean<Category>> getUserProgressByLevel(@Body UserProgressRequest request);

    @POST("/learning-item/get-subtopic-list")
    Single<ResponseBean<ArrayList<Subtopic>>> getSubtopicList(@Body SubtopicRequest request);

    @POST("/learning-item/update-user-progress")
    Single<ResponseBean<String>> updateUserProgress(@Body UserProgressRequest request);

    @POST("/learning-item/get-category-progress")
    Single<ResponseBean<ArrayList<CategoryProgressResponse>>> getCategoryProgress(@Body CategoryProgressRequest request);

    @GET("/learning-item/get-character-item-list")
    Single<ResponseBean<ArrayList<JapaneseCharacter>>> getCharacterList(@Query("subtopic_name") String subtopic_name);

    @GET("/learning-item/get-grammar-item-list")
    Single<ResponseBean<ArrayList<Grammar>>> getGrammarList(@Query("subtopic_name") String subtopic_name);

    @GET("/learning-item/get-vocabulary-item-list")
    Single<ResponseBean<ArrayList<Vocabulary>>> getVocabularyList(@Query("subtopic_name") String subtopic_name);

    @GET("/learning-item/get-learning-item-by-level")
    Single<ResponseBean<LearningItemResponse>> getLearningItemListByLevel(@Query("level_name") String level_name);

    @GET("/learning-item/get-learning-item-by-subtopic-name")
    Single<ResponseBean<LearningItemResponse>> getLearningItemListBySubtopic(@Query("subtopic_name") String subtopic_name);

}
