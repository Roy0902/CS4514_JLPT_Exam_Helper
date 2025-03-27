package com.example.cs4514_jlpt_exam_helper.learning.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Vocabulary;
import com.example.cs4514_jlpt_exam_helper.network.response.JishoResponse;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.VocabualryViewHolder> {
    private List<Vocabulary> vocabularyList;
    private Context context;
    private OnPlayClickListener playClickListener;

    public interface OnPlayClickListener {
        void onPlayClick(String reading);
    }

    public VocabularyAdapter(Context context,
                             List<Vocabulary> vocabularyList,
                             OnPlayClickListener listener) {
        this.context = context;
        this.vocabularyList = vocabularyList;
        this.playClickListener = listener;
    }

    @Override
    public VocabualryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vocabulary, parent, false);
        return new VocabualryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VocabualryViewHolder holder, int position) {
        Vocabulary result = vocabularyList.get(position);
        holder.textWord.setText(result.getWord());
        holder.textReading.setText("(" + result.getReading() + ")");
        holder.textMeaning.setText(String.join(", ", result.getMeaning()));

        holder.btnPlay.setOnClickListener(v -> {
            if (playClickListener != null) {
                playClickListener.onPlayClick(result.getReading());
            }
        });

        holder.btnBookmark.setOnClickListener(v -> {
            if (playClickListener != null) {
                playClickListener.onPlayClick(result.getReading());
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabularyList.size();
    }

    public static class VocabualryViewHolder extends RecyclerView.ViewHolder {
        public TextView textWord;
        public TextView textReading;
        public TextView textMeaning;
        public ImageView btnPlay;
        public ImageView btnBookmark;

        public VocabualryViewHolder(View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.text_word);
            textReading = itemView.findViewById(R.id.text_reading);
            textMeaning = itemView.findViewById(R.id.text_meaning);
            btnPlay = itemView.findViewById(R.id.btn_play);
            btnBookmark = itemView.findViewById(R.id.btn_bookmark);
        }
    }

}