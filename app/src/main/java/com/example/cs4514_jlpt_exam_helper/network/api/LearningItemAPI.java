package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.network.request.UserProgressRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;

import java.util.ArrayList;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LearningItemAPI {

    @POST("/learning-item/get-user-progress")
    Single<ResponseBean<Category>> getUserProgressByLevel(@Body UserProgressRequest request);

    @GET("/learning-item/get-subtopic-list")
    Single<ResponseBean<ArrayList<Subtopic>>> getSubtopicList(@Query("category_name") String category_name,
                                                              @Query("level_name") String level_name);

    @GET("/learning-item/get-character-item-list")
    Single<ResponseBean<ArrayList<JapaneseCharacter>>> getCharacterList(@Query("subtopic_name") String subtopic_name);

    @GET("/learning-item/get-grammar-item-list")
    Single<ResponseBean<ArrayList<Grammar>>> getGrammarList(@Query("subtopic_name") String subtopic_name);

    @GET("/learning-item/get-learning-item-by-level")
    Single<ResponseBean<LearningItemResponse>> getLearningItemListByLevel(@Query("level_name") String level_name);

    @GET("/learning-item/get-learning-item-by-subtopic-name")
    Single<ResponseBean<LearningItemResponse>> getLearningItemListBySubtopic(@Query("subtopic_name") String subtopic_name);

}
