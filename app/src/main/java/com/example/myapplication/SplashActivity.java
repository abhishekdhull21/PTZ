package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.myapplication.Utils.UserInfo;

import static com.example.myapplication.fragments.RegisterFragment.TAG;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
        Intent i =new Intent(SplashActivity.this,SignActivity.class);
        if(sharedPreferences.contains("token")){
            Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
            i = new Intent(SplashActivity.this,MainActivity.class);
            UserInfo userInfo = new UserInfo(sharedPreferences.getString("token",null));
            i.putExtra("token",userInfo);
        }
        startActivity(i);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//         close splash activity
        finish();
    }
}