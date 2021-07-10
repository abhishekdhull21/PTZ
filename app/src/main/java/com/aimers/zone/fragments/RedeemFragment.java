package com.aimers.zone.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.Utils;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class RedeemFragment extends Fragment {



    public RedeemFragment() {
        // Required empty public constructor
    }

    public static RedeemFragment newInstance(String param1, String param2) {
        RedeemFragment fragment = new RedeemFragment();
        Bundle args = new Bundle();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_redeem, container, false);
        NetworkRequest request = new NetworkRequest(requireActivity());
        Context context;
        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
//        progressDialog.show();
        v.findViewById(R.id.btn_redeem)
            .setOnClickListener(v1 -> {

            progressDialog.show();
            request.sendRequest(null, TEST_URL, new RedeemRequestResponse() {
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
        });
        return v;
    }

    private void showResponse(JSONObject response){
        try {
            if (response.getBoolean("success")){
                Utils.alert("Success","Your request submitted wait 3-5 working days",requireActivity(),true);
            }else{
                Utils.alert("Failed","error: "+response.getString("error"),requireActivity(),false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}