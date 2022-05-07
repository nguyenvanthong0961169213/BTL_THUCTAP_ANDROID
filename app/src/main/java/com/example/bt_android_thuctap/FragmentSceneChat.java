package com.example.bt_android_thuctap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.databinding.FragmentSceneChatBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.viewmodel.FriendViewModel;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSceneChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSceneChat extends Fragment {
    FragmentSceneChatBinding fragmentSceneChatBinding;
    FriendViewModel friendViewModel;


    public FragmentSceneChat() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentSceneChat newInstance() {
        FragmentSceneChat fragment = new FragmentSceneChat();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSceneChatBinding= FragmentSceneChatBinding.inflate(inflater, container, false);
        View mview=fragmentSceneChatBinding.getRoot();

        friendViewModel = new FriendViewModel();
        fragmentSceneChatBinding.setFriendViewModel(friendViewModel);
        fragmentSceneChatBinding.txtNameFriendChatSense.setText(friendViewModel.getName());
        return mview;
    }
}