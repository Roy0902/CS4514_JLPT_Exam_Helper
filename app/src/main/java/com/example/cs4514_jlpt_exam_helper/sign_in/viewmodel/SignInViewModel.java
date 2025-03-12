package com.example.cs4514_jlpt_exam_helper.sign_in.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.api.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.api.data.Account;
import com.example.cs4514_jlpt_exam_helper.api.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.api.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SignInViewModel extends ViewModel{

    private AccountRepository repository;

    private MutableLiveData<Boolean> emptyEmail = new MutableLiveData<>();
    private MutableLiveData<Boolean> emptyPw = new MutableLiveData<>();
    private MutableLiveData<Boolean> signInSuccess = new MutableLiveData<>();
    private MutableLiveData<ValidationResult> signInFailed = new MutableLiveData<>();

    public SignInViewModel(){
        if(repository == null){
            repository = AccountRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getEmptyEmail() {
        return emptyEmail;
    }

    public void setEmptyEmail(MutableLiveData<Boolean> emptyEmail) {
        this.emptyEmail = emptyEmail;
    }

    public MutableLiveData<Boolean> getEmptyPw() {
        return emptyPw;
    }

    public void setEmptyPw(MutableLiveData<Boolean> emptyPw) {
        this.emptyPw = emptyPw;
    }

    public MutableLiveData<Boolean> getSignInSuccess() {
        return signInSuccess;
    }

    public void setSignInSuccess(MutableLiveData<Boolean> signInSuccess) {
        this.signInSuccess = signInSuccess;
    }

    public MutableLiveData<ValidationResult> getSignInFailed() {
        return signInFailed;
    }

    public void setSignInFailed(MutableLiveData<ValidationResult> signInFailed) {
        this.signInFailed = signInFailed;
    }

    public void signIn(String email, String pw, Context context){
        if(!valid(email, pw)){
            return;
        }


       Single<ResponseBean<SessionToken>> response =  repository.signIn(new Account(email, pw));
       response.subscribe(new SingleObserver<ResponseBean<SessionToken>>() {
            private Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<SessionToken> responseBean) {
                int code = responseBean.getCode();
                if(code >= 200 && code <=299){
                    repository.saveSessionToken(context, responseBean.getData().getToken());
                    signInSuccess.setValue(true);
                }else{
                    signInFailed.setValue(new ValidationResult(false, responseBean.getMessage()));
                }
                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public boolean valid(String email, String pw){
        emptyEmail.setValue(isEmpty(email));
        emptyPw.setValue(isEmpty(pw));


        return !(emptyEmail.getValue() || emptyPw.getValue());
    }

    private boolean isEmpty(String string) {
        if (!string.isEmpty())
            return false;

        return true;
    }

}
