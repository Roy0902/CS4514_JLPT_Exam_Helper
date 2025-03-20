package com.example.cs4514_jlpt_exam_helper.forum.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentAskQuestionBinding;
import com.example.cs4514_jlpt_exam_helper.forum.viewmodel.ForumViewModel;

public class AskQuestionFragment extends Fragment implements View.OnClickListener{
    private FragmentAskQuestionBinding binding;
    private ForumViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAskQuestionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnSend.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getPostQuestionResult().observe(getViewLifecycleOwner(), result -> {
            if(result){
                showToast("Post Question Successfully!");
                binding.etDescription.setText("");
                binding.etTitle.setText("");
                viewModel.showForum();
            }else{
                showToast("Failed to post question.");
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            viewModel.showForum();
        }else if(id == R.id.btn_send){
            showConfirmDialog();
        }
    }

    public void showConfirmDialog() {
        String question_title = binding.etTitle.getText().toString().trim();
        String question_description = binding.etDescription.getText().toString().trim();
        if (question_title.isEmpty()) {
            showToast("*Title required.");
            return;
        }

        if(question_description.isEmpty()){
            showToast("*Body required.");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Post");
        builder.setMessage("Are you sure you want to submit this question?\n\n");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            String session_token = requireActivity().getSharedPreferences(Constant.key_session_pref, MODE_PRIVATE)
                    .getString(Constant.key_session_token, Constant.error_not_found);
            viewModel.postQuestion(session_token, question_title, question_description);

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
