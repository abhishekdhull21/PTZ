package com.aimers.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aimers.zone.Modals.UserBio;
import com.aimers.zone.Utils.UserInfo;
import com.aimers.zone.Utils.Utils;
import com.aimers.zone.fragments.GameFragment;
import com.aimers.zone.fragments.LoginFragment;
import com.aimers.zone.fragments.MyZoneFragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

import static com.aimers.zone.Utils.Constant.GET_USER_INFO;
import static com.aimers.zone.Utils.Constant.REGISTER_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    final Fragment fragment1 = new GameFragment();
    final Fragment fragment2 = new LoginFragment();
    final Fragment fragment3 = new MyZoneFragment();
    final FragmentManager fm = getSupportFragmentManager();
    public static UserBio user;
    Fragment active = fragment1;

    private HashMap<String,String> params;

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