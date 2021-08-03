package com.aimers.zone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aimers.zone.Interface.RedeemRequestResponse;
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
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class TransactionWalletFragment extends Fragment {
    private View v;
    private DataTable dataTable;
    ImageView img;
    public TransactionWalletFragment() {
        // Required empty public constructor
    }


    public static TransactionWalletFragment newInstance(String param1) {return new TransactionWalletFragment();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_transaction_wallet, container, false);
//        img = v.findViewById(R.id .img_no_found);
        initTable();
        sendRequest();


        return v;
    }
    private void initTable(){
        dataTable = v.findViewById(R.id.data_table);
        Log.d(TAG, "initTable: "+dataTable);
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Sn",1)
                .item("Type",3)
                .item("Rs",2)
                .build();
        dataTable.setHeader(header);
    }

    private void sendRequest(){
        Map<String,String> params = initRequest();
        NetworkRequest request = new NetworkRequest(requireActivity());
        request.sendRequest(params, TEST_URL, new RedeemRequestResponse() {
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
        ArrayList<DataTableRow> rows = new ArrayList<>();
        // define 200 fake rows for table
        for(int i=0;i<responseArray.length();i++) {
            JSONObject data = responseArray.getJSONObject(i);
            DataTableRow row = new DataTableRow.Builder()
                    .value("#" + i)
                    .value(data.getString("type"))
                    .value(data.getString("coins"))

                    .build();
            rows.add(row);
        }

//        dataTable.setTypeface(typeface);

        dataTable.setRows(rows);
        dataTable.inflate(requireActivity());

    }
    private Map<String, String> initRequest(){
        Map<String, String> params = new HashMap<>();
        params.put("token", User.userToken(requireActivity()));
        return params;
    }
}