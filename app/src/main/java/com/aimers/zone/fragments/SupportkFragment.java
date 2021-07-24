package com.aimers.zone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.Utils.Utils.*;

public class SupportkFragment extends Fragment {
    private Button btnSubmit;
    private EditText txtQuery;
    private View v;
    private NetworkRequest request;
    public SupportkFragment() {
        // Required empty public constructor
    }


    public static SupportkFragment newInstance() {
        return new SupportkFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_supportk, container, false);
        btnSubmit = v.findViewById(R.id.btnQuerySubmit);
        txtQuery = v.findViewById(R.id.txtQuery);
        request = new NetworkRequest(getActivity());
        btnSubmit.setOnClickListener(v -> {
            sendRequest();
        });
        return v;
    }
    public void  sendRequest(){
        String query = txtQuery.getText().toString();
        HashMap<String, String> params = new HashMap<>();
        params.put("token", User.userToken(requireActivity()));

        if(!query.isEmpty()){
            params.put("query",query);
            request.sendRequest(params, TEST_URL, new RedeemRequestResponse() {
                @Override
                public void onSuccessResponse(JSONObject response) throws JSONException {
                    alert("Success","Query Submitted",requireActivity(),true);
                }

                @Override
                public void onErrorResponse(JSONObject response) throws JSONException {
                    alert("Error",""+response.getString("error"),requireActivity(),false);

                }
            });
        }
    }
}