package com.example.cs4514_jlpt_exam_helper.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentReplyBinding;
import com.example.cs4514_jlpt_exam_helper.forum.adapter.ReplyAdapter;
import com.example.cs4514_jlpt_exam_helper.forum.viewmodel.ForumViewModel;

import java.util.ArrayList;

public class ReplyFragment extends Fragment implements View.OnClickListener{
    private ReplyAdapter adapter;
    private ForumViewModel viewModel;
    private FragmentReplyBinding binding;
    private Question question;
    private boolean isEnd = false;
    private boolean isLoading = false;
    private boolean isFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReplyBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);
        adapter = new ReplyAdapter(new ArrayList<>());

        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnReply.setOnClickListener(this);

        binding.replyContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (!recyclerView.canScrollVertically(1) && !isLoading && !isEnd && !isFirstLoad) {
                    isLoading = true;
                    viewModel.getReply(question.getQuestion_id());
                }else if(recyclerView.canScrollVertically(-1) && isEnd){
                    binding.textEnd.setVisibility(View.GONE);
                    binding.lineBreak.setVisibility(View.GONE);
                }else if(!recyclerView.canScrollVertically(1) && isEnd){
                    binding.textEnd.setVisibility(View.VISIBLE);
                    binding.lineBreak.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetReplyList(question);
                viewModel.resetReplyPage();
                viewModel.getReply(question.getQuestion_id());
                binding.swipeRefresh.setRefreshing(false);

                isEnd = true;
                binding.textEnd.setVisibility(View.GONE);
                binding.lineBreak.setVisibility(View.GONE);
            }
        });
    }

    public void setupViewModelObserver(){
        viewModel.getReplyList().observe(getViewLifecycleOwner() ,replyList -> {
            if(replyList.isEmpty() && !isFirstLoad){
                isEnd = true;
                binding.textEnd.setVisibility(View.VISIBLE);
                binding.lineBreak.setVisibility(View.VISIBLE);
            }

            isLoading = false;
            isFirstLoad = false;
            adapter.updateReplyList(replyList);
        });

        viewModel.getSelectedQuestion().observe(getViewLifecycleOwner(), selectedQuestion ->{
            question = selectedQuestion;

            if(question == null){
                Toast.makeText(requireActivity(), "Question not found.", Toast.LENGTH_SHORT).show();
                viewModel.showForum();
                return;
            }

            binding.textQuestionTitle.setText(question.getQuestion_title());

            binding.replyContainer.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.replyContainer.setAdapter(adapter);
            adapter.resetReplyList(question);

            isLoading = true;
            viewModel.getReply(question.getQuestion_id());
        });

        viewModel.getPostReplyResult().observe(getViewLifecycleOwner(), result ->{
            if(result){
                viewModel.getReply(question.getQuestion_id());
            }
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            viewModel.showForum();
        }else if(id == R.id.btn_reply){
            switchToPostReplyFragment();
        }
    }

    public void switchToPostReplyFragment(){
        viewModel.showPostReply();
    }

}

