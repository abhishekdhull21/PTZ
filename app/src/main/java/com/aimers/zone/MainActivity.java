package com.aimers.zone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aimers.zone.Modals.Notification;
import com.aimers.zone.Modals.UserBio;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.UserInfo;
import com.aimers.zone.fragments.GameFragment;
import com.aimers.zone.fragments.LoginFragment;
import com.aimers.zone.fragments.MyZoneFragment;
import com.aimers.zone.wallet.Wallet;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidstudy.networkmanager.Tovuti;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.onesignal.OneSignal;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.aimers.zone.Utils.Constant.WALLET_URL;
import static com.aimers.zone.Utils.Utils.alert;
import static com.aimers.zone.Utils.Utils.saveTokenLocal;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    final Fragment fragment1 = new LoginFragment();
    final Fragment fragment2 = new GameFragment();
    final Fragment fragment3 = new MyZoneFragment();

    final FragmentManager fm = getSupportFragmentManager();
    public static UserBio user;
    private static final String ONESIGNAL_APP_ID ="75715159-da5b-4540-9b57-76bc9916d532" ;
    Fragment active = fragment2;

    private HashMap<String, String> params;
    private RequestQueue queue;
    private ProgressDialog progressBar;
    public static ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customActionbar();
        loadFragment();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        queue = Volley.newRequestQueue(this);
        progressBar =new ProgressDialog(this);
//        get intent from pervious activity
        UserInfo i = (UserInfo) getIntent().getSerializableExtra("user");
        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        if (!sp.contains("token")) {
            assert i != null;
            saveTokenLocal(sp, i.getToken());
        }
//MaterialDialog
        SmartDialog mDialog = new SmartDialogBuilder(MainActivity.this)
                .setTitle("Network State")
                .setSubTitle("No Internet Connection Live")
                .setCancalable(false)
                .build();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        Wallet.fetch(MainActivity.this,status -> {

        });
//        loadNotification();
        navigation.setOnItemSelectedListener(this);
        fab.setOnClickListener(this);


        Tovuti.from(this).monitor((connectionType, isConnected, isFast) -> {
            mDialog.dismiss();
            if(!isConnected) {
                mDialog.show();
            }
        });

    }


    private void customActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null)return;
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        view.setBackgroundColor(getColor(R.color.colorPrimary));
//        view.setPadding(0,0,0,0);
        ImageView notification = view.findViewById(R.id.notification_custom_navbar);
        notification.setOnClickListener(this);
    }

    public void loadFragment() {
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").attach(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                return true;
            case R.id.navigation_dashboard:
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                return true;
            case R.id.myzone_menu_item:
//                fragment3 = new MyZoneFragment();
                fm.beginTransaction().hide(active).show(fragment3).commit();
//                replaceFragment(new MyZoneFragment());
                active = fragment3;
                return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_custom_navbar:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.floatingActionButton:
//                startActivity(new Intent(MainActivity.this, MyMatchActivity.class));
                break;
        }
    }



}