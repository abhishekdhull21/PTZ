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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aimers.zone.Modals.UserBio;
import com.aimers.zone.Modals.Wallet;
import com.aimers.zone.Utils.User;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.aimers.zone.Utils.Constant.WALLET_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, View.OnClickListener {
    final Fragment fragment1 = new LoginFragment();
    final Fragment fragment2 = new GameFragment();
    final Fragment fragment3 = new MyZoneFragment();

    final FragmentManager fm = getSupportFragmentManager();
    public static UserBio user;

    Fragment active = fragment2;

    private HashMap<String, String> params;
    private RequestQueue queue;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customActionbar();
        loadFragment();
        queue = Volley.newRequestQueue(this);
        progressBar =new ProgressDialog(this);
//        get intent from pervious activity
        UserInfo i = (UserInfo) getIntent().getSerializableExtra("user");
        ;
        SharedPreferences sp = getSharedPreferences("token", MODE_PRIVATE);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        if (!sp.contains("token")) {
            assert i != null;
            Utils.saveTokenLocal(sp, i.getToken());
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        loadData();
        navigation.setOnItemSelectedListener(this);
        fab.setOnClickListener(this);

    }

    private void customActionbar() {
        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view = getSupportActionBar().getCustomView();
        assert actionBar != null;
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
                fm.beginTransaction().hide(active).show(fragment3).commit();
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

    public void loadData() {
        progressBar.setMessage("getting to serve");
        progressBar.setTitle("Wait for second");
        progressBar.setCancelable(false);
        progressBar.show();
//        progressBar.
        String token = User.userToken(this);
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, WALLET_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject object = response.getJSONObject("data");
                                Wallet.wallet.setCoins(object.getString("coins"));
                                Wallet.wallet.setUser_id(object.getString("user_id"));
                                Wallet.wallet.setId(object.getString("id"));
                            } else {

                                Toast.makeText(MainActivity.this, "" + response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.dismiss();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, " splash  onErrorResponse: " + error.getLocalizedMessage());
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty())
                    Toast.makeText(MainActivity.this, "" + error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
                progressBar.dismiss();
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}