package com.example.cs4514_jlpt_exam_helper.learning.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class JapaneseCharacterViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<ArrayList<JapaneseCharacter>> characterList =
            new MutableLiveData<ArrayList<JapaneseCharacter>>();

    public JapaneseCharacterViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<ArrayList<JapaneseCharacter>> getcharacterList() {
        return characterList;
    }

    public void setCharacterList(MutableLiveData<ArrayList<JapaneseCharacter>> characterList) {
        this.characterList = characterList;
    }

    public void getCharacterItemList(String subtopicName){
        if(subtopicName == null){
            return;
        }

        Single<ResponseBean<ArrayList<JapaneseCharacter>>> response = repository.
                getCharacterList(subtopicName);
        response.subscribe(new SingleObserver<ResponseBean<ArrayList<JapaneseCharacter>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<ArrayList<JapaneseCharacter>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    characterList.setValue(bean.getData());
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
