package com.aimers.zone;

import static com.aimers.zone.Modals.Wallet.wallet;
import static com.aimers.zone.Utils.Constant.JOINED_MATCH_URL;
import static com.aimers.zone.Utils.Utils.saveTokenLocal;
import static com.aimers.zone.fragments.RegisterFragment.TAG;
import static com.aimers.zone.wallet.Wallet.fetch;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Interface.WalletFetchResponse;
import com.aimers.zone.Modals.JoinedMatch;
import com.aimers.zone.Modals.Notification;
import com.aimers.zone.Modals.UserBio;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.UserInfo;
import com.aimers.zone.fragments.GameFragment;
import com.aimers.zone.fragments.MyZoneFragment;
import com.aimers.zone.fragments.SupportFragment;
import com.aimers.zone.wallet.Wallet;
import com.androidstudy.networkmanager.Tovuti;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.onesignal.OneSignal;
import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.Utilities.SmartDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    final Fragment fragment1 = new SupportFragment();
    final Fragment fragment2 = new GameFragment();
    final Fragment fragment3 = new MyZoneFragment();
    private NetworkRequest request;
    String  onesignal_userid ;
    final FragmentManager fm = getSupportFragmentManager();
    public static UserBio user;
    public static JoinedMatch jMatch;
    private static final String ONESIGNAL_APP_ID ="75715159-da5b-4540-9b57-76bc9916d532";
    Fragment active = fragment2;
//    private HashMap<String, String> params;
//    private RequestQueue queue;
    private ProgressDialog progressBar;
    public static ArrayList<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customActionbar();

        request = new NetworkRequest(MainActivity.this);
        fetchJoinedMatch();
        loadFragment();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
         onesignal_userid = Objects.requireNonNull(OneSignal.getDeviceState()).getUserId();

        progressBar =new ProgressDialog(this);
//        get intent from pervious activity
        UserInfo i = (UserInfo) getIntent().getSerializableExtra("user");
        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        if (!sp.contains("token") && i != null) {
            if (i.getToken() !=null)
            saveTokenLocal(sp, i.getToken());
        }
//        OneSignal.setExternalUserId();

//        OneSignal.setSMSNumber(user.getMobile() != null ? user.getMobile() : "0");
        SmartDialog mDialog = new SmartDialogBuilder(MainActivity.this)
                .setTitle("Network State")
                .setSubTitle("No Internet Connection Live")
                .setCancalable(false)
                .build();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        fetch(MainActivity.this,status -> {

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
//        Log.e(TAG, "customActionbar: main activity" );
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        TextView walletCoin = view.findViewById(R.id.textViewWallet);
        fetch(this, status -> {
            if (status)
                walletCoin.setText(com.aimers.zone.Modals.Wallet.wallet.getCoins());
//            Log.e(TAG, "customActionbar: "+wallet.getCoins() );

        });

//        Log.e(TAG, "customActionbar: "+wallet.getCoins() );
        ImageView notification = view.findViewById(R.id.notification_custom_navbar);
        notification.setOnClickListener(this);
        walletCoin.setOnClickListener(view1 -> {
            startActivity(new Intent(MainActivity.this,WalletActivity.class));
        });
    }
    public void loadFragment() {
        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").commit();
        fm.beginTransaction().add(R.id.fragment_container, fragment1, "1").hide(fragment1).commit();
    }
    private void fetchJoinedMatch(){
        HashMap<String, String> params = new HashMap<>();
        params.put("token",User.userToken(MainActivity.this));
        params.put("onesignal_userid",onesignal_userid);
        ArrayList<String> matches = new ArrayList<>();
        request.sendRequest(params, JOINED_MATCH_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                if (response.getBoolean("success")) {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length();i++){
                        JSONObject obj = data.getJSONObject(i);
                        matches.add(obj.getString("match_id"));
                    }
                }else{
                    Log.e(TAG, "fetchJoinedMatch"+response.getString("error"));
                    matches.add("0");
                }
                jMatch = new JoinedMatch(matches);
            }

            @Override
            public void onErrorResponse(JSONObject response) {
                Log.e(TAG, "onErrorResponse: "+response);
            }
        });
    }
    @SuppressLint("NonConstantResourceId")
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
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                return true;
        }
        return false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchJoinedMatch();
    }
    @SuppressLint("NonConstantResourceId")
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