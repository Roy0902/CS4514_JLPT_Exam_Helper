package com.example.cs4514_jlpt_exam_helper.sign_up.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Otp;
import com.example.cs4514_jlpt_exam_helper.network.bean.ResponseBean;
import com.example.cs4514_jlpt_exam_helper.network.repository.AccountRepository;
import com.example.cs4514_jlpt_exam_helper.network.repository.OtpRepository;
import com.example.cs4514_jlpt_exam_helper.validator.EmailValidator;
import com.example.cs4514_jlpt_exam_helper.validator.PasswordValidator;
import com.example.cs4514_jlpt_exam_helper.validator.UserNameValidator;
import com.example.cs4514_jlpt_exam_helper.validator.ValidationResult;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class SignUpViewModel extends ViewModel {

    private AccountRepository accountRepository;
    private OtpRepository otpRepository;

    private MutableLiveData<ValidationResult> validEmail = new MutableLiveData<>();
    private MutableLiveData<ValidationResult> validOtpCode = new MutableLiveData<>();

    private MutableLiveData<ValidationResult> validUserName = new MutableLiveData<>();
    private MutableLiveData<ValidationResult> validPassword = new MutableLiveData<>();
    private MutableLiveData<Boolean> validConfirmPassword = new MutableLiveData<>();

    private MutableLiveData<Boolean> sendOtpSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> resendOtpSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> verifyEmailSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> signUpSuccess = new MutableLiveData<>();

    private MutableLiveData<Integer> progressIndex = new MutableLiveData<>();

    private MutableLiveData<String> emailForVerify = new MutableLiveData<>();

    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public SignUpViewModel(){
        if(accountRepository == null){
            accountRepository = AccountRepository.getInstance();
        }
        if(otpRepository == null){
            otpRepository = OtpRepository.getInstance();
        }
    }

    public void isLoading(Boolean loading){
        this.loading.setValue(loading);
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<ValidationResult> getValidUserName() {
        return validUserName;
    }

    public void setValidUserName(MutableLiveData<ValidationResult> validUserName) {
        this.validUserName = validUserName;
    }

    public MutableLiveData<ValidationResult> getValidEmail() {
        return validEmail;
    }

    public void setValidEmail(MutableLiveData<ValidationResult> validEmail) {
        this.validEmail = validEmail;
    }

    public MutableLiveData<ValidationResult> getValidPassword() {
        return validPassword;
    }

    public void setValidPassword(MutableLiveData<ValidationResult> validPassword) {
        this.validPassword = validPassword;
    }

    public MutableLiveData<Boolean> getValidConfirmPassword() {
        return validConfirmPassword;
    }

    public void setValidConfirmPassword(MutableLiveData<Boolean> validConfirmPassword) {
        this.validConfirmPassword = validConfirmPassword;
    }

    public MutableLiveData<ValidationResult> getValidOtpCode() {
        return validOtpCode;
    }

    public void setValidOtpCode(MutableLiveData<ValidationResult> validOtpCode) {
        this.validOtpCode = validOtpCode;
    }

    public MutableLiveData<Boolean> getSendOtpSuccess() {
        return sendOtpSuccess;
    }

    public void setSendOtpSuccess(MutableLiveData<Boolean> sendOtpSuccess) {
        this.sendOtpSuccess = sendOtpSuccess;
    }

    public MutableLiveData<Boolean> getResendOtpSuccess() {
        return resendOtpSuccess;
    }

    public void setResendOtpSuccess(MutableLiveData<Boolean> resendOtpSuccess) {
        this.resendOtpSuccess = resendOtpSuccess;
    }

    public MutableLiveData<Boolean> getVerifyEmailSuccess() {
        return verifyEmailSuccess;
    }

    public void setVerifyEmailSuccess(MutableLiveData<Boolean> verifyEmailSuccess) {
        this.verifyEmailSuccess = verifyEmailSuccess;
    }

    public MutableLiveData<Boolean> getSignUpSuccess() {
        return signUpSuccess;
    }

    public void setSignUpSuccess(MutableLiveData<Boolean> signUpSuccess) {
        this.signUpSuccess = signUpSuccess;
    }

    public MutableLiveData<Integer> getProgressIndex() {
        return progressIndex;
    }

    public void setProgressIndex(MutableLiveData<Integer> progressIndex) {
        this.progressIndex = progressIndex;
    }

    public MutableLiveData<String> getEmailForVerify() {
        return emailForVerify;
    }

    public void setEmailForVerify(MutableLiveData<String> emailForVerify) {
        this.emailForVerify = emailForVerify;
    }

    public void getSignUpOtp(String email){
        if(!validEmail(email)){
            return;
        }

        Single<ResponseBean<Account>> response = otpRepository.getSignUpOtp(new Account(email));
        response.subscribe(new SingleObserver<ResponseBean<Account>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<Account> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <=299) { //otp code sent successfully
                    emailForVerify.setValue(bean.getData().getEmail());
                    progressIndex.setValue(1);
                    sendOtpSuccess.setValue(true);
                }else {
                    validEmail.setValue(new ValidationResult(false, bean.getMessage()));
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                d.dispose();
            }
        });
    }

    public void verifyEmail(String otpCode){
        if(otpCode == null || otpCode.isEmpty()){
            validOtpCode.setValue(new ValidationResult(false, "*Required."));
            return;
        }

        Single<ResponseBean<Account>> response = otpRepository.verifyEmail(new Otp(getEmailForVerify().getValue(), otpCode));
        response.subscribe(new SingleObserver<ResponseBean<Account>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<Account> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) { //otp code sent successfully
                    validOtpCode.setValue(new ValidationResult(true, ""));
                    progressIndex.setValue(2);
                    verifyEmailSuccess.setValue(true);
                }else {
                    validOtpCode.setValue(new ValidationResult(false, bean.getMessage()));
                }


                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
                d.dispose();
            }
        });
    }

    public void resendOtp(){
        String email = emailForVerify.getValue();
        Single<ResponseBean<Account>> response = otpRepository.resendOtp(new Account(email));
        response.subscribe(new SingleObserver<ResponseBean<Account>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<Account> bean) {
                int code = bean.getCode();

                if (code >= 200 && code <=299) { //otp code sent successfully
                    resendOtpSuccess.setValue(true);
                    emailForVerify.setValue(bean.getData().getEmail());
                }else{
                    resendOtpSuccess.setValue(false);
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
                d.dispose();
            }
        });

    }

    public void signUp(String userName, String password, String confirmPassword){
        if(!validProfile(userName, password, confirmPassword)){
            return;
        }

        String email = emailForVerify.getValue();
        Single<ResponseBean<String>> response = accountRepository.signUp(new Account(email, userName, password));
        response.subscribe(new SingleObserver<ResponseBean<String>>() {
            Disposable d;

            @Override
            public void onSubscribe(Disposable d) {
                this.d = d;
            }

            @Override
            public void onSuccess(ResponseBean<String> bean) {
                int code = bean.getCode();
                if (code >= 200 && code <=299) { //otp code sent successfully
                    progressIndex.setValue(3);
                    signUpSuccess.setValue(true);
                }else{
                    validUserName.setValue(new ValidationResult(false, bean.getMessage()));
                }

                d.dispose();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
                d.dispose();
            }
        });
    }

    public boolean validEmail(String email){
        validEmail.setValue(new EmailValidator().validate(email));
        return validEmail.getValue().isValid();
    }

    public boolean validProfile(String userName, String password, String confirmPassword){
        validUserName.setValue(new UserNameValidator().validate(userName));
        validPassword.setValue(new PasswordValidator().validate(password));
        validConfirmPassword.setValue((confirmPassword.equals(password)));

        boolean result = validUserName.getValue().isValid() &&
                validPassword.getValue().isValid() && validConfirmPassword.getValue();

        return result;
    }
}