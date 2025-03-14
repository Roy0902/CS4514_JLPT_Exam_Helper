package com.example.cs4514_jlpt_exam_helper.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;

import java.util.List;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {
    private List<JishoResponse> resultList;
    private Context context;
    private OnPlayClickListener playClickListener;

    public interface OnPlayClickListener {
        void onPlayClick(String reading);
    }

    public DictionaryAdapter(Context context, List<JishoResponse> resultList, OnPlayClickListener listener) {
        this.context = context;
        this.resultList = resultList;
        this.playClickListener = listener;
    }

    @Override
    public DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary, parent, false);
        return new DictionaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DictionaryViewHolder holder, int position) {
        JishoResponse result = resultList.get(position);
        holder.wordText.setText(result.getWord());
        holder.readingText.setText("(" + result.getReading() + ")");
        holder.meaningText.setText(String.join(", ", result.getEnglish_definition()));
        holder.partOfSpeechText.setText(String.join(", ", result.getPart_of_speech()));

        holder.playButton.setOnClickListener(v -> {
            if (playClickListener != null) {
                playClickListener.onPlayClick(result.getReading());
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class DictionaryViewHolder extends RecyclerView.ViewHolder {
        public TextView wordText;
        public TextView readingText;
        public TextView meaningText;
        public TextView partOfSpeechText;
        public ImageView playButton;

        public DictionaryViewHolder(View itemView) {
            super(itemView);
            wordText = itemView.findViewById(R.id.word_text);
            readingText = itemView.findViewById(R.id.reading_text);
            meaningText = itemView.findViewById(R.id.meaning_text);
            partOfSpeechText = itemView.findViewById(R.id.part_of_speech_text);
            playButton = itemView.findViewById(R.id.play_button);
        }
    }

}