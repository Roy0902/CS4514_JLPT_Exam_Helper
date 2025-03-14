package com.example.cs4514_jlpt_exam_helper.learning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Grammar;

import java.util.List;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.GrammarViewHolder> {
    private List<Grammar> grammarList;

    public GrammarAdapter(List<Grammar> grammarList) {
        this.grammarList = grammarList;
    }

    @Override
    public GrammarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grammar_flashcard, parent, false);
        return new GrammarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GrammarViewHolder holder, int position) {
        Grammar grammar = grammarList.get(position);
        holder.ruleTextView.setText(grammar.getRule());
        holder.explationTextView.setText(grammar.getExplanation());

    }

    @Override
    public int getItemCount() {
        return grammarList.size();
    }

    public static class GrammarViewHolder extends RecyclerView.ViewHolder {
        public TextView ruleTextView;
        public TextView explationTextView;

        public GrammarViewHolder(View itemView) {
            super(itemView);
            ruleTextView = itemView.findViewById(R.id.text_rule);
            explationTextView = itemView.findViewById(R.id.text_explanation);
        }
    }
}