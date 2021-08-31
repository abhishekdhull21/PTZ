package com.aimers.zone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.aimers.zone.Adapters.MatchResultAdapter;
import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.Modals.MatchResultModal;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ir.androidexception.datatable.DataTable;
import ir.androidexception.datatable.model.DataTableHeader;
import ir.androidexception.datatable.model.DataTableRow;

import static com.aimers.zone.Utils.Constant.MATCH_RESULT_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;
import static com.aimers.zone.wallet.Wallet.fetch;

public class MatchResultActivity extends AppCompatActivity implements View.OnClickListener {
    private DataTable dataTable;
    private NetworkRequest request;
    private MatchModal match;
    private ImageView img;
    private Toolbar toolbar;
    private TextView mName,mTime,mStatus,mNote;
    HashMap<String,String> mInfo = new HashMap<>();
    ArrayList<MatchResultModal> matchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);

        customActionbar();

        mName = findViewById(R.id.textView45MatchName);
        mTime = findViewById(R.id.textViewMatchTime);
         match = (MatchModal) getIntent().getSerializableExtra("match");
         mStatus = findViewById(R.id.textView48MatchStatus);
         mNote = findViewById(R.id.textView49CustomMessage);
//         img = findViewById(R.id.img_not);
        mName.setText(String.format("Match: %s", match.getMatch_id()));
        mTime.setText(String.format("%s at %s", match.getMatch_date(), match.getMatch_time()));
        request = new NetworkRequest(this);

        initRequest();

    }
    private void updateMatch(){
        if (mInfo.isEmpty()) return;

        mStatus.setText(mInfo.get("status"));
        mNote.setText(mInfo.get("note"));
    }
    private void initRequest() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", User.userToken(this));
        params.put("match_id",match.getMatch_id());
//        params.put("match_id","7");
        Log.e(TAG, "initRequest: "+match.getMatch_id() );
        matchResults = new ArrayList<>();
        request.sendRequest(params, MATCH_RESULT_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                Log.e(TAG, "onSuccessResponse: "+response );
                if(response.getBoolean("success")){

                    JSONArray responseArray = response.getJSONArray("data");
                    JSONObject info = response.getJSONObject("info");
                    mInfo.put("status", info.getString("status"));
                    mInfo.put("note", info.getString("note"));
                    updateMatch();
                    // define 200 fake rows for table
                    Log.e(TAG, "onSuccessResponse:"+responseArray );
                    for(int i=0;i<responseArray.length();i++) {
                        JSONObject data = responseArray.getJSONObject(i);
                                matchResults.add(new MatchResultModal(
                                    data.getString("username"),
                                    data.getString("kills"),
                                    data.getString("win"),
                                    data.getString("position"),
                                    data.getString("remarks")));

                    }
                        showTable(matchResults);
                    }
            }

            @Override
            public void onErrorResponse(JSONObject response) {
                img.setVisibility(View.VISIBLE);
            }
        });
    }
    private void customActionbar() {
        toolbar = findViewById(R.id.toolbar_matchresult);
        setSupportActionBar(toolbar);
    }
    private void showTable(ArrayList<MatchResultModal> matchResultModals) {
        if (matchResultModals == null) return;
        RecyclerView recyclerView = findViewById(R.id.recycleViewResult);
        ImageView img = findViewById(R.id.imageViewMatcResult404);
        TextView textView = findViewById(R.id.textView42);
        recyclerView.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        recyclerView.setAdapter(new MatchResultAdapter(matchResultModals,this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onClick(View view) {

    }
}