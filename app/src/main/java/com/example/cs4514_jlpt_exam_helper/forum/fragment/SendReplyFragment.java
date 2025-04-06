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
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSendReplyBinding;
import com.example.cs4514_jlpt_exam_helper.forum.viewmodel.ForumViewModel;

public class SendReplyFragment extends Fragment implements View.OnClickListener{
    private FragmentSendReplyBinding binding;
    private ForumViewModel viewModel;
    private Question question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSendReplyBinding.inflate(inflater, container, false);
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
        viewModel.getPostReplyResult().observe(getViewLifecycleOwner(), result -> {
            if(result){
                showToast("Post Question Successfully!");
                binding.etReply.setText("");
                binding.textTitle.setText("");
                viewModel.showReply();
            }else{
                showToast("Failed to post question.");
            }
        });

        viewModel.getSelectedQuestion().observe(getViewLifecycleOwner(), selectedQuestion ->{
            question = selectedQuestion;

            if(question == null){
                Toast.makeText(requireActivity(), "Unknown.", Toast.LENGTH_SHORT).show();
                viewModel.showReply();
                return;
            }

            binding.textTitle.setText(question.getQuestion_title());
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            viewModel.showReply();
        }else if(id == R.id.btn_send){
            showConfirmDialog();
        }
    }

    public void showConfirmDialog() {
        String reply = binding.etReply.getText().toString().trim();

        if(reply.isEmpty()){
            showToast("*Reply required.");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Post");
        builder.setMessage("Are you sure you want to post this reply?\n\n");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            showToast(reply);
            showToast(Integer.toString(question.getQuestion_id()));
            viewModel.postReply(requireActivity(), reply, question.getQuestion_id());

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
