package com.example.cs4514_jlpt_exam_helper.question.activity;

import android.content.Intent;
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
import com.example.cs4514_jlpt_exam_helper.databinding.ActivityQuestionBinding;
import com.example.cs4514_jlpt_exam_helper.question.fragment.AskQuestionFragment;
import com.example.cs4514_jlpt_exam_helper.question.adapter.QuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.question.fragment.ReplyFragment;
import com.example.cs4514_jlpt_exam_helper.question.viewmodel.QuestionViewModel;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener, QuestionAdapter.OnItemClickListener{
    private QuestionViewModel viewModel;
    private ActivityQuestionBinding binding;
    private QuestionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        adapter = new QuestionAdapter(new ArrayList<>(), this);

        setUpEventListener();
        setupViewModelObserver();

        binding.questionContainer.setLayoutManager(new LinearLayoutManager(this));
        binding.questionContainer.setAdapter(adapter);
        viewModel.getQuestion();
    }

    public void setUpEventListener(){
        binding.btnCreateQuestion.setOnClickListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.questionContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 5) {
                    viewModel.getQuestion();
                }
            }
        });
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetQuestionList();
                viewModel.resetPage();
                viewModel.getQuestion();
                binding.swipeRefresh.setRefreshing(false);
            }
        });
    }


    public void setupViewModelObserver(){
        viewModel.getQuestionList().observe(this ,questionList -> {
            adapter.updateQuestionList(viewModel.getQuestionList().getValue());
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

    @Override
    public void onItemClick(Question question){
        Intent intent = new Intent(this, ReplyActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("QUESTION", question);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
