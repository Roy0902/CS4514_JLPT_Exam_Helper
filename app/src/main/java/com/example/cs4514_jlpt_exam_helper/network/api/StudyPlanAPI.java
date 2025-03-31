package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.GenerateStudyPlanRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.StudyPlanItemRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.UpdateStudyPlanProgressRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanSummaryResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudyPlanAPI {

    @POST("/study-plan/get-study-plan-summary")
    Single<ResponseBean<StudyPlanSummaryResponse>> getStudyPlanSummary(@Body SessionToken request);

    @POST("/study-plan/get-study-plan")
    Single<ResponseBean<List<StudyPlanItem>>> getStudyPlan(@Body SessionToken request);

    @POST("/study-plan/generate-study-plan")
    Single<ResponseBean<StudyPlanSummaryResponse>> generateStudyPlan(@Body GenerateStudyPlanRequest request);

    @POST("/study-plan/get-learning-item-by-item-id")
    Single<ResponseBean<LearningItemResponse>> getLearningItemByItemID(@Body StudyPlanItemRequest request);

    @POST("/study-plan/update-study-plan-progress")
    Single<ResponseBean<String>> updateStudyPlanProgress(@Body UpdateStudyPlanProgressRequest request);

    @GET("/study-plan/get-jlpt-exam-date")
    Single<ResponseBean<List<JLPTExamDate>>> getJLPTExamDate();
}
