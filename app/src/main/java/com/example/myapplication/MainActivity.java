package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Utils.UserInfo;
import com.example.myapplication.Utils.Utils;
import com.example.myapplication.fragments.GameFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.MatchViewFragment;
import com.example.myapplication.fragments.MyZoneFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    final Fragment fragment1 = new GameFragment();
    final Fragment fragment2 = new LoginFragment();
    final Fragment fragment3 = new MyZoneFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    public static MainActivity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customActionbar();
        loadFragment();
//        get intent from pervious activity
        UserInfo i = (UserInfo) getIntent().getSerializableExtra("user");;
        SharedPreferences sp = getSharedPreferences("token",MODE_PRIVATE);
        if(!sp.contains("token")) {
            assert i != null;
            Utils.saveTokenLocal(sp,i.getToken());
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(this);
    }
    private void customActionbar(){
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();
        ImageView notification = view.findViewById(R.id.notification_custom_navbar);
        notification.setOnClickListener(this);
    }
    public void loadFragment(){
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;
            case R.id.navigation_dashboard:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            case R.id.myzone_menu_item:
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification_custom_navbar:
                startActivity(new Intent(this,NotificationActivity.class));
        }
    }
}