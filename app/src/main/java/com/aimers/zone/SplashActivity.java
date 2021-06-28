package com.aimers.zone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.aimers.zone.Utils.UserInfo;

import static com.aimers.zone.fragments.RegisterFragment.TAG;
import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
//        Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
        Intent i =new Intent(SplashActivity.this,SignActivity.class);
        if(sharedPreferences.contains("token")){
//            Log.d(TAG, "onCreate: "+sharedPreferences.getString("token",null));
            i = new Intent(SplashActivity.this,MainActivity.class);
            UserInfo userInfo = new UserInfo(sharedPreferences.getString("token",null));
            i.putExtra("token",userInfo);
        }
        new Thread(new Wait(this,i)).start();


//         close splash activity


    }
    public void finishActivity(){
        finish();
    }
    private class Wait implements Runnable{
        private Context context;
        private  Intent i;
        Wait(Context context, Intent i){
            this.context = context;
            this.i = i;
        }
        @Override
        public void run() {
            try {
                for(int i=0;i<3;i++)
                sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(i);
            finishActivity();
        }
    }
}