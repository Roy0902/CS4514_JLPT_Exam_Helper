package com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.OtpRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.StudyPlanRepository;
import com.example.cs4514_jlpt_exam_helper.network.request.GenerateStudyPlanRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanResponse;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class StudyPlanViewModel extends ViewModel {
    private StudyPlanRepository studyPlanRepository;

    private MutableLiveData<Boolean> isPlanExisted = new MutableLiveData<>();
    private MutableLiveData<Boolean> examDateListReady = new MutableLiveData<>();

    private MutableLiveData<String> currentLevel = new MutableLiveData<>();
    private MutableLiveData<String> targetLevel = new MutableLiveData<>();
    private MutableLiveData<Boolean> isJustPass = new MutableLiveData<>();
    private MutableLiveData<Integer> daysLeftUntilExam = new MutableLiveData<>();
    private MutableLiveData<Integer> dailyStudyTime = new MutableLiveData<>();

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
        formatDate(this.selectedExamDate.getValue().getExam_date().split("T")[0]);
    }

    public MutableLiveData<Integer> getDailyStudyTime() {
        return dailyStudyTime;
    }

    public void setDailyStudyTime(int dailyStudyTime) {
        this.dailyStudyTime.setValue(dailyStudyTime);
    }

    public void setIsJustPass(Boolean isJustPass) {
        this.isJustPass.setValue(isJustPass);
    }

    public void setTargetLevel(String targetLevel) {
        this.targetLevel.setValue(targetLevel);
    }

    public MutableLiveData<Integer> getDaysLeftUntilExam() {
        return daysLeftUntilExam;
    }

    public void setDaysLeftUntilExam(int daysLeftUntilExam) {
        this.daysLeftUntilExam.setValue(daysLeftUntilExam);
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

    public void generateStudyPlan(String session_token){
        GenerateStudyPlanRequest request = createRequest(session_token);

        Single<ResponseBean<StudyPlanResponse>> response = studyPlanRepository.generateStudyPlan(request);
        response.subscribe(new SingleObserver<ResponseBean<StudyPlanResponse>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<StudyPlanResponse> bean) {
                int code = bean.getCode();

                System.out.println("Message: (S) " + bean.getMessage());
                if (code >= 200 && code <=299) {

                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Message: " + e.getMessage());
                d.dispose();

                examDateListReady.setValue(false);
            }
        });
    }

    public GenerateStudyPlanRequest createRequest(String session_token){
        String target_goal;

        if(isJustPass.getValue()){
            target_goal = "true";
        }else{
            target_goal = "false";
        }

        return new GenerateStudyPlanRequest(currentLevel.getValue(), targetLevel.getValue(),
                                            dailyStudyTime.getValue(), daysLeftUntilExam.getValue(),
                                            target_goal, session_token);

    }

    public void formatDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate givenDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();

        long daysLeft = ChronoUnit.DAYS.between(currentDate, givenDate);
        daysLeftUntilExam.setValue((int) daysLeft);
    }
}
