package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.network.response.JishoResponse;
import com.example.cs4514_jlpt_exam_helper.network.api.JishoAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class JishoRepository {
    private static JishoRepository repository;

    public JishoRepository(){

    }

    public static JishoRepository getInstance(){
        if(repository == null){
            repository = new JishoRepository();
        }

        return repository;
    }

    public Single<ResponseBean<List<JishoResponse>>> searchWord(String word){
        JishoAPI jishoAPI = RetrofitManager.getInstance().getJishoAPI();
        return jishoAPI.searchWord(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
