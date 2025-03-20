package com.example.cs4514_jlpt_exam_helper.forum.activity;

import android.os.Bundle;
import android.util.Log;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityForumBinding;
import com.example.cs4514_jlpt_exam_helper.forum.fragment.AskQuestionFragment;
import com.example.cs4514_jlpt_exam_helper.forum.fragment.QuestionFragment;
import com.example.cs4514_jlpt_exam_helper.forum.fragment.ReplyFragment;
import com.example.cs4514_jlpt_exam_helper.forum.fragment.SendReplyFragment;
import com.example.cs4514_jlpt_exam_helper.forum.viewmodel.ForumViewModel;


public class ForumActivity extends FragmentActivity {
    private ForumViewModel viewModel;
    private ActivityForumBinding binding;
    private QuestionFragment questionFragment;
    private AskQuestionFragment askQuestionFragment;
    private ReplyFragment replyFragment;
    private SendReplyFragment sendReplyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ForumViewModel.class);
        questionFragment = new QuestionFragment();
        askQuestionFragment = new AskQuestionFragment();
        replyFragment = new ReplyFragment();
        sendReplyFragment = new SendReplyFragment();

        setupViewModelObserver();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_question, questionFragment, "QUESTION");
            transaction.add(R.id.fragment_question, askQuestionFragment, "POST_QUESTION");
            transaction.add(R.id.fragment_question, replyFragment, "REPLY");
            transaction.add(R.id.fragment_question, sendReplyFragment, "POST_REPLY");
            transaction.hide(askQuestionFragment);
            transaction.hide(replyFragment);
            transaction.commit();
            viewModel.showForum();
        }
    }

    public void setupViewModelObserver(){
        viewModel.getCurrentScreen().observe(this, currentScreen -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (currentScreen == null || currentScreen.equals("QUESTION")) {
                transaction.show(questionFragment).hide(askQuestionFragment).hide(replyFragment).hide(sendReplyFragment);
            } else if (currentScreen.equals("POST_QUESTION")) {
                transaction.show(askQuestionFragment).hide(replyFragment).hide(questionFragment).hide(sendReplyFragment);
            }else if (currentScreen.equals("REPLY")) {
                transaction.show(replyFragment).hide(askQuestionFragment).hide(questionFragment).hide(sendReplyFragment);
            }else if (currentScreen.equals("POST_REPLY")) {
                transaction.show(sendReplyFragment).hide(askQuestionFragment).hide(questionFragment).hide(replyFragment);
            }

            try {
                transaction.commit();
            } catch (IllegalStateException e) {
                Log.e("ForumActivity", "Fragment transaction failed", e);
            }
        });
    }
}
