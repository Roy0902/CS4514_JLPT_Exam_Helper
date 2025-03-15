package com.example.cs4514_jlpt_exam_helper.question.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class ReplyViewModel extends ViewModel {
    private QuestionRepository repository;
    private int currentPage = 1;
    private final MutableLiveData<List<Reply>> replyList = new MutableLiveData<>(new ArrayList<>());

    public ReplyViewModel(){
        if(repository == null){
            repository = QuestionRepository.getInstance();
        }
    }

    public MutableLiveData<List<Reply>> getReplyList() {
        return replyList;
    }

    public void resetPage(){
        currentPage = 1;
        replyList.setValue(new ArrayList<>());
    }

    public void getReply(int questionId){

        Single<ResponseBean<List<Reply>>> response =  repository.getReply(questionId);
        response.subscribe(new SingleObserver<ResponseBean<List<Reply>>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<Reply>> bean) {
                int code = bean.getCode();
                if(code >= 200 && code <=299){
                    replyList.setValue(bean.getData());
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
