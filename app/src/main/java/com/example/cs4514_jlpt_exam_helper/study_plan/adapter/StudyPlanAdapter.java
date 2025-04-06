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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudyPlanAdapter extends RecyclerView.Adapter<StudyPlanAdapter.StudyPlanViewHolder> {
    private List<StudyPlanItem> studyPlanList;
    private List<StudyPlanItem> originalStudyPlanList;
    private Map<StudyPlanItem, Integer> initialStudyPlanList;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(StudyPlanItem studyPlan, int position);
    }

    public StudyPlanAdapter(List<StudyPlanItem> studyPlanList, Context context, OnItemClickListener listener) {
        this.originalStudyPlanList = new ArrayList<>(studyPlanList);
        this.studyPlanList = new ArrayList<>(studyPlanList);
        this.context = context;
        this.listener = listener;

        this.initialStudyPlanList = new HashMap<>();
        for (int i = 0; i < originalStudyPlanList.size(); i++) {
            initialStudyPlanList.put(originalStudyPlanList.get(i), i);
        }
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

        int displayPosition = initialStudyPlanList.get(studyPlan) + 1;
        holder.textStudyPlanNumber.setText("Day " + displayPosition);

        if(studyPlan.isIs_completed() > 0){
            holder.iconCompleted.setVisibility(View.VISIBLE);
        }else{
            holder.iconCompleted.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick(studyPlan, position);
                }
            }
        });
    }

    public void sort(){
        studyPlanList.sort(Comparator.comparing(StudyPlanItem::isIs_completed));
        notifyDataSetChanged();
    }

    public void reset(){
        studyPlanList.sort(Comparator.comparing(StudyPlanItem::getStudy_plan_item_id));
        notifyDataSetChanged();
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