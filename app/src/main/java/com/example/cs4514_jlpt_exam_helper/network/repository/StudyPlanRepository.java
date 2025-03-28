package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.api.OtpAPI;
import com.example.cs4514_jlpt_exam_helper.network.api.StudyPlanAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.request.GenerateStudyPlanRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanResponse;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import java.sql.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StudyPlanRepository {
    private static StudyPlanRepository repository;

    public StudyPlanRepository(){

    }

    public static StudyPlanRepository getInstance(){
        if(repository == null){
            repository = new StudyPlanRepository();
        }

        return repository;
    }

    public Single<ResponseBean<StudyPlanResponse>> getStudyPlan(String sessionToken){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getStudyPlan(new SessionToken(sessionToken)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<List<JLPTExamDate>>> getJLPTExamDate(){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.getJLPTExamDate().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<StudyPlanResponse>> generateStudyPlan(GenerateStudyPlanRequest request){
        StudyPlanAPI studyPlanAPI = RetrofitManager.getInstance().getStudyPlanAPI();
        return studyPlanAPI.generateStudyPlan(request).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
