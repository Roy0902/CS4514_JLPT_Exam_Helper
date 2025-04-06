package com.example.cs4514_jlpt_exam_helper.forum.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {
    private List<Question> questionList;
    private List<Question> filteredQuestionList;
    private OnItemClickListener listener;

    public QuestionAdapter(List<Question> questionList, OnItemClickListener listener) {
        this.questionList = questionList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Question question);
    }


    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.textTitle.setText(question.getQuestion_title());
        holder.textPostedUserName.setText("Posted by : " + question.getUser_name());
        holder.textAnswerCount.setText(question.getReply_number() + " Answers");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick(question);
                }
            }
        });

    }

    public void updateQuestionList(List<Question> list){
        questionList.addAll(list);
        notifyDataSetChanged();
    }

    public void resetQuestionList(){
        questionList = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void search(){
        filteredQuestionList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView textTitle;
        public TextView textPostedUserName;
        public TextView textAnswerCount;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textPostedUserName = itemView.findViewById(R.id.text_posted_user_name);
            textAnswerCount = itemView.findViewById(R.id.text_answer_count);
        }
    }
}