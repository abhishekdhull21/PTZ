package com.aimers.zone.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Interface.UsersFromServer;
import com.aimers.zone.MyMatchActivity;
import com.aimers.zone.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.aimers.zone.Utils.Constant.MATCH_JOIN_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class NetworkRequest {
    Activity context;
    RequestQueue queue;
    public NetworkRequest(Activity context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }
    public void sendRequest(Map<String, String> params, String url, RedeemRequestResponse redeemRequestResponse){
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url,new JSONObject(params),
                response -> {
                    try {
                        redeemRequestResponse.onSuccessResponse(response);
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
    }
}
