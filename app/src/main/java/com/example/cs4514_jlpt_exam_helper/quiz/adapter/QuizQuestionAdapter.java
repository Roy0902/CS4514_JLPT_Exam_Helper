package com.example.cs4514_jlpt_exam_helper.quiz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestionAdapter extends RecyclerView.Adapter<QuizQuestionAdapter.QuizQuestionViewHolder> {
    private List<String> optionList;

    private int selectedPosition;
    private String correctAnswer;

    private boolean isAnswerChecked;
    private OnPlayClickListener playClickListener;

    public interface OnPlayClickListener {
        void onPlayClick(String reading);
    }

    public QuizQuestionAdapter(OnPlayClickListener listener) {
        optionList = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            optionList.add("");
        }

        selectedPosition = -1;
        this.playClickListener = listener;
    }

    @Override
    public QuizQuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_option, parent, false);
        return new QuizQuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizQuestionViewHolder holder, int position) {
        String option = optionList.get(position);
        holder.textOption.setText(option);

        if(isAnswerChecked){
            if(option.equals(correctAnswer)){
                holder.itemView.setBackgroundResource(R.drawable.background_quiz_option_correct);
            }else if(selectedPosition == position){
                holder.itemView.setBackgroundResource(R.drawable.background_quiz_option_wrong);
            }
        }else if(selectedPosition == position){
            holder.itemView.setBackgroundResource(R.drawable.background_quiz_option_selected);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.background_quiz_option);
        }

        holder.btnPlay.setOnClickListener(v -> {
            if (playClickListener != null) {
                playClickListener.onPlayClick(option);
            }
        });

        holder.itemView.setOnClickListener(v ->{
            if (isAnswerChecked) {
                return;
            }

            int previousPosition = selectedPosition;
            selectedPosition = holder.getBindingAdapterPosition();
            if (previousPosition != -1){
                notifyItemChanged(previousPosition);
            }

            notifyItemChanged(selectedPosition);
        });

    }

    public boolean checkAnswer() {
        if (selectedPosition == -1) {
            return false;
        }
        isAnswerChecked = true;
        notifyDataSetChanged();
        return optionList.get(selectedPosition).equals(correctAnswer);
    }

    public void changeQuestion(String correctAnswer, List<String> optionList){
        this.correctAnswer = correctAnswer;
        this.optionList = optionList;
        selectedPosition = -1;
        isAnswerChecked = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public static class QuizQuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView textOption;
        public ImageView btnPlay;

        public QuizQuestionViewHolder(View itemView) {
            super(itemView);
            textOption = itemView.findViewById(R.id.text_option);
            btnPlay = itemView.findViewById(R.id.btn_play);
        }
    }

}