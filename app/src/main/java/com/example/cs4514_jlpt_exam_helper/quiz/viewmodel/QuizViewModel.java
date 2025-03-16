package com.example.cs4514_jlpt_exam_helper.quiz.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.LearningItemRepository;
import com.example.cs4514_jlpt_exam_helper.network.response.LearningItemResponse;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class QuizViewModel extends ViewModel {
    private LearningItemRepository repository;

    private MutableLiveData<String> selectedLevel = new MutableLiveData<>();

    public QuizViewModel(){
        if(repository == null){
            repository = LearningItemRepository.getInstance();
        }
    }

    public MutableLiveData<String> getSelectedLevel() {
        return selectedLevel;
    }

    public void setSelectedLevel(String level){
        selectedLevel.setValue(level);
    }

    public void getLearningItem(){
        if(selectedLevel == null){
            return;
        }

        Single<ResponseBean<LearningItemResponse>> response = repository.getLearningItemByLevel(selectedLevel.getValue());
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
