package com.example.bt_android_thuctap.adpter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.common.Convert;
import com.example.bt_android_thuctap.databinding.ItemContainerRecentConversionBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.fragmenthomeapp.ConversionsFragment;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;

import java.util.List;

public class RecentConversationsAdapter extends RecyclerView.Adapter<RecentConversationsAdapter.ConversionViewHolder> {
    public final List<ChatMessage> data;
    public ConversionsFragment conversionsFragment ;

    public RecentConversationsAdapter(List<ChatMessage> data , ConversionsFragment conversionsFragment) {
        this.data = data;
        this.conversionsFragment = conversionsFragment;
        Log.e("TAG", "UserAdapter: ");
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerRecentConversionBinding binding = ItemContainerRecentConversionBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
//        Log.e("haha", "onCreateViewHolder: kakakak" );
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
//            Log.e("TAG", "onCreateView: "+user.getId());
        }
    }
}
