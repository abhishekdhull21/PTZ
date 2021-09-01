package com.aimers.zone.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimers.zone.Adapters.TransactionAdapter;
import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.TransactionModal;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.Utils.Constant.TRANSACTION_LIST_REQUEST;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class TransactionWalletFragment extends Fragment {
    private View v;
    private ArrayList<TransactionModal> transactions;
    private ImageView img;
    private RecyclerView recyclerView;
    private Activity mActivity;
    private TextView txt404;

    public TransactionWalletFragment() {
        // Required empty public constructor
    }


    public static TransactionWalletFragment newInstance(String param1) {return new TransactionWalletFragment();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_transaction_wallet, container, false);
        recyclerView = v.findViewById(R.id.recyclerViewTransactionWallet);
        img = v.findViewById(R.id.imageViewTransaction404);
        txt404 = v.findViewById(R.id.textViewTransaction404);
        mActivity = requireActivity();
//        initTable();
        sendRequest();


        return v;
    }


    private void sendRequest(){
        Map<String,String> params = initRequest();
        if (params== null)return;
        NetworkRequest request = new NetworkRequest(requireActivity());
        request.sendRequest(params, TRANSACTION_LIST_REQUEST, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                showTable(response);
            }


            @Override
            public void onErrorResponse(JSONObject response) {
                Log.e(TAG, "onErrorResponse: "+response );
//                img.setVisibility(View.VISIBLE);
            }
        });
    }

    private void showTable(JSONObject response) throws JSONException {
        if(!response.getBoolean("success")){
            img.setVisibility(View.VISIBLE);
            return;
        }
        JSONArray responseArray = response.getJSONArray("data");
        img.setVisibility(View.GONE);
        txt404.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        // define 200 fake rows for table
        for(int i=0;i<responseArray.length();i++) {
            String sn = String.valueOf(i);
            JSONObject obj = responseArray.getJSONObject(i);
            transactions.add(new TransactionModal(sn,
                    obj.getString("type"),
                    obj.getString("rs"),
                    obj.getString("status"),
                    obj.getString("remarks")
                    ));
        }
        recyclerView.setAdapter(new TransactionAdapter(transactions));
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));


    }
    private Map<String, String> initRequest(){
        if (mActivity == null) return null;
        Map<String, String> params = new HashMap<>();

        params.put("token", User.userToken(mActivity));
        return params;
    }
}