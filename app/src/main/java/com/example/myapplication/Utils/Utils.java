package com.example.myapplication.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

public class Utils {
    @SuppressLint("HardwareIds")
    public static String androidId(Context context){
        return Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

}
