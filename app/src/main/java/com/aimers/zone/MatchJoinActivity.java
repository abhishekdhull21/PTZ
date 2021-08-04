package com.aimers.zone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.Utils;
import com.aimers.zone.databinding.ActivityMatchJoin2Binding;
import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.GET_ROOM;
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
    private NetworkRequest request;
    private boolean isRoom=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatchJoin2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = this;
        progressDialog = new ProgressDialog(this);
        request = new NetworkRequest(this);
        Intent i= getIntent();
        match = (MatchModal) i.getSerializableExtra("match");
        setViewContent();
        getRoom();
        setRules();
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View mDialog = inflater.inflate(R.layout.custom_dialog_input_player,null);
        initView(mDialog);
        playerInfoPopup(mDialog);
        Button join = mDialog.findViewById(R.id.buttonJoinMatch);
        binding.copyId.setOnClickListener(v -> {
            if (isRoom)
            {
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(binding.txtRoomId.getText());
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }else{
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("Wait Until Room Credentials available");
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        binding.copyPassword.setOnClickListener(v -> {
            if (isRoom)
            {
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(binding.txtRoomPassword.getText());
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }else{
                ClipboardManager cm = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText("Wait Until Room Credentials available");
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
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
        Log.d(TAG, "setViewContent: "+match.getMatch_id() );
//        binding.txt1stPrize.setText(match.getPrize());
        binding.txtTotalPrizePool.setText(match.getPrize_pool());
        binding.txtPerKillPrize.setText(match.getPer_kill());
        Glide.with(this).load(match.getPic()).into(binding.imageViewMatchMain);


    }
    private void setRoom(String id, String password){

        binding.txtRoomPassword.setText(password);
        binding.txtRoomId.setText(id);
    }
    private void setRules(){
        TextView txt = binding.txtRules;
        txt.setText(Html.fromHtml("\"<p style='margin:0cm;margin-bottom:.0001pt;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;\">READ EACH RULE VERY CAREFULLY DON'T BLAME ROOM ORGANISERS OR ADMIN FOR ANY UNFOLLOWED RULE FROM YOUR SIDE  </span></strong></span></p>\n" +
                "<p style='margin:0cm;margin-bottom:.0001pt;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;\"></span></strong></span></p>\n" +
                "<h3 style='margin-top:2.0pt;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:107%;font-size:16px;font-family:\"Calibri Light\",\"sans-serif\";color:#1F3763;font-weight:normal;'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;line-height:107%;\">RuLes And Regulations ---&gt;</span></strong></span></h3>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'></p>\n" +
                "<ol style=\"list-style-type: undefined;margin-left:8px;\">\n" +
                "    <li>For <strong>BGMI   &amp; PUBG Lite</strong> Minimum 45 level id are allowed to participate in tournaments. If you still join the room <strong>No Refund</strong> will Be given.</li>\n" +
                "    <li>No <strong>Emulators</strong> are allowed.</li>\n" +
                "    <li>If we found Hacks or teaming up with other players permanent ban will be given.</li>\n" +
                "    <li>If we found sharing id and password with an unregistered player you will be lost your rewards and withdrawals also.</li>\n" +
                "    <li>Id &amp; password will upload <strong>15min</strong> <strong>Before</strong>.</li>\n" +
                "    <li>Once you join any match you will be allotted with your slot number make sure you join and hold your place before start time</li>\n" +
                "</ol>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'> </p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;\">NOTE -&gt;</span></strong></p>\n" +
                "<ol style=\"list-style-type: undefined;\">\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\"> If anyone is already sitting on your slot relax you can change it in squad matches please try to be as soon as possible to join the room with your team I can&apos;t help if any random player is popping and interrupting your team at the last minutes.</span></strong></li>\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\"> If there are fewer than 25 registration before 15 minutes or the meaning Faisal be reduced</span></strong></li>\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\">Fixed winning price will be given if registration is above 60%</span></strong></li>\n" +
                "</ol>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\">Some important points </span></strong><strong><span style=\"font-size:27px;line-height:107%;font-family:Wingdings;color:red;\">-></span></strong></p>\n" +
                "<ul style=\"list-style-type: disc;\">\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If you are found suspicious by the admin then you have to record every game you play on the app if any player refuses to do it so so and argues with the admin action will be taken.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If you are playing with the suspicious player or confirm the hacker winning be on hold it doesn&apos;t matter he is random or your friend.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If anyone of you fails to join the room by the match start time then we are not responsible for IT refund in such cases was not we proceed so make sure to join before time.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Do not share the ID and password with anyone who has not to join the match if you are founded doing so your account gets terminated and all the winning will be lost.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Please note that the list of today&apos;s entry fees is per individual player not for squad or Duo team.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Use only a mobile device to join match no emulator players are allowed.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Creating multi-fake accounts or adding external fake and trees male lead for a permanent ban from app.</span></li>\n" +
                "</ul>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\"\n" +
                "\n"));
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
    private void getRoom(){
        progressDialog.setTitle("Wait...");
        progressDialog.setMessage("Getting Room Credentials");
        HashMap<String,String> params = new HashMap<>();
        params.put("token",User.userToken(this));
        params.put("match_id",match.getMatch_id());
        request.sendRequest(params, GET_ROOM, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                Log.e(TAG, "onSuccessResponse: "+response );
                if (response.getBoolean("success")){

                    JSONObject obj = response.getJSONObject("data");
                    setRoom(obj.getString("id"),obj.getString("password"));
                    isRoom= true;
                }
            }

            @Override
            public void onErrorResponse(JSONObject response) throws JSONException {
                Utils.alert("Error Occurred","Please try after few couple of minutes",context,false);
            }
        });
    }
}