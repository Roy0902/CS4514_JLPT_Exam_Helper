package com.example.cs4514_jlpt_exam_helper.api.retrofit;

import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.AccountAPI;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.LearningItemAPI;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.OtpAPI;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static RetrofitManager mInstance = new RetrofitManager();

    private static AccountAPI accountAPI;
    private static OtpAPI otpAPI;
    private static LearningItemAPI learningItemAPI;

    private RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        accountAPI = retrofit.create(AccountAPI.class);
        otpAPI =retrofit.create(OtpAPI.class);
        learningItemAPI =retrofit.create(LearningItemAPI.class);
    }

    public static RetrofitManager getInstance() {
        return mInstance;
    }

    public AccountAPI getAccountAPI() {
        return accountAPI;
    }

    public OtpAPI getOtpAPI() {
        return otpAPI;
    }

    public LearningItemAPI getLearningItemAPI() {
        return learningItemAPI;
    }
}
