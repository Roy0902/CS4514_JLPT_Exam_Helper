package com.example.cs4514_jlpt_exam_helper.reset_password.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentActionSuccessBinding;
import com.example.cs4514_jlpt_exam_helper.sign_in.activity.SignInActivity;

public class ResetPasswordSuccessFragment extends Fragment implements View.OnClickListener {
    private FragmentActionSuccessBinding binding;

    public static ResetPasswordSuccessFragment newInstance(){
        return new ResetPasswordSuccessFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentActionSuccessBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUpEventListener();
    }

    public void setUpEventListener(){
        binding.btnSignIn.setOnClickListener(this);
        binding.textActionTitle.setText(R.string.reset_password_successfully_title);
        binding.textActionDescription.setText(R.string.reset_password_successfully_description);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_signIn){
            goSignInPage();
        }
    }

    public void goSignInPage(){
        Intent intent = new Intent(requireActivity(), SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
