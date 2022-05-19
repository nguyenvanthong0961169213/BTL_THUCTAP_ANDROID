package com.example.bt_android_thuctap.fragmenthomeapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.adpter.GroupAdapter;
import com.example.bt_android_thuctap.adpter.UserAdapter;
import com.example.bt_android_thuctap.databinding.FragmentHomeBinding;
import com.example.bt_android_thuctap.model.GroupChat;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_Home extends Fragment {

    NavController navigation;
   /* FirebaseDatabase firebaseDatabase;*/
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    DatabaseReference databaseReference;
    UserAdapter userAdapter;
    GroupAdapter groupAdapter;
    List<User> dataUser;
    List<GroupChat> dataGroup;
    PreferenceManager preferenceManager;
    public String chatId;

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
        userAdapter = new UserAdapter(dataUser,this);
        groupAdapter = new GroupAdapter(dataGroup,this);

        fragmentHomeBinding.edtSearch.setOnClickListener(v -> goConversions());
        fragmentHomeBinding.addGroupBtn.setOnClickListener(v-> requestGroup());
        return mview;
    }

    private  void requestGroup(){
        Log.d("Request","Error");
        String[] phone = new String[dataUser.size()];
        for(int i=0;i< dataUser.size();i++){
            phone[i] =(dataUser.get(i).getPhoneNumber());
        }
        ArrayList<String> selectedItemList = new ArrayList<>();
         int itemListLength = phone.length;
         boolean[] selectionPreference = new boolean[itemListLength];
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Enter grouup name");
        final EditText groupName =new EditText(getActivity());
        groupName.setHint("CNTT");
        builder.setView(groupName);
        builder.setMultiChoiceItems(phone, selectionPreference, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        String item = phone[i];
                        if(b){
                            selectionPreference[i]= true;
                        }
                        else{
                            selectionPreference[i] = false;
                        }
                    }
        });
         builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String group = groupName.getText().toString();
                        if (TextUtils.isEmpty(group)) {
                            Toast.makeText(getActivity(), "Please write name group", Toast.LENGTH_SHORT);

                        }
                        else
                            Log.d("if","Error");
                            /*CreateNewGroup(group,selectedItemList);*/
                        {
                            for (i = 0; i < itemListLength; i++) {
                                if (selectionPreference[i]) {
                                    selectedItemList.add(phone[i]);
                                }


                            }
                            CreateNewGroup(group,selectedItemList);

                        }

                    }
         });
        builder.setNegativeButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();



    }

    private void CreateNewGroup(String groupName,ArrayList<String> listPhone) {
    /*    Log.d("Create","Error");*/
        /*firebaseDatabase = FirebaseDatabase.getInstance();*/
/*        databaseReference = FirebaseDatabase.getInstance(
                "https://w6chat-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Group");*/
        firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String,String> phoneAdd = new HashMap<>();
        HashMap<String, Object> group = new HashMap<>();
        String groupId="";
        group.put(Constants.key_Group_Name,groupName);
        group.put(Constants.key_Group_Id,preferenceManager.getString(Constants.key_UserId));

/*        firebaseFirestore.collection(Constants.key_Group_Col).add(group).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                chatId   = documentReference.getId();
                preferenceManager.putString(Constants.key_Group_Id_Doc,chatId);
                Log.e("hi1",""+chatId);
    *//*            for(int i = 0;i< listPhone.size();i++){
                    User user = new User(listPhone.get(i));
                    firebaseFirestore.collection(Constants.key_Group_Col).document().collection("Member").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Ha","UpdatePhone");
                        }
                    });

                }*//*
            }
        });*/
        firebaseFirestore.collection(Constants.key_Group_Col).add(group).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                chatId   = task.getResult().getId();
                preferenceManager.putString(Constants.key_Group_Id_Doc,chatId);
                Log.e("hi1",""+chatId);

            }
        });
        Log.e("hi2",""+chatId);
  /*      GroupChat groupChat = new GroupChat(groupName,preferenceManager.getString(Constants.key_UserId),"","");
        databaseReference.push().setValue(groupChat);*/
 /*       databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/






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
        firebaseFirestore = FirebaseFirestore.getInstance();
        /*CollectionReference docref = firebaseFirestore.collection(Constants.key_Group_Col).document(chatId).collection("Member");*/
        dataUser = new ArrayList<>();
        dataGroup = new ArrayList<>();

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
                            dataUser.add(user);
                        }
                    }
                    fragmentHomeBinding.rectanglesUser.setAdapter(userAdapter);
                    fragmentHomeBinding.rectanglesUser.setVisibility(View.VISIBLE);
                }
            }
        });

        Log.d("ha", "onComplete: ");
        firebaseFirestore.collection(Constants.key_Group_Col).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc : task.getResult()){
                        if(doc.get(Constants.key_Group_Id).equals(preferenceManager.getString(Constants.key_UserId))){
                            GroupChat groupChat = new GroupChat();
                            groupChat.setGroupId(doc.getString(Constants.key_Group_Id));
                            groupChat.setGroupName(doc.getString(Constants.key_Group_Name));
                            groupChat.setGroupMember("");
                            groupChat.setImage("");
                            dataGroup.add(groupChat);
                            Log.d("ha", "onComplete: ");
                        }

                    }
                    fragmentHomeBinding.rectanglesGroup.setAdapter(groupAdapter);
                    fragmentHomeBinding.rectanglesGroup.setVisibility(View.VISIBLE);

                }

            }
        });
/*        docref.whereEqualTo("Phone",preferenceManager.getString(Constants.key_Phone)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
               if(task.isSuccessful()){
                   for(QueryDocumentSnapshot doc : task.getResult()){

                           GroupChat groupChat = new GroupChat();
                           groupChat.setGroupId(doc.getString(Constants.key_Group_Id));
                           groupChat.setGroupName(doc.getString(Constants.key_Group_Name));
                           groupChat.setGroupMember("");
                           groupChat.setImage("");
                           dataGroup.add(groupChat);
                           Log.d("ha", "onComplete: ");

                   }
               }
            }
        });*/
/*        firebaseFirestore.collection(Constants.key_Group_Col).whereEqualTo(Constants.key_Group_Id,preferenceManager.getString(Constants.key_UserId))*/

       /* firebaseFirestore.collection(Constants.key_Group_Col).get()*/
    }

    @Override
    public void onPause() {
        super.onPause();
      /*  documentReference.update(Constants.key_Status, "offline");*/
    }

    @Override
    public void onResume() {
        super.onResume();
        /*documentReference.update(Constants.key_Status, "online");*/
    }
}
