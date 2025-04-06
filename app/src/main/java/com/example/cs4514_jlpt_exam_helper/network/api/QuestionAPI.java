package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.PostQuestionRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.PostReplyRequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionAPI {

    @POST("/forum/post-question")
    Single<ResponseBean<String>> postQuestion(@Body PostQuestionRequest request);

    @POST("/forum/post-reply")
    Single<ResponseBean<String>> postReply(@Body PostReplyRequest request);

    @GET("/forum/get-question")
    Single<ResponseBean<List<Question>>> getQuestion(@Query("page") int page, @Query("limit") int limit);

    @GET("/forum/get-reply")
    Single<ResponseBean<List<Reply>>> getReply(@Query("page") int page, @Query("limit") int limit, @Query("question_id") int questionId);

    @GET("/forum/search-question")
    Single<ResponseBean<List<Question>>> searchQuestion(@Query("keyword") String keyword);
}
