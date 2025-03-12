package com.example.cs4514_jlpt_exam_helper.reset_password.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cs4514_jlpt_exam_helper.reset_password.fragment.EnterEmailResetPasswordFragment;
import com.example.cs4514_jlpt_exam_helper.reset_password.fragment.EnterOtpResetPasswordFragment;
import com.example.cs4514_jlpt_exam_helper.reset_password.fragment.ResetPasswordFragment;
import com.example.cs4514_jlpt_exam_helper.reset_password.fragment.ResetPasswordSuccessFragment;

public class ResetPasswordViewPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 4;

    public ResetPasswordViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EnterEmailResetPasswordFragment();
            case 1:
                return new EnterOtpResetPasswordFragment();
            case 2:
                return new ResetPasswordFragment();
            case 3:
                return new ResetPasswordSuccessFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
