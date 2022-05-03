package com.example.bt_android_thuctap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Changer_Password;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Update_Profile;
import com.google.android.material.navigation.NavigationView;

public class Layout_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private static final int FRAGMENT_HOME=0;
    private static final int FRAGMENT_UPDATE_PROFILE=1;
    private static final int FRAGMENT_CHANGE_PASSWORD=2;

    private int currentFragment=FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.Navigation_drawer_open,R.string.Navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView=findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Fragment_Home());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         int id=item.getItemId();
         if(id==R.id.navigation_home)
         {
             if(currentFragment!=FRAGMENT_HOME)
             {
                 replaceFragment(new Fragment_Home());
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
             //Đăng Xuất
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
}