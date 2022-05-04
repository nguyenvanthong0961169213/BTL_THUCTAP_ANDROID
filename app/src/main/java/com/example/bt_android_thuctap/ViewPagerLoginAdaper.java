package com.example.bt_android_thuctap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerLoginAdaper extends FragmentStatePagerAdapter {

    public ViewPagerLoginAdaper(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new SignUpFragment();
//            case 1:
//                return new SignUpFragment();
            default:
                return new SignUpFragment();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
