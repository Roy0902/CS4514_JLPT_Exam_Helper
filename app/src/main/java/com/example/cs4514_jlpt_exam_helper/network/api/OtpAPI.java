package com.example.cs4514_jlpt_exam_helper.network.api;

import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Otp;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OtpAPI {

    @POST("/otp/get-sign-up-otp")
    Single<ResponseBean<Account>> getSignUpOtp(@Body Account request);

    @POST("/otp/resend-otp")
    Single<ResponseBean<Account>> resendOtp(@Body Account request);

    @POST("/otp/get-reset-password-otp")
    Single<ResponseBean<Account>> getResetPasswordOtp(@Body Account request);

    @POST("/otp/verify-email")
    Single<ResponseBean<Account>> verifyEmail(@Body Otp request);
}

