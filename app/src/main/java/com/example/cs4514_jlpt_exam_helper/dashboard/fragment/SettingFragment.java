package com.example.cs4514_jlpt_exam_helper.dashboard.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.SessionManager;
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

        setUpEventListener();
    }

    public void setUpEventListener(){
        binding.btnChangePassword.setOnClickListener(this);
        binding.btnSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_reset_password){

        }else if(id == R.id.btn_signOut){
            showConfirmDialog();
        }
    }

    public void showConfirmDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Sign Out");
        builder.setMessage("Are you sure you want to sign out?\n\n");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            String session_token = SessionManager.getSessionToken(requireActivity());
            viewModel.signOut(session_token);

        });

        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
