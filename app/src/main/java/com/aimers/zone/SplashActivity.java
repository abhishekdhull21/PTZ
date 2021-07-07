package com.aimers.zone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.aimers.zone.Modals.Wallet;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.UserInfo;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.GET_USER_INFO;
import static com.aimers.zone.Utils.Constant.WALLET_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("token", MODE_PRIVATE);
//        Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
        Intent i = new Intent(SplashActivity.this, SignActivity.class);
        boolean iSLogin = false;
        if (sharedPreferences.contains("token")) {
//            Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
            i = new Intent(SplashActivity.this, MainActivity.class);
            UserInfo userInfo = new UserInfo(sharedPreferences.getString("token", null));
            i.putExtra("token", userInfo);
            iSLogin = true;
        }
        new Thread(new Wait(this, i, iSLogin)).start();


//         close splash activity


    }

    public void finishActivity() {
        finish();
    }

    private class Wait implements Runnable {
        private Context context;
        private Intent i;
        private boolean iSLogin;

        Wait(Context context, Intent i, boolean iSLogin) {
            this.context = context;
            this.i = i;
            this.iSLogin = iSLogin;
        }

        @Override
        public void run() {
            if (iSLogin) {
                RequestQueue queue = Volley.newRequestQueue(context);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(i);

            }
            else
                startActivity(new Intent(SplashActivity.this,SignActivity.class));
            finishActivity();
        }

    }
}