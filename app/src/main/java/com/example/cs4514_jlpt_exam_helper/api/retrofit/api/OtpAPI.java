package com.example.cs4514_jlpt_exam_helper.api.retrofit.api;

import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.data.Otp;
import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OtpAPI {

    @POST("/get-sign-up-otp")
    Single<ResponseBean<Account>> getSignUpOtp(@Body Account request);

    @POST("/resend-otp")
    Single<ResponseBean<Account>> resendOtp(@Body Account request);

    @POST("/get-reset-password-otp")
    Single<ResponseBean<Account>> getResetPasswordOtp(@Body Account request);

    @POST("/verify-email")
    Single<ResponseBean<Account>> verifyEmail(@Body Otp request);
}

