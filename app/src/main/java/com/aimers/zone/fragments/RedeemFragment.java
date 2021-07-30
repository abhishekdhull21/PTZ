package com.aimers.zone.fragments;

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
import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.Utils.Utils.*;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class RedeemFragment extends Fragment {
    private Button btnRedeem;
    private EditText edtCoins,edtMobile,edtEmail;
    private View v;
    private Map<String, String> params;

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
        NetworkRequest request = new NetworkRequest(requireActivity());

        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
//        progressDialog.show();
        btnRedeem.setOnClickListener(v1 -> {
            progressDialog.show();
            if(getRedeemValue() != null) {
                if(!verifyRedeemCoin()){
                 alert("Not enough coins","coin should be 50",requireActivity(),false);
                 progressDialog.dismiss();
                 return;
                }
                request.sendRequest(getRedeemValue(), TEST_URL, new RedeemRequestResponse() {
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
        });
        return v;
    }

    private void showResponse(JSONObject response){
        try {
            if (response.getBoolean("success")){
                alert("Success","Your request submitted wait 3-5 working days",requireActivity(),true);
            }else{
                alert("Failed","error: "+response.getString("error"),requireActivity(),false);
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

        params  = new HashMap<>();
        params.put("token", User.userToken(requireActivity()));
        params.put("mobile",mobile);
        params.put("email",email);
        params.put("coins",coins);
        return params;
    }
    private boolean verifyRedeemCoin(){
        int coin = Integer.parseInt(wallet.getCoins());
        Log.d(TAG, "verifyRedeemCoin: "+coin);
        return coin >= 50;
    }
}