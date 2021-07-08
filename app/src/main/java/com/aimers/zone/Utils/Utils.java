package com.aimers.zone.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.aimers.zone.R;
import com.tapadoo.alerter.Alerter;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class Utils {
    @SuppressLint("HardwareIds")
    public static String androidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }
//    user token saved to local storage and used later
    public static void saveTokenLocal(SharedPreferences sharedPreferences,String token){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token",token);
        if(editor.commit())
            Log.d(TAG, "saveTokenLocal: true");
        else
            Log.d(TAG, "saveTokenLocal: false");

    }
//remove token from user device , like when user hit logout or etc
    public static void removeTokenLocal(SharedPreferences sharedPreferences){
        if(sharedPreferences.contains("token")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("token");
            editor.clear();
            editor.apply();
        }
    }

    public static void alert(String title,String message,Activity context, boolean success){
        Alerter alerter = Alerter.create(context)
                .setText(message)
                .setTitle(title);
        if (success) {
            alerter.setBackgroundColorRes(R.color.colorBackgroundSuccess);
        } else {
            alerter.setBackgroundColorRes(R.color.alert_default_error_background);
        }
        alerter.show();
    }
}
