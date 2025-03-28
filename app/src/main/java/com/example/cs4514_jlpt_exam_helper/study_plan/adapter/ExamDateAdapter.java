package com.example.cs4514_jlpt_exam_helper.study_plan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.JLPTExamDate;
import com.example.cs4514_jlpt_exam_helper.quiz.data.Question;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class ExamDateAdapter extends RecyclerView.Adapter<ExamDateAdapter.ExamDateViewHolder> {
    private List<JLPTExamDate> examDateList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(JLPTExamDate exam_date);
    }

    public ExamDateAdapter(List<JLPTExamDate> examDateList, OnItemClickListener listener) {
        this.examDateList = examDateList;
        this.listener = listener;
    }

    @Override
    public ExamDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exam_date_option, parent, false);
        return new ExamDateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamDateViewHolder holder, int position) {
        JLPTExamDate exam_date = examDateList.get(position);
        holder.optionExamDate.setText(exam_date.getExam_date().split("T")[0]);

        holder.itemView.setOnClickListener(v ->{
            if(listener != null){
                listener.onItemClick(exam_date);
            }
        });

    }

    @Override
    public int getItemCount() {
        return examDateList.size();
    }

    public static class ExamDateViewHolder extends RecyclerView.ViewHolder {
        public TextView optionExamDate;

        public ExamDateViewHolder(View itemView) {
            super(itemView);
            optionExamDate = itemView.findViewById(R.id.option_exam_date);
        }
    }

}