package com.example.cs4514_jlpt_exam_helper.study_plan.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cs4514_jlpt_exam_helper.GoogleTTSManager;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentStudyPlanItemListBinding;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentStudyPlanListBinding;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.GrammarAdapter;
import com.example.cs4514_jlpt_exam_helper.learning.adapter.VocabularyAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.adapter.StudyPlanAdapter;
import com.example.cs4514_jlpt_exam_helper.study_plan.viewmodel.StudyPlanViewModel;

import java.util.List;

public class StudyPlanItemListFragment extends Fragment implements
        VocabularyAdapter.OnPlayClickListener{
    private FragmentStudyPlanItemListBinding binding;
    private StudyPlanViewModel viewModel;
    private VocabularyAdapter vocabularyAdapter;
    private GrammarAdapter grammarAdapter;

    private MediaPlayer player;
    private GoogleTTSManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStudyPlanItemListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(StudyPlanViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        manager = GoogleTTSManager.getInstance();
        player = new MediaPlayer();

        List<Vocabulary> vocabularyItemList = viewModel.getVocabularyItemList().getValue();
        List<Grammar> grammarItemList = viewModel.getGrammarItemList().getValue();

        if(vocabularyItemList == null || vocabularyItemList.isEmpty()){
            binding.vocabularyRecyclerView.setVisibility(View.GONE);
        }else{
            vocabularyAdapter = new VocabularyAdapter(requireActivity(), vocabularyItemList, this);
            binding.vocabularyRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.vocabularyRecyclerView.setAdapter(vocabularyAdapter);
        }

        if(grammarItemList == null || grammarItemList.isEmpty()){
            binding.grammarRecyclerView.setVisibility(View.GONE);
        }else{
            grammarAdapter = new GrammarAdapter(grammarItemList);
            binding.grammarRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
            binding.grammarRecyclerView.setAdapter(grammarAdapter);
        }
    }

    @Override
    public void onPlayClick(String reading){
        manager.getTTSService(reading, player, requireActivity());
    }

}
