package com.aimers.zone.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Modals.Wallet.*;
import static com.aimers.zone.Utils.Constant.GET_REDEEM_VALUE;
import static com.aimers.zone.Utils.Constant.REDEEM_REQUEST;
import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.Utils.Utils.*;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class RedeemFragment extends Fragment {
    private Button btnRedeem;
    private EditText edtCoins,edtMobile,edtEmail;
    private View v;
    private Map<String, String> params;
    private int redeemValue=200;
    NetworkRequest request;
    private Activity mActivity;

    public RedeemFragment() {
        // Required empty public constructor
    }

    public static RedeemFragment newInstance(String param1, String param2) {
        return new RedeemFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_redeem, container, false);
        init();
        mActivity = requireActivity();
         request = new NetworkRequest(mActivity);

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
//        progressDialog.show();
        btnRedeem.setOnClickListener(v1 -> {
            progressDialog.show();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Processing redeem request");
            redeemValue(progressDialog);

        });
        return v;
    }

    private void showResponse(JSONObject response){
        try {
            if (response.getBoolean("success")){
                alert("Success","Your request submitted wait 3-5 working days",mActivity,true);
            }else{
                alert("Failed","error: "+response.getString("error"),mActivity,false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void init(){
        btnRedeem = v.findViewById(R.id.btn_redeem);
        edtCoins = v.findViewById(R.id.edtxt_coins);
        edtEmail = v.findViewById(R.id.edtxt_email);
        edtMobile = v.findViewById(R.id.edtxt_mobile);
    }
    private Map<String,String> getRedeemValue() {
        String coins = edtCoins.getText().toString(),
                mobile =edtMobile.getText().toString(),
                email = edtEmail.getText().toString();
        if (coins.isEmpty() && mobile.isEmpty() && email.isEmpty()){
            alert("Complete form to proceed","all field mandatory",requireActivity(),false);
            return null;
        }
        if(Integer.parseInt(coins) < redeemValue){
            alert("Error ","Redeem coins value should be "+redeemValue,mActivity,false);
            return null;
        }
        params  = new HashMap<>();
        params.put("token", User.userToken(mActivity));
        params.put("mobile",mobile);
        params.put("email",email);
        params.put("coins",coins);
        return params;
    }
    private boolean verifyRedeemCoin(){
        int coin = Integer.parseInt(wallet.getCoins()!=null?wallet.getCoins():"0");
        Log.d(TAG, "verifyRedeemCoin: "+coin);
        return coin >= redeemValue;
    }

    private void redeemValue(ProgressDialog progressDialog){
        HashMap<String,String> map = new HashMap<>();
        map.put("key","transaction value");
        request.sendRequest(map, GET_REDEEM_VALUE, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                progressDialog.dismiss();
                if (response.getBoolean("success")){
                    redeemValue = Integer.parseInt(response.getString("rs"));
                    if(getRedeemValue() != null) {
                        if(!verifyRedeemCoin()){
                            alert("Not enough coins","coin should be "+redeemValue ,mActivity,false);
                            progressDialog.dismiss();
                            return;
                        }
                        request.sendRequest(getRedeemValue(), REDEEM_REQUEST, new RedeemRequestResponse() {
                            @Override
                            public void onSuccessResponse(JSONObject response) {
                                showResponse(response);
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onErrorResponse(JSONObject response) {
                                progressDialog.dismiss();

                            }
                        });
                    }
                }
                else {
                    alert("Error",response.getString("error"),mActivity,false);
                }
            }

            @Override
            public void onErrorResponse(JSONObject response) throws JSONException {
                progressDialog.dismiss();
            }
        });
    }
}