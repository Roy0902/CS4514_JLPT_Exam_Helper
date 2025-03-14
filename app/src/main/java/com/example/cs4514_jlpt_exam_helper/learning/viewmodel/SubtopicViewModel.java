package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public void getSubtopicItemList(String categoryName, String levelName){
        if(categoryName == null || levelName == null){
            return;
        }

        Single<ResponseBean<ArrayList<Subtopic>>> response = repository.
                getSubtopicList(categoryName, levelName);
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
                d.dispose();
            }
        });
    }
}
