package com.example.cs4514_jlpt_exam_helper.onboarding.adapter;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cs4514_jlpt_exam_helper.onboarding.fragment.OnboardingCommonFragment;


public class OnboardingViewPagerAdapter extends FragmentStateAdapter {
    private final Context context;
    private final int[] imageResIDs;
    private final int[] titleIDs;
    private final int[] descriptionsIDs;

    public OnboardingViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,
                                      int[] imageResIDs, int[] titles, int[] descriptions) {
        super(fragmentActivity);
        this.context = fragmentActivity;
        this.imageResIDs = imageResIDs;
        this.titleIDs = titles;
        this.descriptionsIDs = descriptions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return OnboardingCommonFragment.newInstance(
                context.getResources().getString(titleIDs[position]),
                context.getResources().getString(descriptionsIDs[position]),
                imageResIDs[position]
        );
    }

    @Override
    public int getItemCount() {
        return imageResIDs.length;
    }

}
