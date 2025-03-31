package com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.data.SessionToken;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.validator.PasswordValidator;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SettingViewModel extends ViewModel {
    private AccountRepository repository;

    private MutableLiveData<ValidationResult> validOldPassword = new MutableLiveData<>();
    private MutableLiveData<ValidationResult> validNewPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> validConfirmNewPassword = new MutableLiveData<>();

    public SettingViewModel(){
        if(repository == null){
            repository = AccountRepository.getInstance();
        }
    }

    public void changePassword(String session_token, String oldPassword, String newPassword, String confirmNewPassword) {
        if (session_token == null || oldPassword == null ||
            newPassword == null || confirmNewPassword == null) {
            return;
        }

    }

    public boolean validPassword(String oldPassword, String newPassword, String confirmNewPassword){
        validOldPassword.setValue(new PasswordValidator().validate(oldPassword));
        validNewPassword.setValue(new PasswordValidator().validate(newPassword));
        validConfirmNewPassword.setValue((confirmNewPassword.equals(newPassword)));

        boolean result = validOldPassword.getValue().isValid() && validNewPassword.getValue().isValid()
                         && validConfirmNewPassword.getValue();

        return result;
    }

    public void signOut(String session_token){
        if(session_token == null){
            return;
        }

        Single<ResponseBean<String>> response = res
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
}
