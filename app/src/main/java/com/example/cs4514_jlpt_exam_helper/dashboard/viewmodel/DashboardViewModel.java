package com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class DashboardViewModel extends ViewModel {
    private AccountRepository accountRepository;

    private MutableLiveData<Boolean> validToken = new MutableLiveData<>();
    private MutableLiveData<String> firebaseToken = new MutableLiveData<>();

    public DashboardViewModel(){
        if(accountRepository == null){
            accountRepository = AccountRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getValidToken() {
        return validToken;
    }

    public void setValidToken(MutableLiveData<Boolean> validToken) {
        this.validToken = validToken;
    }

    public MutableLiveData<String> getFirebaseToken() {
        return firebaseToken;
    }

    public void setFirebaseToken(MutableLiveData<String> firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public void verifySessionToken(Context context){
        String token = context.getSharedPreferences(Constant.key_session_pref, Context.MODE_PRIVATE)
                .getString(Constant.key_session_token, Constant.error_not_found);

        if(token.equals(Constant.error_not_found)){
            validToken.setValue(false);
            return;
        }

        Single<ResponseBean<SessionToken>> response = accountRepository.
                verifySessionToken(new SessionToken(token));
        response.subscribe(new SingleObserver<ResponseBean<SessionToken>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<SessionToken> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    validToken.setValue(true);
                }else{
                    validToken.setValue(false);
                    accountRepository.removeSessionToken(context);
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void updateFirebaseToken(String session_token, String firebase_token){
        if(session_token == null || firebase_token == null){
            return;
        }

        Single<ResponseBean<String>> response = accountRepository.
                updateFirebaseToken(session_token, firebase_token);
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) {
                    firebaseToken.setValue(bean.getData());
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });

    }
}
