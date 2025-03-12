package com.example.cs4514_jlpt_exam_helper.api.repository;

import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.data.Category;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.LearningItemAPI;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.OtpAPI;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LearningItemRepository {
    private static LearningItemRepository repository;

    public LearningItemRepository(){

    }

    public static LearningItemRepository getInstance(){
        if(repository == null){
            repository = new LearningItemRepository();
        }

        return repository;
    }

    public Single<ResponseBean<Category>> getUserProgress(String level, String sessionToken){
        LearningItemAPI learningItemAPI = RetrofitManager.getInstance().getLearningItemAPI();
        return learningItemAPI.getUserProgressByLevel(level, sessionToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
