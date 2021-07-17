package com.aimers.zone.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.R;
import com.aimers.zone.R.color;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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
    private EditText editTextGame,editTextName;
    private Button btnJoin;
    private TextView txt_loading_status;
    public MatchJoinActivity(Context context, MatchModal match) {
        this.context = context;
        this.match = match;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_match_join,container,false);

        editTextGame = v.findViewById(R.id.txtUserGameID);
        editTextName = v.findViewById(R.id.edt_username);
         btnJoin =v.findViewById(R.id.join_req_btn);
        txt_loading_status =v.findViewById(R.id.txt_loading_status);
        Log.e(TAG, "onCreateView: "+match.getMatch_id() );
        btnJoin.setOnClickListener(v1 -> {
            btnJoin.setActivated(false);
            Log.d(TAG, "onClick: bottom sheet btn clicked");

            joinMatchRequest();
        });

        return v;
    }
    public void joinMatchRequest( ){
        btnJoin.setEnabled(false);
        txt_loading_status.setVisibility(View.VISIBLE);
        String token = User.userToken(context);
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("match_id",match.getMatch_id());
        String game_id = editTextGame.getText().toString();
        String user_name = editTextName.getText().toString();
        if(!game_id.isEmpty() && !user_name.isEmpty()) {
            params.put("user_game_id", game_id);
            params.put("username", user_name);
            sendRequestForMatchJoin(params);
        }


    }

    public void sendRequestForMatchJoin(Map<String, String> params){

        NetworkRequest request = new NetworkRequest(requireActivity());
        request.sendRequest(params, MATCH_JOIN_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                Log.e(TAG, "onSuccessResponse: "+response );
                if (response.getBoolean("success")){
                             /*   JSONObject object = response.getJSONObject("data");
//                                Alerter.create(requireActivity())
//                                        .setTitle("Congrats")
//                                        .setBackgroundColorRes(R.color.colorBackgroundSuccess)
//                                        .setText("Join Match Successfully...")
//                                        .show();
*/

                    txt_loading_status.setText(R.string.joind_true);
                    txt_loading_status.setTextColor(getResources().getColor(R.color.colorBackgroundSuccess));

//                    startActivity(new Intent(requireActivity(), MyMatchActivity.class));
                            }else{


                    txt_loading_status.setText(String.format("%s", response.getString("error")));
                    txt_loading_status.setTextColor(getResources().getColor(color.colorBackgroundError));
//                                Toast.makeText(context, ""+response.getString("error"), Toast.LENGTH_SHORT).show();
//                                Alerter.create(requireActivity())
//                                        .setTitle("Unluckily, failed")
//                                        .setBackgroundColorRes(R.color.colorBackgroundError)
//                                        .setText(response.getString("error"))
//                                        .show();

                            }
            }

            @Override
            public void onErrorResponse(JSONObject response) {
                Log.e(TAG, "onErrorResponse: "+response);
                //                    Toast.makeText(context, ""+response.getString("error"), Toast.LENGTH_SHORT).show();

                txt_loading_status.setText(String.format("failed..%s", response));
                txt_loading_status.setTextColor(getResources().getColor(color.colorBackgroundError));
                /*  try {
                    Alerter.create(requireActivity())
                            .setTitle("Unluckily, failed")
                            .setBackgroundColorRes(R.color.colorBackgroundError)
                            .setText(response.getString("error"))
                            .show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
//        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,MATCH_JOIN_URL,new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//                        try {
//                            if (response.getBoolean("success")){
//                                JSONObject object = response.getJSONObject("data");
//                                Alerter.create(requireActivity())
//                                        .setTitle("Congrats")
//                                        .setBackgroundColorRes(R.color.colorBackgroundSuccess)
//                                        .setText("Join Match Successfully...")
//                                        .show();
//                                startActivity(new Intent(requireActivity(), MyMatchActivity.class));
//                            }else{
//
//
//                                Toast.makeText(context, ""+response.getString("error"), Toast.LENGTH_SHORT).show();
//                                Alerter.create(requireActivity())
//                                        .setTitle("Unluckily, failed")
//                                        .setBackgroundColorRes(R.color.colorBackgroundError)
//                                        .setText(response.getString("error"))
//                                        .show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Log.d(TAG, " userfromserver  onErrorResponse: "+error.getLocalizedMessage() );
//                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
//                    Toast.makeText(context, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(context, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
    }


}