package com.example.bt_android_thuctap.fragmenthomeapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.FragmentSceneChat;
import com.example.bt_android_thuctap.Layout_Home;
import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.RecentConversationsAdapter;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.databinding.FragmentSignUpBinding;
import com.example.bt_android_thuctap.model.ChatMessage;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_Home extends Fragment {

    User user;
    NavController navigation;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    UserAdapter userAdapter;
    List<User> data;
    PreferenceManager preferenceManager;
//    List<ChatMessage> conversions;
//    RecentConversationsAdapter recentConversationsAdapter;
    FragmentHomeBinding fragmentHomeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentHomeBinding= FragmentHomeBinding.inflate(inflater, container, false);
        View mview = fragmentHomeBinding.getRoot();
        preferenceManager = new PreferenceManager(getActivity().getApplicationContext());
        fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);
        navigation = NavHostFragment.findNavController(this);
        LoadingData();
        userAdapter = new UserAdapter(data,this);
        fragmentHomeBinding.edtSearch.setOnClickListener(v -> goConversions());
        return mview;
    }

    private void goConversions() {
        navigation.navigate(R.id.action_fragment_Home_to_conversionsFragment);
    }

    public void LoadingFriend(User Friend) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("haha",Friend);
        navigation.navigate(R.id.action_fragment_Home_to_fragmentSceneChat,bundle);
    }

    public void LoadingData() {
        data = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("User")
                .document(preferenceManager.getString(Constants.key_UserId));
        firebaseFirestore.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        if(doc.getId().equals(preferenceManager.getString
                                (Constants.key_UserId)) == false) {
                            User user = new User();
                            user.setName(doc.getString(Constants.key_Name));
                            user.setPhoneNumber(doc.getString(Constants.key_Phone));
                            user.setPassword(doc.getString(Constants.key_Password));
                            user.setToken(doc.getString(Constants.key_FCM_Token));
                            user.setImage(doc.getString(Constants.key_Image));
                            Log.e("TAG", "onComplete: " + doc.getId());
                            user.setStatus(doc.getString(Constants.key_Status));
                            user.setId(doc.getId());
                            data.add(user);
                        }
                    }
                    fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
                    fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        documentReference.update(Constants.key_Status, "offline");
    }

    @Override
    public void onResume() {
        super.onResume();
        documentReference.update(Constants.key_Status, "online");
    }
}
