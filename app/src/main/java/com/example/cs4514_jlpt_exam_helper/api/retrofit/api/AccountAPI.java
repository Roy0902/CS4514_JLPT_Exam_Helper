package com.example.cs4514_jlpt_exam_helper.api.retrofit.api;

import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.SessionToken;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountAPI {

    @POST("/sign-up")
    Single<ResponseBean<String>> signUp(@Body Account request);

    @POST("/reset-password")
    Single<ResponseBean<String>> resetPassword(@Body Account request);

    @POST("/sign-in")
    Single<ResponseBean<SessionToken>> signIn(@Body Account request);

    @POST("/verify-session-token")
    Single<ResponseBean<SessionToken>> verifySessionToken(@Body SessionToken request);
}
