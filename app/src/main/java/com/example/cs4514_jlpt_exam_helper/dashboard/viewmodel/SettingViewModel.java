package com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
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

    private MutableLiveData<Boolean> signOutSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> changePasswordSuccess = new MutableLiveData<>();
    private MutableLiveData<String> changePasswordFailedMessage = new MutableLiveData<>();

    public SettingViewModel(){
        if(repository == null){
            repository = AccountRepository.getInstance();
        }
    }

    public MutableLiveData<Boolean> getSignOutSuccess() {
        return signOutSuccess;
    }

    public void setSignOutSuccess(MutableLiveData<Boolean> signOutSuccess) {
        this.signOutSuccess = signOutSuccess;
    }

    public MutableLiveData<ValidationResult> getValidOldPassword() {
        return validOldPassword;
    }

    public void setValidOldPassword(MutableLiveData<ValidationResult> validOldPassword) {
        this.validOldPassword = validOldPassword;
    }

    public MutableLiveData<ValidationResult> getValidNewPassword() {
        return validNewPassword;
    }

    public void setValidNewPassword(MutableLiveData<ValidationResult> validNewPassword) {
        this.validNewPassword = validNewPassword;
    }

    public MutableLiveData<Boolean> getValidConfirmNewPassword() {
        return validConfirmNewPassword;
    }

    public void setValidConfirmNewPassword(MutableLiveData<Boolean> validConfirmNewPassword) {
        this.validConfirmNewPassword = validConfirmNewPassword;
    }

    public MutableLiveData<Boolean> getChangePasswordSuccess() {
        return changePasswordSuccess;
    }

    public void setChangePasswordSuccess(MutableLiveData<Boolean> changePasswordSuccess) {
        this.changePasswordSuccess = changePasswordSuccess;
    }

    public MutableLiveData<String> getChangePasswordFailedMessage() {
        return changePasswordFailedMessage;
    }

    public void setChangePasswordFailedMessage(MutableLiveData<String> changePasswordFailedMessage) {
        this.changePasswordFailedMessage = changePasswordFailedMessage;
    }

    public void changePassword(Context context, String oldPassword, String newPassword, String confirmNewPassword) {
        if (oldPassword == null || newPassword == null || confirmNewPassword == null) {
            return;
        }else if(!validPassword(oldPassword, newPassword, confirmNewPassword)){
            return;
        }


        Single<ResponseBean<String>> response = repository.changePassword(context, oldPassword, newPassword);
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
                    changePasswordSuccess.setValue(true);
                }else{
                    changePasswordFailedMessage.setValue(bean.getMessage());
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });

    }

    public boolean validPassword(String oldPassword, String newPassword, String confirmNewPassword){
        validOldPassword.setValue(new PasswordValidator().validate(oldPassword));
        validNewPassword.setValue(new PasswordValidator().validate(newPassword));
        validConfirmNewPassword.setValue((confirmNewPassword.equals(newPassword)));

        boolean result = validOldPassword.getValue().isValid() && validNewPassword.getValue().isValid()
                         && validConfirmNewPassword.getValue();

        return result;
    }

    public void signOut(Context context){
        Single<ResponseBean<String>> response = repository.signOut(context);
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
                    repository.removeSessionToken(context);
                    signOutSuccess.setValue(true);
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
