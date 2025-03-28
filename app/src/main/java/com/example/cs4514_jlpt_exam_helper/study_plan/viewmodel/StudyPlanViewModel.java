package com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.OtpRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.StudyPlanRepository;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanResponse;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class StudyPlanViewModel extends ViewModel {
    private StudyPlanRepository studyPlanRepository;

    private MutableLiveData<Boolean> isPlanExisted = new MutableLiveData<>();
    private MutableLiveData<String> currentLevel = new MutableLiveData<>();
    private MutableLiveData<String> targetLevel = new MutableLiveData<>();
    private MutableLiveData<Boolean> isJustPass = new MutableLiveData<>();
    private MutableLiveData<Boolean> examDateListReady = new MutableLiveData<>();

    private MutableLiveData<JLPTExamDate> selectedExamDate = new MutableLiveData<>();

    private List<JLPTExamDate> examDateList = new ArrayList<>();

    private int completedPlan;
    private int totalPlan;


    public StudyPlanViewModel(){
        if(studyPlanRepository == null){
            studyPlanRepository = StudyPlanRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getIsPlanExisted() {
        return isPlanExisted;
    }

    public void setIsPlanExisted(MutableLiveData<Boolean> isPlanExisted) {
        this.isPlanExisted = isPlanExisted;
    }

    public int getCompletedPlan() {
        return completedPlan;
    }

    public void setCompletedPlan(int completedPlan) {
        this.completedPlan = completedPlan;
    }

    public MutableLiveData<String> getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel.setValue(currentLevel);
    }

    public MutableLiveData<String> getTargetLevel() {
        return targetLevel;
    }

    public MutableLiveData<Boolean> getIsJustPass() {
        return isJustPass;
    }

    public List<JLPTExamDate> getExamDateList() {
        return examDateList;
    }

    public void setExamDateList(List<JLPTExamDate> examDateList) {
        this.examDateList = examDateList;
    }

    public MutableLiveData<Boolean> getExamDateListReady() {
        return examDateListReady;
    }

    public void setExamDateListReady(MutableLiveData<Boolean> examDateListReady) {
        this.examDateListReady = examDateListReady;
    }

    public MutableLiveData<JLPTExamDate> getSelectedExamDate() {
        return selectedExamDate;
    }

    public void setSelectedExamDate(JLPTExamDate selectedExamDate) {
        this.selectedExamDate.setValue(selectedExamDate);
    }

    public void setIsJustPass(Boolean isJustPass) {
        this.isJustPass.setValue(isJustPass);
    }

    public void setTargetLevel(String targetLevel) {
        this.targetLevel.setValue(targetLevel);
    }

    public int getTotalPlan() {
        return totalPlan;
    }

    public void setTotalPlan(int totalPlan) {
        this.totalPlan = totalPlan;
    }

    public void getStudyPlan(String sessionToken){
        if(sessionToken == null){
            return;
        }

        Single<ResponseBean<StudyPlanResponse>> response = studyPlanRepository.getStudyPlan(sessionToken);
        response.subscribe(new SingleObserver<ResponseBean<StudyPlanResponse>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<StudyPlanResponse> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <=299) {
                    StudyPlanResponse response = bean.getData();
                    if(response.getTotalStudyPlan() <= 0){
                        isPlanExisted.setValue(false);
                    }else{
                        isPlanExisted.setValue(true);
                        completedPlan = response.getCompletedStudyPlan();
                        totalPlan = response.getTotalStudyPlan();
                    }
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void getJLPTExamDate(){

        Single<ResponseBean<List<JLPTExamDate>>> response = studyPlanRepository.getJLPTExamDate();
        response.subscribe(new SingleObserver<ResponseBean<List<JLPTExamDate>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<JLPTExamDate>> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <=299) {
                    examDateList = bean.getData();
                    examDateListReady.setValue(true);
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
                d.dispose();

                examDateListReady.setValue(false);
            }
        });
    }
}
