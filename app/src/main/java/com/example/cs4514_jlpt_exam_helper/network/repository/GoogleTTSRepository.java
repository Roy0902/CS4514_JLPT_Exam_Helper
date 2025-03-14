package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.network.api.AccountAPI;
import com.example.cs4514_jlpt_exam_helper.network.api.GoogleTTSAPI;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GoogleTTSRepository {
    private static GoogleTTSRepository repository;

    public GoogleTTSRepository(){

    }

    public static GoogleTTSRepository getInstance(){
        if(repository == null){
            repository = new GoogleTTSRepository();
        }

        return repository;
    }

    public Single<ResponseBean<String>> getGoogleTTSService(String text){
        GoogleTTSAPI googleTTSAPI = RetrofitManager.getInstance().getGoogleTTSAPI();
        return googleTTSAPI.getGoogleTTSService(text).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
