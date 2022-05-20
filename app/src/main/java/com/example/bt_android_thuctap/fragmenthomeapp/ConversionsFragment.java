package com.example.bt_android_thuctap.fragmenthomeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.RecentConversationsAdapter;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentConversionsBinding;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ConversionsFragment extends Fragment {

    NavController navigation;
    FirebaseFirestore firebaseFirestore;
    List<ChatMessage> data;
    List<User> friend;
    RecentConversationsAdapter recentConversationsAdapter;
    FragmentConversionsBinding binding;
    PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentConversionsBinding.inflate(inflater, container, false);
        View mview=binding.getRoot();
        init();
        listenerdata();
        navigation = NavHostFragment.findNavController(this);
        binding.btnTranshome.setOnClickListener(v -> goHome());
        return mview;

    }

    private void goHome() {
        navigation.navigate(R.id.action_conversionsFragment_to_fragment_Home);
    }

    public void loadingFriend(String idFriend){
        Log.e("TAGaaaa", "loadingFriend: "+ idFriend );
        loadingData(idFriend);
    }

    public void init(){
        navigation = NavHostFragment.findNavController(this);
        data = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        recentConversationsAdapter = new RecentConversationsAdapter(data,this);

    }

    private void listenerdata(){
        Log.e("TAG", "listenerdata: "+preferenceManager.getString(Constants.key_UserId) );
        firebaseFirestore.collection(Constants.key_Conversion_Col)
                .whereEqualTo(Constants.key_Sender_Id,preferenceManager.getString(Constants.key_UserId))
                .addSnapshotListener(eventListener);
        firebaseFirestore.collection(Constants.key_Conversion_Col)
                .whereEqualTo(Constants.key_Receiver_Id,preferenceManager.getString(Constants.key_UserId))
                .addSnapshotListener(eventListener);
    }

    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                Log.e("TAG", ": ++++++++== 0000000000"   );
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    ChatMessage chatMessage = new ChatMessage();
                    String senderId = documentChange.getDocument().getString(Constants.key_Sender_Id);
                    String recevierId = documentChange.getDocument().getString(Constants.key_Receiver_Id);
                    chatMessage.setIdReceiver(recevierId);
                    chatMessage.setIdSend(senderId);
                    chatMessage.setMessage(documentChange.getDocument().getString(Constants.key_Last_Message));
                    chatMessage.setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
                    if (preferenceManager.getString(Constants.key_UserId).equals(senderId)) {
                        chatMessage.setConversionImage(documentChange.getDocument().getString(Constants.key_Receiver_Image));
                        chatMessage.setConversionId(documentChange.getDocument().getString(Constants.key_Receiver_Id));
                        chatMessage.setConversionName(documentChange.getDocument().getString(Constants.key_Receiver_Name));
                    } else {
                        chatMessage.setConversionId(documentChange.getDocument().getString(Constants.key_Sender_Id));
                        chatMessage.setConversionName(documentChange.getDocument().getString(Constants.key_Sender_Name));
                        chatMessage.setConversionImage(documentChange.getDocument().getString(Constants.key_Sender_Image));
                    }
                    data.add(chatMessage);

                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < data.size(); i++) {
                        String senderId = documentChange.getDocument().getString(Constants.key_Sender_Id);
                        String recevierId = documentChange.getDocument().getString(Constants.key_Receiver_Id);
                        if (data.get(i).getIdSend().equals(senderId) && data.get(i).getIdReceiver().equals(recevierId)) {
                            data.get(i).setMessage(documentChange.getDocument().getString(Constants.key_Last_Message));
                            data.get(i).setDateObject(documentChange.getDocument().getDate(Constants.key_Time));
                            break;
                        }
                    }
                }
            }
            Log.e("aaaaaaaaaaaaa", "aaaaaaaaaaaaaaa " + data.size());
            Collections.sort(data, (obj1, obj2) -> obj2.getDateObject().compareTo(obj1.getDateObject()));
            binding.rectanglesUser.setAdapter(recentConversationsAdapter);
            binding.rectanglesUser.smoothScrollToPosition(0);
            binding.rectanglesUser.setVisibility(View.VISIBLE);

        }
    };

    public void loadingData(String idFriend) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(Constants.key_User_Col).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if (doc.getId().equals(idFriend) == true) {
                            user.setName(doc.getString(Constants.key_Name));
                            user.setPhoneNumber(doc.getString(Constants.key_Phone));
                            user.setPassword(doc.getString(Constants.key_Password));
                            user.setToken(doc.getString(Constants.key_FCM_Token));
                            user.setImage(doc.getString(Constants.key_Image));
                            Log.e("TAG", "onComplete: " + doc.getId());
                            user.setStatus(doc.getString(Constants.key_Status));
                            user.setId(doc.getId());
                            break;
                        }
                    }
                    Log.e("TAG", "onComplete: a" + user.getId() );
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.key_Receiver_Id,user);
                    navigation.navigate(R.id.action_conversionsFragment_to_fragmentSceneChat,bundle);
                }
            }

        });
    }


}