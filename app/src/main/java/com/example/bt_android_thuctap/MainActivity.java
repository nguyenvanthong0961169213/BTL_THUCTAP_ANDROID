package com.example.bt_android_thuctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;
import com.google.android.material.tabs.TabLayout;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    FragmentSignInBinding fragmentSignInBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.view_pager_login);
        ViewPagerLoginAdaper viewPageLoginAdapter = new ViewPagerLoginAdaper(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageLoginAdapter);

/*        fragmentSignInBinding = DataBindingUtil.setContentView(this,
                R.layout.fragment_sign_in);
        LoginViewModel loginViewModel = new LoginViewModel();
        fragmentSignInBinding.setLoginViewModel(loginViewModel);
        fragmentSignInBinding.dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night) {
                    fragmentSignInBinding.sun.animate().translationY(30).setDuration(1000);
                    fragmentSignInBinding.dayBCkground.animate().alpha(0).setDuration(1300);
                } else {
                    fragmentSignInBinding.sun.animate().translationY(-30).setDuration(1000);
                    fragmentSignInBinding.dayBCkground.animate().alpha(1).setDuration(1300);
                }
            }
        });*/
    }
}