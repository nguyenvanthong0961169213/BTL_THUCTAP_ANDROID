package com.example.bt_android_thuctap;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;
import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class SignInFragment extends Fragment {

    private View mview;
    ImageView sun;
    View daybackground, nightbackground;
    DayNightSwitch dayNightSwitch;
    public SignInFragment() {
        // Required empty public constructor
    }
    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_sign_in, container, false);
        Connect_ID();
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                if (is_night) {
                    sun.animate().translationY(30).setDuration(1000);
                    daybackground.animate().alpha(0).setDuration(1300);
                } else {
                    sun.animate().translationY(-30).setDuration(1000);
                    daybackground.animate().alpha(1).setDuration(1300);
                }
            }
        });
        FragmentSignInBinding fragmentSignInBinding = DataBindingUtil.setContentView(getActivity(),
                R.layout.fragment_sign_in);
        LoginViewModel loginViewModel = new LoginViewModel();
        fragmentSignInBinding.setLoginViewModel(loginViewModel);
        return mview;
    }
    void Connect_ID()
    {
        sun=mview.findViewById(R.id.sun);
        daybackground=mview.findViewById(R.id.day_bạckground);
        nightbackground=mview.findViewById(R.id.night_background);
        dayNightSwitch=mview.findViewById(R.id.day_night_switch);
    }
}