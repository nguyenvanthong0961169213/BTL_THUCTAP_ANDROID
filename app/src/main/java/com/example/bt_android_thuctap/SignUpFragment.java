package com.example.bt_android_thuctap;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {



    FragmentSignUpBinding fragmentSignUpBinding;
    LoginViewModel loginViewModel;
    ViewPagerLoginAdaper viewPagerLoginAdaper;


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
        fragmentSignUpBinding= FragmentSignUpBinding.inflate(inflater, container, false);
        View mview=fragmentSignUpBinding.getRoot();

        loginViewModel = new LoginViewModel();
        fragmentSignUpBinding.setLoginViewModel(loginViewModel);
        fragmentSignUpBinding.btnSignUp1.setOnClickListener(v-> SignUpClick());
        fragmentSignUpBinding.btnSignininfragsignup.setOnClickListener(v -> SignInClick());
        return mview;
    }

   private void SignInClick()
   {
       FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
       fragmentTransaction.replace(R.id.view_pager_login,SignInFragment.newInstance());
       fragmentTransaction.commit();
   }
    private void SignUpClick() {
        User user = new User(loginViewModel.getPhoneNumber(),loginViewModel.getPassword(),"user");
        if(isValidPassWord(user.getPassword())  == true && isValidPhone(user.getPhoneNumber()) == true)
        {
//           loginViewModel.validate.set("Đăng kí thành công");
            FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.view_pager_login,SignInFragment.newInstance());
            transaction.commit();
        }
        else{
              loginViewModel.validate.set("Đăng kí thất bại");
       }
    }
    public boolean isValidPassWord(String str){
        if(str.length()>=6){
            return true;
        }
        return false;
    }

    public boolean isValidPhone(String str){
        Pattern ptrn = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher match = ptrn.matcher(str);
        return true;
    }

}