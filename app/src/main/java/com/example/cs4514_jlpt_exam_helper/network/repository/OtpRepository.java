package com.example.cs4514_jlpt_exam_helper.network.repository;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Otp;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.network.api.OtpAPI;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OtpRepository {

    private static OtpRepository repository;

    public OtpRepository(){

    }

    public static OtpRepository getInstance(){
        if(repository == null){
            repository = new OtpRepository();
        }

        return repository;
    }

    public Single<ResponseBean<Account>> getSignUpOtp(Account account){
        OtpAPI otpAPI = RetrofitManager.getInstance().getOtpAPI();
        return otpAPI.getSignUpOtp(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<Account>> verifyEmail(Otp otp){
        OtpAPI otpAPI = RetrofitManager.getInstance().getOtpAPI();
        return otpAPI.verifyEmail(otp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<Account>> resendOtp(Account account){
        OtpAPI otpAPI = RetrofitManager.getInstance().getOtpAPI();
        return otpAPI.resendOtp(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<Account>> getResetPasswordOtp(Account account){
        OtpAPI otpAPI = RetrofitManager.getInstance().getOtpAPI();
        return otpAPI.getResetPasswordOtp(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
