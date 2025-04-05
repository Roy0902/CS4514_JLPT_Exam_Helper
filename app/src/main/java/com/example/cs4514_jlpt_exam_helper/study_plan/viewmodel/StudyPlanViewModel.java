package com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.StudyPlanRepository;
import com.example.cs4514_jlpt_exam_helper.network.request.GenerateStudyPlanRequest;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;
import com.example.cs4514_jlpt_exam_helper.network.response.StudyPlanSummaryResponse;

import java.time.LocalDate;
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
    private MutableLiveData<Boolean> studyPlanReady = new MutableLiveData<>();
    private MutableLiveData<Boolean> studyPlanItemReady = new MutableLiveData<>();

    private MutableLiveData<String> currentLevel = new MutableLiveData<>();
    private MutableLiveData<String> targetLevel = new MutableLiveData<>();
    private MutableLiveData<Boolean> isJustPass = new MutableLiveData<>();
    private MutableLiveData<Integer> daysLeftUntilExam = new MutableLiveData<>();
    private MutableLiveData<Integer> dailyStudyTime = new MutableLiveData<>();

    private MutableLiveData<JLPTExamDate> selectedExamDate = new MutableLiveData<>();
    private MutableLiveData<StudyPlanItem> selectedStudyPlan = new MutableLiveData<>();

    private MutableLiveData<List<StudyPlanItem>> studyPlanList = new MutableLiveData<>();

    private MutableLiveData<List<Grammar>> grammarItemList = new MutableLiveData<>();
    private MutableLiveData<List<Vocabulary>> vocabularyItemList = new MutableLiveData<>();

    private MutableLiveData<Boolean> processEnd = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateProgressSuccess = new MutableLiveData<>();

    private List<JLPTExamDate> examDateList = new ArrayList<>();

    private int completedPlan;
    private int totalPlan;
    private boolean badStudyPlan;
    private int selectedPosition;
    private int currentFragment;


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

    public boolean isBadStudyPlan(){
        return badStudyPlan;
    }

    public MutableLiveData<List<StudyPlanItem>> getStudyPlanList() {
        return studyPlanList;
    }

    public MutableLiveData<Boolean> getStudyPlanReady() {
        return studyPlanReady;
    }

    public void setStudyPlanReady(MutableLiveData<Boolean> studyPlanReady) {
        this.studyPlanReady = studyPlanReady;
    }

    public void setStudyPlanList(MutableLiveData<List<StudyPlanItem>> studyPlanList) {
        this.studyPlanList = studyPlanList;
    }

    public MutableLiveData<StudyPlanItem> getSelectedStudyPlan() {
        return selectedStudyPlan;
    }

    public void setSelectedStudyPlan(StudyPlanItem selectedStudyPlan) {
        this.selectedStudyPlan.setValue(selectedStudyPlan);
    }

    public MutableLiveData<Boolean> getStudyPlanItemReady() {
        return studyPlanItemReady;
    }

    public void setStudyPlanItemReady(MutableLiveData<Boolean> studyPlanItemReady) {
        this.studyPlanItemReady = studyPlanItemReady;
    }

    public MutableLiveData<List<Vocabulary>> getVocabularyItemList() {
        return vocabularyItemList;
    }

    public void setVocabularyItemList(MutableLiveData<List<Vocabulary>> vocabularyItemList) {
        this.vocabularyItemList = vocabularyItemList;
    }

    public MutableLiveData<List<Grammar>> getGrammarItemList() {
        return grammarItemList;
    }

    public void setGrammarItemList(MutableLiveData<List<Grammar>> grammarItemList) {
        this.grammarItemList = grammarItemList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(int currentFragment) {
        this.currentFragment = currentFragment;
    }

    public MutableLiveData<Boolean> getProcessEnd() {
        return processEnd;
    }

    public void setProcessEnd(MutableLiveData<Boolean> processEnd) {
        this.processEnd = processEnd;
    }

    public MutableLiveData<Boolean> getUpdateProgressSuccess() {
        return updateProgressSuccess;
    }

    public void setUpdateProgressSuccess(MutableLiveData<Boolean> updateProgressSuccess) {
        this.updateProgressSuccess = updateProgressSuccess;
    }

    public void getStudyPlanSummary(Context context){
        String sessionToken = SessionManager.getInstance().getSessionToken(context);
        if(sessionToken.equals(Constant.error_not_found)){
            return;
        }

        Single<ResponseBean<StudyPlanSummaryResponse>> response = studyPlanRepository.
                getStudyPlanSummary(sessionToken);
        response.subscribe(new SingleObserver<ResponseBean<StudyPlanSummaryResponse>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<StudyPlanSummaryResponse> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <=299) {
                    completedPlan = bean.getData().getCompleted_study_plan();
                    totalPlan = bean.getData().getTotal_study_plan();
                    badStudyPlan = bean.getData().getFitness_score() <= 0;

                    if(bean.getData().getTotal_study_plan() <= 0){
                        isPlanExisted.setValue(false);
                    }else{
                        isPlanExisted.setValue(true);
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

    public void getStudyPlan(Context context){
        String sessionToken = SessionManager.getInstance().getSessionToken(context);
        if(sessionToken.equals(Constant.error_not_found)){
            return;
        }

        Single<ResponseBean<List<StudyPlanItem>>> response = studyPlanRepository.getStudyPlan(sessionToken);
        response.subscribe(new SingleObserver<ResponseBean<List<StudyPlanItem>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<StudyPlanItem>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    studyPlanList.setValue(bean.getData());
                    studyPlanReady.setValue(true);
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
                studyPlanReady.setValue(false);
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

        Single<ResponseBean<StudyPlanSummaryResponse>> response = studyPlanRepository.generateStudyPlan(request);
        response.subscribe(new SingleObserver<ResponseBean<StudyPlanSummaryResponse>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
                processEnd.setValue(true);
            }

            @Override
            public void onSuccess(ResponseBean<StudyPlanSummaryResponse> bean) {
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
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

    public void getStudyPlanItemList(List<String> itemIDList) {
        if (itemIDList == null) {
            return;
        }

        Single<ResponseBean<LearningItemResponse>> response = studyPlanRepository.
                getLearningItemByItemID(itemIDList);
        response.subscribe(new SingleObserver<ResponseBean<LearningItemResponse>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<LearningItemResponse> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <= 299) {
                    if (bean.getData().getGrammarList() != null) {
                        grammarItemList.setValue(bean.getData().getGrammarList());
                    }

                    if (bean.getData().getVocabularyList() != null) {
                        vocabularyItemList.setValue(bean.getData().getVocabularyList());
                    }

                    studyPlanItemReady.setValue(true);
                }

                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void formatDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate givenDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();

        long daysLeft = ChronoUnit.DAYS.between(currentDate, givenDate);
        daysLeftUntilExam.setValue((int) daysLeft);
    }

    public void updateStudyPlanProgress(String sessionToken){
        if(sessionToken == null || selectedStudyPlan.getValue() == null){
            return;
        }

        Single<ResponseBean<String>> response = studyPlanRepository.
                updateStudyPlanProgress(sessionToken, selectedStudyPlan.getValue().getStudy_plan_item_id());
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <= 299) {
                    updateProgressSuccess.setValue(true);
                }

                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }

        });
    }
}
