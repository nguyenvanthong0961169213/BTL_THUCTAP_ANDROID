package com.example.bt_android_thuctap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bt_android_thuctap.common.Convert;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Changer_Password;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Update_Profile;
import com.example.bt_android_thuctap.fragmenthomeapp.HomeAppFragment;
import com.example.bt_android_thuctap.model.User;
import com.example.bt_android_thuctap.util.BaseActivity;
import com.example.bt_android_thuctap.util.Constants;
import com.example.bt_android_thuctap.util.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Layout_Home extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    PreferenceManager preferenceManager ;
    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME=0;
    private static final int FRAGMENT_UPDATE_PROFILE=1;
    private static final int FRAGMENT_CHANGE_PASSWORD=2;
//    private static final int FRAGMENT_CHAT_SENSE=3;
    DocumentReference documentReference;
    FirebaseFirestore firebaseFirestore;
    CircleImageView circleImageView;
    TextView textView;

    private int currentFragment=FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferenceManager = new PreferenceManager(this.getApplicationContext());
        getToken();
        Log.e("sdasadsdasd", "onCreate: "+preferenceManager.getString(Constants.key_Phone) );

        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference = firebaseFirestore.collection("User")
                .document(preferenceManager.getString(Constants.key_UserId));


        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.Navigation_drawer_open,R.string.Navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=findViewById(R.id.navigation_view);
        View headerview =navigationView.getHeaderView(0);
        circleImageView =(CircleImageView)headerview.findViewById(R.id.avatarUser);
        textView  = (TextView)headerview.findViewById(R.id.nameUser);
        circleImageView.setImageBitmap(Convert.base64ToBitmap(preferenceManager.getString(Constants.key_Image)));
        textView.setText(preferenceManager.getString(Constants.key_Name));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeAppFragment());
//        SetDataUser();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         int id=item.getItemId();
         if(id==R.id.navigation_home_app)
         {
             if(currentFragment!=FRAGMENT_HOME)
             {
                 replaceFragment(new HomeAppFragment());
                 currentFragment=FRAGMENT_HOME;
             }
         }
         else if(id==R.id.navigation_update_profile)
         {
             if(currentFragment!=FRAGMENT_UPDATE_PROFILE)
             {
                 replaceFragment(new Fragment_Update_Profile());
                 currentFragment=FRAGMENT_UPDATE_PROFILE;
             }
         }
         else if(id ==R.id.navigation_change_password) {
             if(currentFragment!=FRAGMENT_CHANGE_PASSWORD)
             {
                 replaceFragment(new Fragment_Changer_Password());
                 currentFragment=FRAGMENT_CHANGE_PASSWORD;
             }
         }
         else
         {
             SignOut();
         }
         drawerLayout.closeDrawer(GravityCompat.START);

         return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
        {
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
    private void SignOut(){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection("User").document(preferenceManager.getString(Constants.key_UserId));
        Map<String, Object> updates = new HashMap<>();
        updates.put(Constants.key_FCM_Token, FieldValue.delete());
        documentReference.update(updates).addOnSuccessListener(unused -> {
            preferenceManager.clear();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        });
    }

    public User SetDataUser(){
        User user = new User(preferenceManager.getString(Constants.key_Name),
                preferenceManager.getString(Constants.key_Phone),
                preferenceManager.getString(Constants.key_Password),
                preferenceManager.getString(Constants.key_UserId),
                preferenceManager.getString(Constants.key_FCM_Token),
                preferenceManager.getString(Constants.key_Image));
        Log.e("Du lieu nguoi dung ",user.getName());
        return user;
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

    private void updateToken(String token){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection("User").document(preferenceManager.getString(Constants.key_UserId));
        documentReference.update(Constants.key_FCM_Token,token).addOnSuccessListener(unused -> showToast("Update"))
                .addOnFailureListener(e -> showToast("Error"));

    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }
    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}