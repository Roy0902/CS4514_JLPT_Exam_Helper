package com.example.cs4514_jlpt_exam_helper.sign_up.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentEditProfileBinding;
import com.example.cs4514_jlpt_exam_helper.sign_up.viewmodel.SignUpViewModel;

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentEditProfileBinding binding;
    private SignUpViewModel viewModel;

    public static EditProfileFragment newInstance(){
        return new EditProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
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
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_signUp){
            String userName = binding.etUserName.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPw.getText().toString().trim();
            viewModel.signUp(userName, password, confirmPassword);
        }
    }

    public void initViewModelObserver() {
        viewModel.getValidUserName().observe(requireActivity(), validUserName -> {
            if (!validUserName.isValid()) {
                binding.errorUserName.setVisibility(View.VISIBLE);
                binding.errorUserName.setText(validUserName.getErrorMsg());
            } else {
                binding.errorUserName.setVisibility(View.INVISIBLE);
                binding.errorUserName.setText("");
            }
        });

        viewModel.getValidPassword().observe(requireActivity(), validPassword -> {
            if (!validPassword.isValid()) {
                binding.errorPassword.setVisibility(View.VISIBLE);
                binding.errorPassword.setText(validPassword.getErrorMsg());
            } else {
                binding.errorPassword.setVisibility(View.INVISIBLE);
                binding.errorPassword.setText("");
            }
        });

        viewModel.getValidConfirmPassword().observe(requireActivity(), validConfirmPassword -> {
            if (!validConfirmPassword) {
                binding.errorConfirmPw.setVisibility(View.VISIBLE);
                binding.errorConfirmPw.setText(Constant.confirmPasswordErrorMsg);
            } else {
                binding.errorConfirmPw.setVisibility(View.INVISIBLE);
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
