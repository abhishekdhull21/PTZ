package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.Utils.UserInfo;
import com.example.myapplication.Utils.Utils;
import com.example.myapplication.fragments.GameFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.MatchActivity;
import com.example.myapplication.fragments.MatchVirewFragment;
import com.example.myapplication.fragments.MyZoneFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.Serializable;
import java.util.Objects;

import static com.example.myapplication.fragments.RegisterFragment.TAG;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {

    public static MainActivity main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;
        customActionbar();
        loadFragment(new GameFragment());
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
    public void loadFragment(Fragment f){
        if (f==null)
            return;
        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragment_container, f);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navigation_home:
                loadFragment(new GameFragment());
                break;
            case R.id.navigation_dashboard:
                loadFragment(new MatchVirewFragment());
                break;
            case R.id.myzone_menu_item:
                loadFragment(new MyZoneFragment());
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