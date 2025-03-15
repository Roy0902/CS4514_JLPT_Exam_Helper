package com.example.cs4514_jlpt_exam_helper.question.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentReplyBinding;
import com.example.cs4514_jlpt_exam_helper.question.viewmodel.QuestionViewModel;
import com.example.cs4514_jlpt_exam_helper.question.adapter.ReplyAdapter;


public class ReplyFragment extends Fragment implements View.OnClickListener{
    private FragmentReplyBinding binding;
    private QuestionViewModel viewModel;
    private Question question;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReplyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(QuestionViewModel.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            question = (Question) bundle.getSerializable("QUESTION");
        }

        showToast("1234.");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        setUpEventListener();
        setupViewModelObserver();

        if(question == null){
            showToast("Question does not exist.");
            backPage();
        }

        System.out.println("Question " + question);
        viewModel.getReply(question.getQuestion_id());
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnReply.setOnClickListener(this);
    }

    public void setupViewModelObserver(){
        viewModel.getReplyList().observe(requireActivity(), replyList ->{
            if(question != null)
                replyList.add(0, new Reply(question.getQuestion_description(), question.getUser_name()));
            binding.replyContainer.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.replyContainer.setAdapter(new ReplyAdapter(replyList));
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            backPage();
        }
    }

    public void backPage(){
        getParentFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
    }

    public void showToast(String text){
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show();
    }
}

