package com.example.cs4514_jlpt_exam_helper.onboarding.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cs4514_jlpt_exam_helper.databinding.FragmentOnboardingCommonBinding;

public class OnboardingCommonFragment extends Fragment {
    private FragmentOnboardingCommonBinding binding;
    private String title;
    private String description;
    private int imageRes;

    public static OnboardingCommonFragment newInstance(String title, String description, int imageRes){
        OnboardingCommonFragment fragment = new OnboardingCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putInt("imageResId", imageRes);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageRes = getArguments().getInt("imageResId");
            title = getArguments().getString("title");
            description = getArguments().getString("description");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOnboardingCommonBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        binding.imageOnboarding.setImageResource(imageRes);
        binding.textTitle.setText(title);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

}
