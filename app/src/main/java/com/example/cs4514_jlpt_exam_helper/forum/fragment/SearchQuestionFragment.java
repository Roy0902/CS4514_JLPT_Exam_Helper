package com.example.cs4514_jlpt_exam_helper.forum.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Constant;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentAskQuestionBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentSearchQuestionBinding;
import com.example.cs4514_jlpt_exam_helper.dictionary.adapter.DictionaryAdapter;
import com.example.cs4514_jlpt_exam_helper.forum.adapter.QuestionAdapter;
import com.example.cs4514_jlpt_exam_helper.forum.viewmodel.ForumViewModel;

public class SearchQuestionFragment extends Fragment implements View.OnClickListener, QuestionAdapter.OnItemClickListener{
    private FragmentSearchQuestionBinding binding;
    private ForumViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchQuestionBinding.inflate(inflater, container, false);
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

        binding.etKeyword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    showProgressBar();
                    viewModel.searchQuestion(binding.etKeyword.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });
    }

    public void setupViewModelObserver(){
        viewModel.getFilteredQuestionList().observe(getViewLifecycleOwner(), responseList -> {
            if(responseList != null && !responseList.isEmpty()){
                binding.questionRecyclerView.setVisibility(View.VISIBLE);
                binding.textNoResult.setVisibility(View.GONE);
                binding.questionRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                binding.questionRecyclerView.setAdapter(new QuestionAdapter(responseList, this));
            }else{
                binding.questionRecyclerView.setVisibility(View.GONE);
                binding.textNoResult.setVisibility(View.VISIBLE);
            }

            hideProgressBar();
        });

    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.btn_back){
            viewModel.showForum();
        }
    }

    public void showToast(String text){
        Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(Question question) {
        viewModel.getSelectedQuestion().setValue(question);
        viewModel.showReply();
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }
}
