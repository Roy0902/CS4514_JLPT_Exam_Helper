package com.example.cs4514_jlpt_exam_helper.question.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.data.Question;
import com.example.cs4514_jlpt_exam_helper.data.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ReplyViewHolder> {
    private List<Reply> replyList;

    public ReplyAdapter(List<Reply> replyList) {
        this.replyList = replyList;
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reply, parent, false);
        return new ReplyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        Reply reply = replyList.get(position);
        holder.textNumber.setText("# " + position);
        holder.textUserName.setText(reply.getUser_name());
        holder.textReply.setText(reply.getReply());
    }

    public void updateReplyList(List<Reply> list){
        replyList.addAll(list);
        notifyDataSetChanged();
    }

    public void resetReplyList(Question question){
        replyList = new ArrayList<>();
        replyList.add(new Reply(question.getQuestion_description(), question.getUser_name()));
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return replyList.size();
    }

    public static class ReplyViewHolder extends RecyclerView.ViewHolder {
        public TextView textNumber;
        public TextView textUserName;
        public TextView textReply;

        public ReplyViewHolder(View itemView) {
            super(itemView);
            textNumber = itemView.findViewById(R.id.text_number);
            textUserName = itemView.findViewById(R.id.text_user_name);
            textReply = itemView.findViewById(R.id.text_reply);
        }
    }
}