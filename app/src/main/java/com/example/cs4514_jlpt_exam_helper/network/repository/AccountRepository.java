package com.example.cs4514_jlpt_exam_helper.network.repository;

import android.content.Context;

import com.example.cs4514_jlpt_exam_helper.FirebaseTokenService;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.dashboard.activity.DashboardActivity;
import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.api.AccountAPI;
import com.example.cs4514_jlpt_exam_helper.network.request.ChangePasswordRequest;
import com.example.cs4514_jlpt_exam_helper.network.request.FirebaseTokenRequest;
import com.example.cs4514_jlpt_exam_helper.network.retrofit.RetrofitManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountRepository {

    private static AccountRepository repository;
    private SessionManager sessionManager;

    public AccountRepository(){
        if(sessionManager == null){
            sessionManager = SessionManager.getInstance();
        }

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

    public Single<ResponseBean<SessionToken>> verifySessionToken(Context context){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        String sessionToken = sessionManager.getSessionToken(context);

        //return accountAPI.verifySessionToken(new SessionToken(sessionToken)).subscribeOn(Schedulers.io())
        //        .observeOn(AndroidSchedulers.mainThread());

        if (sessionToken.equals(Constant.error_not_found)) {
            return Single.just(new ResponseBean<SessionToken>(400, "No Session Token", null))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        return accountAPI.verifySessionToken(new SessionToken(sessionToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> updateFirebaseToken (String session_token, String firebase_token){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.updateFirebaseToken(new FirebaseTokenRequest(session_token, firebase_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> changePassword (String session_token, String old_password, String new_password){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.changePassword(new ChangePasswordRequest(session_token, old_password, new_password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ResponseBean<String>> signOut (String session_token){
        AccountAPI accountAPI = RetrofitManager.getInstance().getAccountAPI();
        return accountAPI.signOut(new SessionToken(session_token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void saveSessionToken(Context context, String sessionToken){
        sessionManager.updateSessionToken(context, sessionToken);

    }

    public void removeSessionToken(Context context){
        sessionManager.clearSession(context);

    }
}
