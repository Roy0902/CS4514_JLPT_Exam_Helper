package com.example.cs4514_jlpt_exam_helper.api.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.api.AccountAPI;
import com.example.cs4514_jlpt_exam_helper.api.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountRepository {

    private static AccountRepository repository;

    public AccountRepository(){

    }

    public static AccountRepository getInstance(){
        if(repository == null){
            repository = new AccountRepository();
        }

        return repository;
    }

    public Single<ResponseBean<String>> signUp(Account account){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.signUp(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> resetPassword(Account account){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.resetPassword(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<SessionToken>> signIn(Account account){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.signIn(account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<SessionToken>> verifySessionToken(SessionToken sessionToken){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.verifySessionToken(sessionToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void saveSessionToken(Context context, String sessionToken){
        context.getSharedPreferences("Session", Context.MODE_PRIVATE).
                edit().putString(Constant.key_session_token, sessionToken).apply();

    }

    public void removeSessionToken(Context context){
        context.getSharedPreferences("Session", Context.MODE_PRIVATE).
                edit().remove(Constant.key_session_token).apply();

    }
}
