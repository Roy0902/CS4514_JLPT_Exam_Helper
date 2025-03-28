package com.example.cs4514_jlpt_exam_helper.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.network.response.JishoResponse;

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
        holder.textWord.setText(result.getWord().trim());
        holder.textReading.setText("(" + result.getReading().trim() + ")");
        holder.textMeaning.setText(String.join(", ", result.getEnglish_definition().trim()));
        holder.textPartOfSpeech.setText(String.join(", ", result.getPart_of_speech().trim()));

        holder.btnPlay.setOnClickListener(v -> {
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
        public TextView textWord;
        public TextView textReading;
        public TextView textMeaning;
        public TextView textPartOfSpeech;
        public ImageView btnPlay;

        public DictionaryViewHolder(View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.text_word);
            textReading = itemView.findViewById(R.id.text_reading);
            textMeaning = itemView.findViewById(R.id.text_meaning);
            textPartOfSpeech = itemView.findViewById(R.id.text_part_of_speech);
            btnPlay = itemView.findViewById(R.id.btn_play);
        }
    }

}