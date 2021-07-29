package com.aimers.zone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.Utils;
import com.aimers.zone.databinding.ActivityMatchJoin2Binding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.MATCH_JOIN_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MatchJoinActivity extends AppCompatActivity {
    private ActivityMatchJoin2Binding binding;
    private MatchModal match;
    private AlertDialog dialog;
    private TextInputEditText edtName,edtID;
    private String pID,pName;
    private ProgressDialog progressDialog;
    private Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchJoin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        progressDialog = new ProgressDialog(this);
        Intent i= getIntent();
        match = (MatchModal) i.getSerializableExtra("match");
        setViewContent();
        LayoutInflater inflater = getLayoutInflater();
        View mDialog = inflater.inflate(R.layout.custom_dialog_input_player,null);
        initView(mDialog);
        playerInfoPopup(mDialog);
        Button join = mDialog.findViewById(R.id.buttonJoinMatch);

        join.setOnClickListener(v -> {
            if (inputValid()){
                joinMatch();
            } else
            Utils.alert("Complete before proceed","Enter Player Name and Player ID of game ",this,false);
        });
        binding.btnJoinMatch.setOnClickListener(v -> dialog.show());
    }
    private void setViewContent(){
        if(match == null)return;
//        binding.txt1stPrize.setText(match.getPrize());
        binding.txtTotalPrizePool.setText(match.getPrize_pool());
        binding.txtPerKillPrize.setText(match.getPer_kill());
//        Glide.with(this).load(match.getImageUrl()).into(binding.imageViewMatchMain);
//        binding.txtPassword.setText(match.getRoomPassword());
//        binding.txtRoomId.setText(match.getRoomId());

    }
    private void playerInfoPopup(View v){
        dialog = new AlertDialog.Builder(this)
                .setTitle("Game Player ID and Player Name")
                .setView(v)
                .create();

    }
    private boolean inputValid(){

        if (!edtID.getText().toString().equals("") && !edtName.getText().toString().equals("")){
            pID = edtID.getText().toString();
            pName = edtName.getText().toString();
            return true;
        }
        return false;
    }
    private void initView(View v){
        edtID = v.findViewById(R.id.txt_player_id);
        edtName = v.findViewById(R.id.txt_player_name);
    }
    private void joinMatch(){
        String token = User.userToken(this);
        Map<String,String> params = new HashMap<>();
        params.put("token",token);
        params.put("match_id",match.getMatch_id());
        params.put("user_game_id", pID);
        params.put("username", pName);
        sendRequestForMatchJoin(params);
    }
    public void sendRequestForMatchJoin(Map<String, String> params){
        progressDialog.setTitle("Request in progress");
        progressDialog.show();
        dialog.cancel();
        NetworkRequest request = new NetworkRequest(this);
        request.sendRequest(params, MATCH_JOIN_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                progressDialog.dismiss();
                Log.e(TAG, "onSuccessResponse: " + response);
                if (response.getBoolean("success")) {
                    Utils.alert("Congrats","Join Match Successfully...",context,true);
                } else {
                    Utils.alert("Failed",response.getString("error"),context,false);
                }
            }
            @Override
            public void onErrorResponse(JSONObject response) {
                progressDialog.dismiss();
                Log.e(TAG, "onErrorResponse: "+response );
                Utils.alert("Error","please try after some moment",context,false);
            }
        });
    }
}