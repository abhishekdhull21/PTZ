package com.aimers.zone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.aimers.zone.Utils.UserInfo;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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
        private final Context context;
        private final Intent i;
        private final boolean iSLogin;

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