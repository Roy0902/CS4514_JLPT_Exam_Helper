package com.example.cs4514_jlpt_exam_helper.question.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityReplyBinding;
import com.example.cs4514_jlpt_exam_helper.question.viewmodel.ReplyViewModel;
import com.example.cs4514_jlpt_exam_helper.question.adapter.ReplyAdapter;
import com.example.cs4514_jlpt_exam_helper.question.fragment.AskQuestionFragment;

import java.util.ArrayList;

public class ReplyActivity extends AppCompatActivity implements View.OnClickListener{
    private ReplyViewModel viewModel;
    private ActivityReplyBinding binding;
    private ReplyAdapter adapter;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(ReplyViewModel.class);
        adapter = new ReplyAdapter(new ArrayList<>());

        setUpEventListener();
        setupViewModelObserver();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            question = (Question) bundle.getSerializable("QUESTION");
        }

        binding.replyContainer.setLayoutManager(new LinearLayoutManager(this));
        binding.replyContainer.setAdapter(adapter);
        viewModel.getReply(question.getQuestion_id());
    }

    public void setUpEventListener(){
        binding.btnBack.setOnClickListener(this);
        binding.btnReply.setOnClickListener(this);
        binding.replyContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 5) {
                    viewModel.getReply(question.getQuestion_id());
                }
            }
        });

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetReplyList(question);
                viewModel.resetPage();
                viewModel.getReply(question.getQuestion_id());
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }


    public void setupViewModelObserver(){
        viewModel.getReplyList().observe(this ,questionList -> {
            adapter.updateReplyList(viewModel.getReplyList().getValue());
        });

    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            goBackDashboardPage();
        }else if(id == R.id.btn_create_question){
            loadFragment(new AskQuestionFragment());
        }
    }



    public void goBackDashboardPage(){
        finish();
    }

    private void loadFragment(Fragment fragment) {
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_question, fragment)
                .commit();
    }

    private void loadFragment(Fragment fragment, Question question) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        fragment.setArguments(bundle);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_question, fragment)
                .commit();
    }

}