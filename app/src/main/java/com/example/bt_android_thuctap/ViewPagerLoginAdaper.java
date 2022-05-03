package com.example.bt_android_thuctap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

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
                return SignInFragment.newInstance();
            case 1:
                return SignUpFragment.newInstance();
            default:
                return SignInFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
/*    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    public void addFragments(List<Fragment> fragments) {
        fragmentList.clear();
        fragmentList.addAll(fragments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (fragmentList.contains(object)) {
            return POSITION_UNCHANGED;
        }
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int item) {
        if (item >= fragmentList.size()) {
            return null;
        }
        return fragmentList.get(item);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }*/
}