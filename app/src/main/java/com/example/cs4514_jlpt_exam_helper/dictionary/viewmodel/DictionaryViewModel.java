package com.example.cs4514_jlpt_exam_helper.dictionary.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.JishoRepository;
import com.example.cs4514_jlpt_exam_helper.network.response.JishoResponse;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class DictionaryViewModel extends ViewModel {
    private JishoRepository repository;

    private MutableLiveData<List<JishoResponse>> responseList =
            new MutableLiveData<List<JishoResponse>>();

    public DictionaryViewModel(){
        if(repository == null){
            repository = JishoRepository.getInstance();
        }
    }

    private MutableLiveData<String> toastText = new MutableLiveData<>();

    public MutableLiveData<String> getToastText() {
        return toastText;
    }

    public void setToastText(MutableLiveData<String> toastFailedText) {
        this.toastText = toastFailedText;
    }

    public MutableLiveData<List<JishoResponse>> getResponseList() {
        return responseList;
    }

    public void setResponseList(MutableLiveData<List<JishoResponse>> responseList) {
        this.responseList = responseList;
    }

    public void searchWord(String word){
        if(word == null){
            return;
        }

        Single<ResponseBean<List<JishoResponse>>> response = repository.
                searchWord(word);
        response.subscribe(new SingleObserver<ResponseBean<List<JishoResponse>>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<List<JishoResponse>> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    responseList.setValue(bean.getData());
                }else{
                    responseList.setValue(null);
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
