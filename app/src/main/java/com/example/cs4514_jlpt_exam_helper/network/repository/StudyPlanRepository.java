package com.example.cs4514_jlpt_exam_helper.network.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.api.StudyPlanAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.GenerateStudyPlanRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.StudyPlanItemRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.UpdateStudyPlanProgressRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.CategoryProgressResponse;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanSummaryResponse;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StudyPlanRepository {
    private static StudyPlanRepository repository;
    private SessionManager sessionManager;

    public StudyPlanRepository(){
        if(sessionManager == null){
            sessionManager = SessionManager.getInstance();
        }

    }

    public static StudyPlanRepository getInstance(){
        if(repository == null){
            repository = new StudyPlanRepository();
        }

        return repository;
    }

    public Single<ResponseBean<StudyPlanSummaryResponse>> getStudyPlanSummary(String sessionToken){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getStudyPlanSummary(new SessionToken(sessionToken)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<StudyPlanItem>>> getStudyPlan(Context context){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<List<StudyPlanItem>>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getStudyPlan(new SessionToken(session_token)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<JLPTExamDate>>> getJLPTExamDate(){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getJLPTExamDate().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<StudyPlanSummaryResponse>> generateStudyPlan(GenerateStudyPlanRequest request){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.generateStudyPlan(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<LearningItemResponse>> getLearningItemByItemID(List<String> item_id){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getLearningItemByItemID(new StudyPlanItemRequest(item_id)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> updateStudyPlanProgress(Context context, int study_plan_item_id){
        String session_token = sessionManager.getSessionToken(context);

        if (session_token.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<String>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI
                .updateStudyPlanProgress(new UpdateStudyPlanProgressRequest(session_token, study_plan_item_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
