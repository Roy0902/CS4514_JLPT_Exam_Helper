package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class GrammarViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<ArrayList<Grammar>> grammarList =
            new MutableLiveData<ArrayList<Grammar>>();

    public GrammarViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<ArrayList<Grammar>> getGrammarList() {
        return grammarList;
    }

    public void setGrammarList(MutableLiveData<ArrayList<Grammar>> grammarList) {
        this.grammarList = grammarList;
    }

    public void getGrammarItemList(String subtopicName){
        if(subtopicName == null){
            return;
        }

        Single<ResponseBean<ArrayList<Grammar>>> response = repository.
                getGrammarList(subtopicName);
        response.subscribe(new SingleObserver<ResponseBean<ArrayList<Grammar>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<ArrayList<Grammar>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    grammarList.setValue(bean.getData());
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
