package com.example.cs4514_jlpt_exam_helper.reset_password.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentResetPasswordBinding;
import com.example.cs4514_jlpt_exam_helper.reset_password.viewmodel.ResetPasswordViewModel;
import com.example.cs4514_jlpt_exam_helper.sign_up.fragment.EditProfileFragment;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener{

    private FragmentResetPasswordBinding binding;
    private ResetPasswordViewModel viewModel;

    public static EditProfileFragment newInstance(){
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ResetPasswordViewModel.class);

        setUpEventListener();
        initViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_reset_password){
            String password = binding.etPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPw.getText().toString().trim();
            viewModel.resetPassword(password, confirmPassword);
        }
    }

    public void initViewModelObserver() {
        viewModel.getValidPassword().observe(requireActivity(), validPassword -> {
            if (!validPassword.isValid()) {
                binding.errorPassword.setText(validPassword.getErrorMsg());
            } else {
                binding.errorPassword.setText("");
            }
        });

        viewModel.getValidConfirmPassword().observe(requireActivity(), validConfirmPassword -> {
            if (!validConfirmPassword) {
                binding.errorConfirmPw.setText(Constant.confirmPasswordErrorMsg);
            } else {
                binding.errorConfirmPw.setText("");
            }
        });

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
