package com.example.cs4514_jlpt_exam_helper.learning.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.data.JapaneseCharacter;

import java.util.List;

public class JapaneseCharacterAdapter extends RecyclerView.Adapter<JapaneseCharacterAdapter.JapaneseViewHolder> {
    private List<JapaneseCharacter> japaneseCharacterList;
    private OnItemClickListener listener;

    public JapaneseCharacterAdapter(List<JapaneseCharacter> japaneseCharacterList, OnItemClickListener listener) {
        this.japaneseCharacterList = japaneseCharacterList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(JapaneseCharacter japaneseCharacter);
    }


    @Override
    public JapaneseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_character_flashcard, parent, false);
        return new JapaneseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JapaneseViewHolder holder, int position) {
        JapaneseCharacter japaneseCharacter = japaneseCharacterList.get(position);
        holder.hiraganaTextView.setText(japaneseCharacter.getJapanese_character());
        holder.pronunicationTextView.setText(japaneseCharacter.getPronunciation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick(japaneseCharacter);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return japaneseCharacterList.size();
    }

    public static class JapaneseViewHolder extends RecyclerView.ViewHolder {
        public TextView hiraganaTextView;
        public TextView pronunicationTextView;

        public JapaneseViewHolder(View itemView) {
            super(itemView);
            hiraganaTextView = itemView.findViewById(R.id.text_hiragana);
            pronunicationTextView = itemView.findViewById(R.id.text_pronunciation);
        }
    }
}