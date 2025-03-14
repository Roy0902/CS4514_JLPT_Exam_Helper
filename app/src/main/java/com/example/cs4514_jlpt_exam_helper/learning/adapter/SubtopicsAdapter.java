package com.example.cs4514_jlpt_exam_helper.learning.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Category;
import com.example.cs4514_jlpt_exam_helper.data.Subtopic;

import java.util.List;

public class SubtopicsAdapter extends RecyclerView.Adapter<SubtopicsAdapter.SubtopicViewHolder> {
    private List<Subtopic> subtopics;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(Subtopic subtopic);
    }

    public SubtopicsAdapter(List<Subtopic> subtopics, Context context, OnItemClickListener listener) {
        this.subtopics = subtopics;
        this.context = context;
        this.listener =listener;
    }

    @NonNull
    @Override
    public SubtopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subtopic, parent, false);
        return new SubtopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtopicViewHolder holder, int position) {
        Subtopic subtopic = subtopics.get(position);



        holder.subtopicNumberTextView.setText("Lesson ");
        holder.subtopicNanmeTextView.setText(subtopic.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.onItemClick(subtopic);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subtopics.size();
    }

    public static class SubtopicViewHolder extends RecyclerView.ViewHolder {
        ImageView iconStatusImageView;
        TextView subtopicNumberTextView;
        TextView subtopicNanmeTextView;

        SubtopicViewHolder(@NonNull View itemView) {
            super(itemView);
            iconStatusImageView = itemView.findViewById(R.id.icon_status);
            subtopicNumberTextView = itemView.findViewById(R.id.text_subtopic_number);
            subtopicNanmeTextView = itemView.findViewById(R.id.text_subtopic_name);
        }
    }
}