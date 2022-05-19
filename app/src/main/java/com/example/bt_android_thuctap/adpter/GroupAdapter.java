package com.example.bt_android_thuctap.adpter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.databinding.GroupContainerBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.GroupChat;
import com.example.bt_android_thuctap.model.User;

import java.util.List;

public class GroupAdapter extends  RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    public final List<GroupChat> datagroup;
    public Fragment_Home fragment_Home ;


    public GroupAdapter(List<GroupChat> datagroup, Fragment_Home fragment_Home) {
        this.datagroup = datagroup;
        this.fragment_Home = fragment_Home;
    }


    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GroupContainerBinding binding = GroupContainerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        Log.e("haha", "onCreateViewHolder: kakakak" );
        return new GroupViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        GroupChat groupChat = datagroup.get(position);
        holder.setGroupData(groupChat);
        Log.e("ad", "onBindViewHolder: "+position );
    }

    @Override
    public int getItemCount() {
        if(datagroup != null){
            return datagroup.size();
        }
        return 0;
    }


    public class GroupViewHolder extends RecyclerView.ViewHolder{
        GroupContainerBinding binding;

        public GroupViewHolder( GroupContainerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setGroupData(GroupChat groupData){
            if(groupData.getImage()!= null){

            }
            binding.txtNameUserContainer.setText(groupData.getGroupName());

            binding.ctFriendUserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });



        }

    }
}
