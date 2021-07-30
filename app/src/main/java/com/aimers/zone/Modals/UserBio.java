package com.aimers.zone.Modals;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aimers.zone.Interface.UsersFromServer;
import com.aimers.zone.Utils.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.GET_USER_INFO;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

@SuppressWarnings("SameReturnValue")
public class UserBio {
    static RequestQueue queue;
   private String uid,username,name,lastName,mobile,email,address, dob,gender,coins,kills,match_played;

    public static final UserBio user = new UserBio();

    public String getMatch_played() {
        return match_played;
    }

    public void setMatch_played(String match_played) {
        this.match_played = match_played;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public void setUid(String uid) {
        this.uid = uid;

    }

    public UserBio() {

    }

    public String getUid() {
        return uid;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public static boolean UserFromServer(Context context, UsersFromServer fromServer){

        queue = Volley.newRequestQueue(context);
        String token = User.userToken(context);
       Map<String,String> params = new HashMap<>();
        params.put("token",token);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,GET_USER_INFO,new JSONObject(params),
                response -> {
                    try {
                        if (response.getBoolean("success")){
                            JSONObject object = response.getJSONObject("data");
                            String[] name = object.getString("name").split(" ",1);

                            user.setUid( object.getString("uid"));
                             user.setName(name[0]);
                             user.setMobile(object.getString("mobile"));
                            if(name.length >1)
                                user.setLastName(name[1]);
                            user.setAddress(object.getString("address"));
                            user.setEmail(object.getString("email"));
                            user.setGender(object.getString("gender"));
                            user.setDob(object.getString("dob"));
                            user.setCoins(object.getString("coins"));
                            user.setKills(object.getString("kills"));
                            user.setMatch_played(object.getString("match_played"));
                            fromServer.SetProfileUser();
                            Log.d("TAG", "onResponse Userbio: "+response);
                        }else{

                            Toast.makeText(context, ""+response.getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {

                    Log.d(TAG, " userfromserver  onErrorResponse: "+error.getLocalizedMessage() );
                    if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                        Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
        return true;
    }
}
