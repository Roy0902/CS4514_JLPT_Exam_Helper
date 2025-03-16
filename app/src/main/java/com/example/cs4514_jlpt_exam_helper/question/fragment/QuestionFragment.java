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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentQuestionBinding;
import com.example.cs4514_jlpt_exam_helper.question.adapter.QuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.question.viewmodel.ForumViewModel;

import java.util.ArrayList;

public class QuestionFragment extends Fragment implements View.OnClickListener, QuestionAdapter.OnItemClickListener{
    private QuestionAdapter adapter;
    private ForumViewModel viewModel;
    private FragmentQuestionBinding binding;
    private boolean isEnd = false;
    private boolean isLoading = false;
    private boolean isFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ForumViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        setUpEventListener();
        setupViewModelObserver();
        adapter = new QuestionAdapter(new ArrayList<>(), this);

        binding.questionContainer.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.questionContainer.setAdapter(adapter);

        viewModel.getQuestion();
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnCreateQuestion.setOnClickListener(this);

        binding.questionContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (!recyclerView.canScrollVertically(1) && !isLoading && !isEnd) {
                    isLoading = true;
                    viewModel.getQuestion();
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
                adapter.resetQuestionList();
                viewModel.resetQuestionPage();
                viewModel.getQuestion();
                binding.swipeRefresh.setRefreshing(false);

                isEnd = false;
                binding.textEnd.setVisibility(View.GONE);
                binding.lineBreak.setVisibility(View.GONE);
            }
        });
    }

    public void setupViewModelObserver(){
        viewModel.getQuestionList().observe(getViewLifecycleOwner(), questionList ->{
            if(questionList.isEmpty() && !isFirstLoad){
                isEnd = true;
                binding.textEnd.setVisibility(View.VISIBLE);
                binding.lineBreak.setVisibility(View.VISIBLE);
            }else{
                isFirstLoad = false;
            }

            isLoading = false;
            adapter.updateQuestionList(questionList);
        });
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            requireActivity().finish();
        }else if(id == R.id.btn_create_question){
            switchToAskQuestionFragment();
        }
    }

    public void switchToAskQuestionFragment(){
        viewModel.showPostQuestion();
    }

    @Override
    public void onItemClick(Question question) {
        viewModel.getSelectedQuestion().setValue(question);
        viewModel.showReply();
    }
}

