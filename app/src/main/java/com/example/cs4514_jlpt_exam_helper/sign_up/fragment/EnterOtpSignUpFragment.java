package com.example.cs4514_jlpt_exam_helper.sign_up.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentEnterOtpBinding;
import com.example.cs4514_jlpt_exam_helper.sign_up.viewmodel.SignUpViewModel;

public class EnterOtpSignUpFragment extends Fragment implements View.OnClickListener{
    private FragmentEnterOtpBinding binding;
    private SignUpViewModel viewModel;
    private String otpCode;
    private CountDownTimer resendTimer;

    public static EnterEmailSignUpFragment newInstance(){
        return new EnterEmailSignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding =FragmentEnterOtpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);
        binding.textEnterOtpDescription.setText(new StringBuilder().append(
                                        getContext().getString(R.string.enter_otp_description)).
                                        append(" ").
                                        append(viewModel.getEmailForVerify().getValue()).toString());

        setUpEventListener();
        initViewModelObserver();
    }

    public void setUpEventListener(){
        binding.etOtp.requestFocus();
        binding.etOtp.setOtpCompletionListener(s -> {
            otpCode = s;
        });
        binding.btnVerifyEmail.setOnClickListener(this);
        binding.btnResendOtp.setOnClickListener(this);
        startResendTimer();
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_verify_email){
            binding.btnVerifyEmail.setEnabled(false);
            viewModel.isLoading(true);
            viewModel.verifyEmail(otpCode);
        }else if (id == R.id.btn_resend_otp){
            viewModel.isLoading(true);
            viewModel.resendOtp();
            startResendTimer();
        }
    }

    private void startResendTimer() {
        binding.btnResendOtp.setEnabled(false); // Disable button
        binding.btnResendOtp.setTextColor(getResources().getColor(R.color.light_gray));

        resendTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                binding.btnResendOtp.setText("Resend in " + secondsLeft + "s");
            }

            @Override
            public void onFinish() {
                binding.btnResendOtp.setEnabled(true);
                binding.btnResendOtp.setText("Resend OTP");
                binding.btnResendOtp.setTextColor(getResources().getColor(R.color.blue_1));
            }
        }.start();

    }

    public void initViewModelObserver() {
        viewModel.getValidOtpCode().observe(requireActivity(), validOtpCode -> {
            if (!validOtpCode.isValid()) {
                binding.btnVerifyEmail.setEnabled(true);
                binding.errorOtp.setVisibility(View.VISIBLE);
                binding.errorOtp.setText(validOtpCode.getErrorMsg());
            }else{
                binding.errorOtp.setVisibility(View.INVISIBLE);
                binding.errorOtp.setText("");
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
