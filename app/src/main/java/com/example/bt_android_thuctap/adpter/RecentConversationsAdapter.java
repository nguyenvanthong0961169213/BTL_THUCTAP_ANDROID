package com.example.bt_android_thuctap.adpter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.Layout_Home;
import com.example.bt_android_thuctap.common.Convert;
import com.example.bt_android_thuctap.databinding.ItemContainerRecentConversionBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.fragmenthomeapp.ConversionsFragment;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {
    public final List<ChatMessage> data;
    public ConversionsFragment conversionsFragment ;

    public RecentConversationsAdapter(List<ChatMessage> data , ConversionsFragment conversionsFragment) {
        this.data = data;
        this.conversionsFragment = conversionsFragment;
        Log.e("TAG", "UserAdapter: " + data.size());
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerRecentConversionBinding binding = ItemContainerRecentConversionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        return new RecentConversationsAdapter.ConversionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{
        ItemContainerRecentConversionBinding binding;


        public ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding) {
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }
        void setData(ChatMessage chatMessage){
            if(chatMessage.getConversionImage()!= null){
                binding.imgUsercontainer.setImageBitmap(Convert.base64ToBitmap(chatMessage.getConversionImage()));
            }
            binding.txtNameUserContainer.setText(chatMessage.getConversionName());
            binding.txtRecentConversionContainer.setText(chatMessage.getMessage());
            binding.ctFriendUserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("TAG", "onClick: "+chatMessage.getIdReceiver() );
                    conversionsFragment.loadingFriend(chatMessage.getIdReceiver());
                }
            });
        }


    }
}
