package com.aimers.zone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

import static com.aimers.zone.Utils.Constant.MATCH_RESULT_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MatchResultActivity extends AppCompatActivity {
    private DataTable dataTable;
    private NetworkRequest request;
    private MatchModal match;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
         match = (MatchModal) getIntent().getSerializableExtra("match");
//         img = findViewById(R.id.img_not);
        request = new NetworkRequest(this);
        initTable();
        initRequest();

    }

    private void initRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", User.userToken(this));
        params.put("match_id",match.getMatch_id());
        request.sendRequest(params, MATCH_RESULT_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                showTable(response);
            }

            @Override
            public void onErrorResponse(JSONObject response) {
                img.setVisibility(View.VISIBLE);
            }
        });
    }
    private void initTable(){
        dataTable = findViewById(R.id.data_table);
        Log.d(TAG, "initTable: "+dataTable);
        DataTableHeader header = new DataTableHeader.Builder()
                .item("Sn",1)
                .item("Player Name",3)
                .item("Total Kill",2)
                .item("Win",2)
                .build();
        dataTable.setHeader(header);
    }
    private void showTable(JSONObject response) throws JSONException {
        if(!response.getBoolean("success")){
//            img.setVisibility(View.VISIBLE);
            return;
        }
        JSONArray responseArray = response.getJSONArray("data");
        ArrayList<DataTableRow> rows = new ArrayList<>();
        // define 200 fake rows for table
        for(int i=0;i<responseArray.length();i++) {
            JSONObject data = responseArray.getJSONObject(i);
            DataTableRow row = new DataTableRow.Builder()
                    .value("#" + i)
                    .value(data.getString("username"))
                    .value(data.getString("total_kill"))
                    .value(data.getString("win"))

                    .build();
            rows.add(row);
        }

//        dataTable.setTypeface(typeface);

        dataTable.setRows(rows);
        dataTable.inflate(this);

    }
}