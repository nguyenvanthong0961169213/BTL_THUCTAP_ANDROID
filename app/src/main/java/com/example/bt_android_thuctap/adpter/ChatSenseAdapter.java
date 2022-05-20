package com.example.bt_android_thuctap.adpter;

import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.FragmentSceneChat;
import com.example.bt_android_thuctap.common.Convert;
//import com.example.bt_android_thuctap.common.Firebase;
import com.example.bt_android_thuctap.databinding.ItemContainerReceivedMessageBinding;
import com.example.bt_android_thuctap.databinding.ItemContainerReceivedPhotoBinding;
import com.example.bt_android_thuctap.databinding.ItemContainerSentMessageBinding;
import com.example.bt_android_thuctap.databinding.ItemContainerSentPhotoBinding;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.firebase.FireBase;
import com.example.bt_android_thuctap.model.ChatMessage;

import java.util.List;

public class ChatSenseAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_SENT = 1;
    public static final int VIEW_TYPE_RECEIVED = 2;
    public static final int VIEW_IMAGE_SENT = 3;
    public static final int VIEW_IMAGE_RECEIVED = 4;
    FireBase firebase = new FireBase ();

    public List<ChatMessage> data;
    private final String senderID;
    FragmentSceneChat fragmentSceneChat;


    public ChatSenseAdapter(List<ChatMessage> data, FragmentSceneChat fragmentSceneChat) {
        this.data = data;
        this.fragmentSceneChat = fragmentSceneChat;
        this.senderID = fragmentSceneChat.setDataSender().getId();
        Log.e("haha", "ChatSenseAdapter: Ok" );

    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_SENT){
            ItemContainerSentMessageBinding binding = ItemContainerSentMessageBinding.inflate(
                    LayoutInflater.from
                            (parent.getContext()),parent,false);
            return new SendMessageViewHolder(binding);
        }
        else if(viewType == VIEW_IMAGE_SENT){
            return new SendImageViewHolder(ItemContainerSentPhotoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        }
        else if(viewType == VIEW_IMAGE_RECEIVED){
            return new ReceiverImageViewHolder(ItemContainerReceivedPhotoBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
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
        else if(getItemViewType(position) == VIEW_IMAGE_SENT){
            ((SendImageViewHolder)holder).SetImageSendData(data.get(position));
        }
        else if(getItemViewType(position) == VIEW_IMAGE_RECEIVED){
            ((ReceiverImageViewHolder)holder).SetImageReceiverData(data.get(position));
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
        if(data.get(position).getMessage() != ""){
            if(data.get(position).getIdSend().equals(senderID))  { return VIEW_TYPE_SENT;}
            else {
                return VIEW_TYPE_RECEIVED;
            }
        }
        else if(data.get(position).getUri() != null){
            if(data.get(position).getIdSend().equals(senderID)) {
                return  VIEW_IMAGE_SENT;
            }
            else return VIEW_IMAGE_RECEIVED;
        }
        return 0;
    }

    public class SendMessageViewHolder extends RecyclerView.ViewHolder{

        ItemContainerSentMessageBinding binding;
        public SendMessageViewHolder(ItemContainerSentMessageBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;

        }
        public void SetMessageSendData(ChatMessage chatMessage){
            binding.txtMessageSent.setText(chatMessage.getMessage());

        }
    }
    public class SendImageViewHolder extends RecyclerView.ViewHolder{

        ItemContainerSentPhotoBinding binding;
        public SendImageViewHolder(ItemContainerSentPhotoBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;

        }
        public void SetImageSendData(ChatMessage chatMessage){
            firebase.visibleImage("images/"+chatMessage.getUri(),binding.imgSentPhoto);
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
    public class ReceiverImageViewHolder extends RecyclerView.ViewHolder{

        ItemContainerReceivedPhotoBinding binding;
        public ReceiverImageViewHolder(ItemContainerReceivedPhotoBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;

        }


        public void SetImageReceiverData(ChatMessage chatMessage){
            firebase.visibleImage("images/"+chatMessage.getUri(),binding.imgReceiverPhoto);
            if(fragmentSceneChat.receiverUser.getImage()!= null){
                binding.imageProfile2.setImageBitmap(Convert.base64ToBitmap
                        (fragmentSceneChat.receiverUser.getImage()));
            }

        }
    }

}
