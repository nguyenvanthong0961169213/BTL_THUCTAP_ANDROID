package com.example.bt_android_thuctap.adpter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.FragmentSceneChat;
import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.common.Convert;
import com.example.bt_android_thuctap.databinding.ItemContainerReceivedMessageBinding;
import com.example.bt_android_thuctap.databinding.ItemContainerSentMessageBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.model.ChatMessage;

import java.util.List;

public class ChatSenseAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;

    public ItemContainerSentMessageBinding binding;
    public List<ChatMessage> data;
    private final String senderID;
    FragmentSceneChat fragmentSceneChat;


    public ChatSenseAdapter(List<ChatMessage> data, FragmentSceneChat fragmentSceneChat) {
        this.data = data;
        this.fragmentSceneChat = fragmentSceneChat;
        this.senderID = fragmentSceneChat.setDataSender().getId();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            binding = ItemContainerSentMessageBinding.inflate(
                    LayoutInflater.from
                    (parent.getContext()),parent,false);
            return new SendMessageViewHolder(binding);
        }
        else {
            return new ReceiverMessageViewHolder(ItemContainerReceivedMessageBinding.inflate(LayoutInflater.from
                    (parent.getContext()),parent,false));
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_SENT){
            ((SendMessageViewHolder)holder).SetMessageSendData(data.get(position));
        }
        else {
            ((ReceiverMessageViewHolder)holder).SetMessageReceiverData(data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(data.get(position).getIdSend().equals(senderID))  { return VIEW_TYPE_SENT;}
        else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    public class SendMessageViewHolder extends RecyclerView.ViewHolder{

        ItemContainerSentMessageBinding binding;
        public SendMessageViewHolder(ItemContainerSentMessageBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;

        }
        public void SetMessageSendData(ChatMessage chatMessage){
            binding.txtMessageSent.setText(chatMessage.getMessage());
           // binding.checkSend.setImageResource(R.drawable.ic_send_success);
        }
    }

    public class ReceiverMessageViewHolder extends RecyclerView.ViewHolder{

        ItemContainerReceivedMessageBinding binding;
        public ReceiverMessageViewHolder(ItemContainerReceivedMessageBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;

        }


        public void SetMessageReceiverData(ChatMessage chatMessage){
            binding.textMessageReceiver.setText(chatMessage.getMessage());
            if(fragmentSceneChat.receiverUser.getImage()!= null){
                binding.imageProfile.setImageBitmap(Convert.base64ToBitmap
                        (fragmentSceneChat.receiverUser.getImage()));
            }

        }
    }

}
