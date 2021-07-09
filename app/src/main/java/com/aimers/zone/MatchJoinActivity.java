package com.aimers.zone;

import androidx.annotation.Nullable;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.Utils.User;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.MATCH_JOIN_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MatchJoinActivity extends BottomSheetDialogFragment {
//    private BottomSheetListner
final MatchModal match;
    final Context context;
    private EditText editTextGame;

    public MatchJoinActivity(Context context, MatchModal match) {
        this.context = context;
        this.match = match;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_match_join,container,false);

        editTextGame = v.findViewById(R.id.txtUserGameID);
        Button btnJoin =v.findViewById(R.id.join_req_btn);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnJoin.setActivated(false);
                Log.d(TAG, "onClick: bottom sheet btn clicked");
                joinMatchRequest();
            }
        });
//        binding = ActivityMatchJoinBinding.inflate(getLayoutInflater());
//        View v = binding.getRoot();
//        setContentView(v);
//        match = (MatchModal) getIntent().getSerializableExtra("match");
//        v.findViewById(R.id.).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                joinMatchRequest();
//            }
//        });
        return v;
    }
    public void joinMatchRequest( ){
        String token = User.userToken(context);
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("match_id",match.getMatch_id());
        String game_id = editTextGame.getText().toString();
        if(!game_id.isEmpty()) {
            params.put("user_game_id", game_id);
            sendRequestForMatchJoin(params);
        }


    }

    public void sendRequestForMatchJoin(Map<String, String> params){

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,MATCH_JOIN_URL,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        try {
                            if (response.getBoolean("success")){
                                JSONObject object = response.getJSONObject("data");
                                Alerter.create(requireActivity())
                                        .setTitle("Congrats")
                                        .setBackgroundColorRes(R.color.colorBackgroundSuccess)
                                        .setText("Join Match Successfully...")
                                        .show();
                                startActivity(new Intent(requireActivity(),MyMatchActivity.class));
                            }else{


                                Toast.makeText(context, ""+response.getString("error"), Toast.LENGTH_SHORT).show();
                                Alerter.create(requireActivity())
                                        .setTitle("Unluckily, failed")
                                        .setBackgroundColorRes(R.color.colorBackgroundError)
                                        .setText(response.getString("error"))
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, " userfromserver  onErrorResponse: "+error.getLocalizedMessage() );
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                    Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}