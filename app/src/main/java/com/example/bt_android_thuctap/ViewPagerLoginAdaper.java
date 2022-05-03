package com.example.bt_android_thuctap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerLoginAdaper extends FragmentStatePagerAdapter {

    public ViewPagerLoginAdaper(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public ViewPagerLoginAdaper(FragmentManager manager) {
        super(manager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new SignInFragment();
            case 1:
                return new SignUpFragment();
            default:
                return new SignInFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
