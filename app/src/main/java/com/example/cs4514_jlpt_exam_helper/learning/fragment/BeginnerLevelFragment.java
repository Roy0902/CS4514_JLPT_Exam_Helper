package com.example.cs4514_jlpt_exam_helper.learning.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cs4514_jlpt_exam_helper.R;
import com.example.cs4514_jlpt_exam_helper.api.data.Constant;
import com.example.cs4514_jlpt_exam_helper.dashboard.viewmodel.DashboardViewModel;
import com.example.cs4514_jlpt_exam_helper.databinding.FragmentBeginnerLevelBinding;
import com.example.cs4514_jlpt_exam_helper.learning.viewmodel.LearningDashboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class BeginnerLevelFragment extends Fragment implements View.OnClickListener{
    private FragmentBeginnerLevelBinding binding;
    private LearningDashboardViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBeginnerLevelBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireParentFragment()).get(LearningDashboardViewModel.class);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences pref = requireActivity().getSharedPreferences("Session", MODE_PRIVATE);
        String sessionToken = pref.getString(Constant.key_session_token, Constant.error_not_found);

        viewModel.getUserProgress(Constant.level_beginner, sessionToken);
        setUpEventListener();
        setupViewModelObserver();
    }

    public void setUpEventListener(){

    }

    public void setupViewModelObserver(){

    }


    @Override
    public void onClick(View v){
        int id = v.getId();
    }

    private static class Topic {

    }

    private class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
        private List<LearningDashboardViewModel.Topic> topics;

        TopicAdapter(List<LearningDashboardViewModel.Topic> topics) {
            this.topics = topics;
        }

        @NonNull
        @Override
        public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_topic, parent, false);
            return new TopicViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
            LearningDashboardViewModel.Topic topic = topics.get(position);
            holder.titleTextView.setText(topic.title);
            holder.imageView.setImageResource(topic.imageResId);
            holder.progressBar.setProgress(topic.progress);

            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("level", topic.level);
                Navigation.findNavController(v).navigate(R.id.action_home_to_beginner, bundle);
            });
        }

        @Override
        public int getItemCount() {
            return topics.size();
        }

        class TopicViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView titleTextView;
            ProgressBar progressBar;

            TopicViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.topicImage);
                titleTextView = itemView.findViewById(R.id.topicTitle);
                progressBar = itemView.findViewById(R.id.topicProgress);
            }
        }
}
