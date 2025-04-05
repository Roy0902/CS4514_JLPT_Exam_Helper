package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.request.ChangePasswordRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.FirebaseTokenRequest;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountAPI {

    @POST("/account/sign-up")
    Single<ResponseBean<String>> signUp(@Body Account request);

    @POST("/account/reset-password")
    Single<ResponseBean<String>> resetPassword(@Body Account request);

    @POST("/account/change-password")
    Single<ResponseBean<String>> changePassword(@Body ChangePasswordRequest request);

    @POST("/account/sign-in")
    Single<ResponseBean<SessionToken>> signIn(@Body Account request);

    @POST("/account/sign-out")
    Single<ResponseBean<String>> signOut(@Body SessionToken request);

    @POST("/account/verify-session-token")
    Single<ResponseBean<SessionToken>> verifySessionToken(@Body SessionToken request);

    @POST("/account/update-firebase-token")
    Single<ResponseBean<String>> updateFirebaseToken(@Body FirebaseTokenRequest request);
}
