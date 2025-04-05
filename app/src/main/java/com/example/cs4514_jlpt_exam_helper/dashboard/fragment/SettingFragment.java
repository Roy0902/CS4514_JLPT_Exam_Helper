package com.example.cs4514_jlpt_exam_helper.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
import com.example.cs4514_jlpt_exam_helper.SplashActivity;
import com.example.cs4514_jlpt_exam_helper.UserEntryActivity;
import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.SettingViewModel;
import com.example.cs4514_jlpt_exam_helper.data.Account;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuizBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSettingBinding;
import com.example.cs4514_jlpt_exam_helper.quiz.adapter.QuizQuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.quiz.viewmodel.QuizViewModel;

import java.util.ArrayList;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private SettingViewModel viewModel;
    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setUpViewModelObserver();
        setUpEventListener();
    }

    public void setUpEventListener(){
        binding.btnChangePassword.setOnClickListener(this);
        binding.btnSignOut.setOnClickListener(this);
    }

    public void setUpViewModelObserver(){
        viewModel.getValidOldPassword().observe(getViewLifecycleOwner(), validOldPassword -> {
            if (!validOldPassword.isValid()) {
                binding.errorOldPassword.setVisibility(View.VISIBLE);
                binding.errorOldPassword.setText(validOldPassword.getErrorMsg());
            } else {
                binding.errorOldPassword.setVisibility(View.INVISIBLE);
                binding.errorOldPassword.setText("");
            }
        });

        viewModel.getValidNewPassword().observe(getViewLifecycleOwner(), validNewPassword -> {
            if (!validNewPassword.isValid()) {
                binding.errorNewPassword.setVisibility(View.VISIBLE);
                binding.errorNewPassword.setText(validNewPassword.getErrorMsg());
            } else {
                binding.errorNewPassword.setVisibility(View.INVISIBLE);
                binding.errorNewPassword.setText("");
            }
        });

        viewModel.getValidConfirmNewPassword().observe(getViewLifecycleOwner(), validConfirmNewPassword -> {
            if (!validConfirmNewPassword) {
                binding.errorConfirmNewPw.setVisibility(View.VISIBLE);
                binding.errorConfirmNewPw.setText(Constant.confirmPasswordErrorMsg);
            } else {
                binding.errorConfirmNewPw.setVisibility(View.INVISIBLE);
                binding.errorConfirmNewPw.setText("");
            }
        });

        viewModel.getSignOutSuccess().observe(getViewLifecycleOwner(), isSuccess->{
            if(isSuccess){
                showToast("Sign Out Successfully!");

                Intent intent = new Intent(requireActivity(), UserEntryActivity.class);
                startActivity(intent);
            }
        });

        viewModel.getChangePasswordSuccess().observe(getViewLifecycleOwner(), isSuccess->{
            if(isSuccess){
                showToast("Password is changed successfully.");
                clearInput();
            }
        });

        viewModel.getChangePasswordFailedMessage().observe(getViewLifecycleOwner(), message->{
            binding.textChangePasswordDescription.setVisibility(View.GONE);
            binding.errorChangePasswordFail.setVisibility(View.VISIBLE);
            binding.errorChangePasswordFail.setText(message);
            clearInput();
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_change_password){
            changePassword();
        }else if(id == R.id.btn_signOut){
            showConfirmDialog();
        }
    }

    public void changePassword(){
        binding.errorChangePasswordFail.setVisibility(View.GONE);
        binding.textChangePasswordDescription.setVisibility(View.VISIBLE);
        String oldPassword = binding.etOldPassword.getText().toString().trim();
        String newPassword = binding.etNewPassword.getText().toString().trim();
        String confirmNewPassword = binding.etConfirmNewPw.getText().toString().trim();

        viewModel.changePassword(requireActivity(), oldPassword, newPassword, confirmNewPassword);
    }

    public void clearInput(){
        binding.etOldPassword.setText("");
        binding.etNewPassword.setText("");
        binding.etConfirmNewPw.setText("");
    }

    public void showConfirmDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?\n\n");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            viewModel.signOut(requireActivity());

        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showToast(String text){
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show();
    }


}
