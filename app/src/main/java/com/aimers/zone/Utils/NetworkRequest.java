package com.aimers.zone.Utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class NetworkRequest {
    final Activity context;
    final RequestQueue queue;
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

//                    Log.d(TAG, " userfromserver  onErrorResponse: "+error.getLocalizedMessage() );
                    if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                        Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
                });
        queue.add(stringRequest);
    }
}
