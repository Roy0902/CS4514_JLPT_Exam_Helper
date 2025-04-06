package com.example.cs4514_jlpt_exam_helper.sign_up.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentEnterEmailBinding;
import com.example.cs4514_jlpt_exam_helper.sign_in.activity.SignInActivity;
import com.example.cs4514_jlpt_exam_helper.sign_up.viewmodel.SignUpViewModel;

public class EnterEmailSignUpFragment extends Fragment implements View.OnClickListener {
    private FragmentEnterEmailBinding binding;
    private SignUpViewModel viewModel;

    public static EnterEmailSignUpFragment newInstance(){
        return new EnterEmailSignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEnterEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SignUpViewModel.class);

        setUpEventListener();
        initViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnSendCode.setOnClickListener(this);
        binding.sectionSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_sendCode){
            binding.btnSendCode.setEnabled(false);
            viewModel.isLoading(true);
            viewModel.getSignUpOtp(binding.etEmail.getText().toString().trim());
        }else if(id == R.id.section_signIn){
            goSignInPage();
        }
    }

    public void goSignInPage(){
        Intent intent = new Intent(requireActivity(), SignInActivity.class);
        startActivity(intent);
    }

    public void initViewModelObserver() {
        viewModel.getValidEmail().observe(requireActivity(), validEmail -> {
            if (!validEmail.isValid()) {
                viewModel.isLoading(false);
                binding.errorEmail.setVisibility(View.VISIBLE);
                binding.errorEmail.setText(validEmail.getErrorMsg());
                binding.btnSendCode.setEnabled(true);
            } else {
                binding.errorEmail.setVisibility(View.INVISIBLE);
                binding.errorEmail.setText("");
            }

            binding.btnSendCode.setEnabled(true);
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
