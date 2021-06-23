package com.example.myapplication.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;

import static com.example.myapplication.Utils.Constant.LOGIN_URL;
import static com.example.myapplication.Utils.Constant.REGISTER_URL;
import static com.example.myapplication.fragments.RegisterFragment.TAG;


public class User {
    private RequestQueue queue;
    private  Context context;

    public User(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(this.context);
    }


    public void register(Map<String, String>params, ProgressDialog dialog) {

        dialog.dismiss();
        if (params == null) return;
        dialog.setMessage("Wait little bit...");
        dialog.show();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,REGISTER_URL,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success"))
                                Log.d("TAG", "onResponse: "+response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage() );
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                    Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void login(Map<String, String>params, ProgressDialog dialog) {

        dialog.dismiss();
        if (params == null) return;
        dialog.setMessage(" login proceeding...");
        dialog.show();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,LOGIN_URL,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")){
                                Log.d("TAG", "onResponse: "+response);
                                Intent i = new Intent(context, MainActivity.class);
                                UserInfo userInfo = new UserInfo(response.getString("token"));
                                i.putExtra("user", userInfo);
                                context.startActivity(i);
                            }
                            else {

                                Toast.makeText(context, response.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                        Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage() );
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                    Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
