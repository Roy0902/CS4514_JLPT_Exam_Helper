package com.example.cs4514_jlpt_exam_helper.network.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.FirebaseTokenService;
import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.api.AccountAPI;
import com.example.cs4514_jlpt_exam_helper.network.request.FirebaseTokenRequest;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;

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

    public Single<ResponseBean<String>> updateFirebaseToken (String session_token, String firebase_token){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.updateFirebaseToken(new FirebaseTokenRequest(session_token, firebase_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void saveSessionToken(Context context, String sessionToken){
        context.getSharedPreferences(Constant.key_session_pref, Context.MODE_PRIVATE).
                edit().putString(Constant.key_session_token, sessionToken).apply();

    }

    public void removeSessionToken(Context context){
        context.getSharedPreferences(Constant.key_session_pref, Context.MODE_PRIVATE).
                edit().remove(Constant.key_session_token).apply();

    }
}
