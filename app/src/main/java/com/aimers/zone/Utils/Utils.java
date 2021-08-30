package com.aimers.zone.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.R;
import com.aimers.zone.SignActivity;
import com.tapadoo.alerter.Alerter;

import static com.aimers.zone.Utils.Constant.LOGOUT_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
    public static void removeTokenLocal(SharedPreferences sharedPreferences, Activity context){
        if(sharedPreferences.contains("token")){
            String token = sharedPreferences.getString("token","");
            NetworkRequest request = new NetworkRequest(context);
            HashMap<String,String> map = new HashMap<>();
            map.put("token",token);
            request.sendRequest(map, LOGOUT_URL, new RedeemRequestResponse() {
                @Override
                public void onSuccessResponse(JSONObject response) throws JSONException {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("token");
                    editor.clear();
                    editor.apply();
                    Intent i =new Intent(context, SignActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(i);
                }

                @Override
                public void onErrorResponse(JSONObject response) throws JSONException {
                    Toast.makeText(context,"Something went wrong, Please restart app",Toast.LENGTH_SHORT).show();
                }
            });

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
