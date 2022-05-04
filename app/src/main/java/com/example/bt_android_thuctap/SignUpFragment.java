package com.example.bt_android_thuctap;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;

public class SignUpFragment extends Fragment {


    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        // Inflate the layout for this fragment
        FragmentSignUpBinding fragmentSignUpBinding = DataBindingUtil.setContentView(getActivity(),
                R.layout.fragment_sign_up);
        LoginViewModel loginViewModel = new LoginViewModel();
        fragmentSignUpBinding.setLoginViewModel(loginViewModel);
        return mView;
    }
}