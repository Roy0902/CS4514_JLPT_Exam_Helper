package com.example.cs4514_jlpt_exam_helper.sign_up.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cs4514_jlpt_exam_helper.sign_up.fragment.EditProfileFragment;
import com.example.cs4514_jlpt_exam_helper.sign_up.fragment.EnterEmailSignUpFragment;
import com.example.cs4514_jlpt_exam_helper.sign_up.fragment.EnterOtpSignUpFragment;
import com.example.cs4514_jlpt_exam_helper.sign_up.fragment.SignUpSuccessFragment;

public class SignUpViewPagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 4;

    public SignUpViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new EnterEmailSignUpFragment();
            case 1:
                return new EnterOtpSignUpFragment();
            case 2:
                return new EditProfileFragment();
            case 3:
                return new SignUpSuccessFragment();
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}