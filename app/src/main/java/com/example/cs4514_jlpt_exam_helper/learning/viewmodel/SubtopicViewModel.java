package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SubtopicViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<ArrayList<Subtopic>> subtopics = new  MutableLiveData<ArrayList<Subtopic>>();
    private MutableLiveData<String> test = new  MutableLiveData<>();

    public MutableLiveData<String> getTest() {
        return test;
    }

    public void setTest(MutableLiveData<String> test) {
        this.test = test;
    }

    public SubtopicViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<ArrayList<Subtopic>> getSubtopics() {
        return subtopics;
    }

    public void setSubtopics(MutableLiveData<ArrayList<Subtopic>> subtopics) {
        this.subtopics = subtopics;
    }

    public void getSubtopicItemList(Context context, String categoryName, String levelName){
        if(categoryName == null || levelName == null){
            return;
        }

        Single<ResponseBean<ArrayList<Subtopic>>> response = repository.
                getSubtopicList(context, categoryName, levelName);
        response.subscribe(new SingleObserver<ResponseBean<ArrayList<Subtopic>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<ArrayList<Subtopic>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    subtopics.setValue(bean.getData());
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Failed");
                test.setValue(e.getMessage());
                d.dispose();
            }
        });
    }
}
