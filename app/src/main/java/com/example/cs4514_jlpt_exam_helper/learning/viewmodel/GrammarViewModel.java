package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
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

    private MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();

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

    public MutableLiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    public void setUpdateSuccess(MutableLiveData<Boolean> updateSuccess) {
        this.updateSuccess = updateSuccess;
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

    public void updateUserProgress(Context context, String subtopicName){
        if(subtopicName == null){
            return;
        }

        Single<ResponseBean<String>> response = repository.
                updateUserProgress(context, subtopicName);
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {
                updateSuccess.setValue(true);
                d.dispose();
            }

            @Override

            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }
}
