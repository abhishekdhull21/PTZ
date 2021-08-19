package com.aimers.zone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MatchResultActivity extends AppCompatActivity {
    private DataTable dataTable;
    private NetworkRequest request;
    private MatchModal match;
    private ImageView img;
    ArrayList<MatchResultModal> matchResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
         match = (MatchModal) getIntent().getSerializableExtra("match");
//         img = findViewById(R.id.img_not);
        request = new NetworkRequest(this);

        initRequest();

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
                    // define 200 fake rows for table
                    Log.e(TAG, "onSuccessResponse:"+responseArray );
                    for(int i=0;i<responseArray.length();i++) {
                        JSONObject data = responseArray.getJSONObject(i);
                                matchResults.add(new MatchResultModal(
                                    data.getString("username"),
                                    data.getString("kills"),
                                    data.getString("win"),
                                    data.getString("position")));
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
//    private void customActionbar() {
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar == null)return;
//        Log.e(TAG, "customActionbar: main activity" );
//        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        View view = getSupportActionBar().getCustomView();
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        ImageView notification = view.findViewById(R.id.notification_custom_navbar);
////        notification.setOnClickListener(this);
//    }
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
}