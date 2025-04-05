package com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.google.firebase.messaging.FirebaseMessaging;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class DashboardViewModel extends ViewModel {
    private AccountRepository accountRepository;
    private SessionManager manager;

    private MutableLiveData<Boolean> validToken = new MutableLiveData<>();
    private MutableLiveData<String> toastText = new MutableLiveData<>();

    public DashboardViewModel(){
        if(accountRepository == null){
            accountRepository = AccountRepository.getInstance();
        }

        if(manager == null){
            manager = SessionManager.getInstance();
        }
    }

    public MutableLiveData<Boolean> getValidToken() {
        return validToken;
    }

    public void setValidToken(MutableLiveData<Boolean> validToken) {
        this.validToken = validToken;
    }

    public MutableLiveData<String> getToastText() {
        return toastText;
    }

    public void setToastText(MutableLiveData<String> toastText) {
        this.toastText = toastText;
    }

    public void verifySessionToken(Context context){
        Single<ResponseBean<SessionToken>> response = accountRepository.
                verifySessionToken(context);

        if(manager.isNoVerificationNeeded()){
            return;
        }

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

    public void updateFirebaseToken(Context context){

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String token = task.getResult();
                        String session_token = SessionManager.getInstance().getSessionToken(context);
                        saveFirebaseToken(token, session_token);
                    }else{
                        toastText.setValue("Failed to get Firebase Token.");
                    }
                });

    }

    public void saveFirebaseToken(String firebase_token, String session_token){
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

                if (code > 299) {
                    toastText.setValue("Failed to save Firebase Token.");
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void saveSelectedLevel(Context context, String level_name){
        SharedPreferences pref = context.getSharedPreferences(Constant.key_session_pref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Constant.key_selected_current_level, level_name);
        editor.apply();

        toastText.setValue("Changed Level.");
    }
}
