package com.example.bt_android_thuctap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mahfa.dnswitch.DayNightSwitch;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.view_pager_login);
        ViewPagerLoginAdaper viewPageLoginAdapter=new ViewPagerLoginAdaper(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageLoginAdapter);

    }

    public void chuyen(){
        Intent intent=new Intent(this,Layout_Home.class);
       startActivity(intent);
    }
}