package com.example.bt_android_thuctap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.example.bt_android_thuctap.adpter.ChatSenseAdapter;
import com.example.bt_android_thuctap.databinding.FragmentSceneChatBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignInBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.viewmodel.FriendViewModel;
import com.example.bt_android_thuctap.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSceneChat#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSceneChat extends Fragment {
    public  FragmentSceneChatBinding fragmentSceneChatBinding;
    public TextView txt;
    public List<ChatMessage> data;
    ChatSenseAdapter adpater;




    public FragmentSceneChat() {
    }

    public static FragmentSceneChat newInstance() {
        FragmentSceneChat fragment = new FragmentSceneChat();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSceneChatBinding= FragmentSceneChatBinding.inflate(inflater, container, false);
        View mview=fragmentSceneChatBinding.getRoot();
        User user = (User) getArguments().getSerializable("haha");
        fragmentSceneChatBinding.txtNameFriendChatSense.setText(user.getName());
        data = new ArrayList<>();



        adpater = new ChatSenseAdapter(data,this);
        fragmentSceneChatBinding.rcvChatSense.setAdapter(adpater);





        return mview;
    }
    public User setDataSender(){
        Layout_Home layout_home = (Layout_Home) getActivity();
        return layout_home.SetDataUser();
    }
}