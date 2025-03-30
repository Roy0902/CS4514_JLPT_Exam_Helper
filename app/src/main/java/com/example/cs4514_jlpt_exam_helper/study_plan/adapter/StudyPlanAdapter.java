package com.example.cs4514_jlpt_exam_helper.study_plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.StudyPlanItem;

import java.util.List;

public class StudyPlanAdapter extends RecyclerView.Adapter<StudyPlanAdapter.StudyPlanViewHolder> {
    private List<StudyPlanItem> studyPlanList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(StudyPlanItem studyPlan);
    }

    public StudyPlanAdapter(List<StudyPlanItem> studyPlanList, Context context, OnItemClickListener listener) {
        this.studyPlanList = studyPlanList;
        this.context = context;
        this.listener =listener;
    }

    @NonNull
    @Override
    public StudyPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_study_plan, parent, false);
        return new StudyPlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyPlanViewHolder holder, int position) {
        StudyPlanItem studyPlan = studyPlanList.get(position);


        holder.textStudyPlanNumber.setText("Day " + position);

        if(studyPlan.isIs_completed()){
            holder.iconCompleted.setVisibility(View.VISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick(studyPlan);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return studyPlanList.size();
    }

    public static class StudyPlanViewHolder extends RecyclerView.ViewHolder {
        ImageView iconStatus;
        ImageView iconCompleted;
        TextView textStudyPlanNumber;
        TextView textStudyPlanName;

        StudyPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            iconStatus = itemView.findViewById(R.id.icon_status);
            iconCompleted = itemView.findViewById(R.id.icon_completed);
            textStudyPlanNumber = itemView.findViewById(R.id.text_study_plan_number);
            textStudyPlanName = itemView.findViewById(R.id.text_study_plan_name);
        }
    }
}