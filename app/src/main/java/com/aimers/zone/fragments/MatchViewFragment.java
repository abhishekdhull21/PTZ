package com.aimers.zone.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimers.zone.Modals.OfferModal;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aimers.zone.Adapters.MatchViewAdapter;
import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.MATCH_STATUS;
import static com.aimers.zone.Utils.Constant.MATCH_URL;
import static com.aimers.zone.Utils.Utils.*;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class MatchViewFragment extends Fragment {
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView msg;
    private final ArrayList <MatchModal> match = new ArrayList<>();
    private  final HashMap<String, OfferModal> offerModalHashMap = new HashMap<>();
    private static  GameModal game;
    private  int pos=1;
    private Activity mActivity;
    private SwipeRefreshLayout swipeRefreshLayout;
    public MatchViewFragment(GameModal param1, int pos) {
        game = param1;
       this.pos = pos;
    }

//    public MatchViewFragment() {
//    }

    public static MatchViewFragment newInstance(GameModal param1, int pos) {
        return new MatchViewFragment(param1,pos);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_match_virew, container, false);
        mActivity= requireActivity();
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        Log.d(TAG, "onCreateView: "+game.getTitle());
        queue = Volley.newRequestQueue(mActivity);
        recyclerView= v.findViewById(R.id.match_view_recyler);
        imageView = v.findViewById(R.id.imageViewFragMatchView);
        msg = v.findViewById(R.id.textViewFragMatchView);
                getMatchInfo(pos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMatchInfo(pos);
            }
        });
        return v;
    }

    public void getMatchInfo(int pos){
        Map<String,String> map = new HashMap<>();
        map.put("game_id",game.getId());
        map.put("status",MATCH_STATUS[pos]);
        match.clear();
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,MATCH_URL,new JSONObject(map),
                response -> {
                    try {

                        if (response.getBoolean("success")){
                                Log.d("TAGa", "onResponse matches: "+response);
//                                String img_url = response.getString("img_url");
                            JSONArray data = response.getJSONArray("data");
                            JSONArray offers = response.getJSONArray("offers");
//                            offerModalHashMa null;
                            for(int i =0; i<offers.length(); i++){

                                JSONObject offersJSONObject = offers.getJSONObject(i);
                                String offerMatch_id = offersJSONObject.getString("match_id");
                                offerModalHashMap.put(offerMatch_id,
                                        new OfferModal(
                                                !offersJSONObject.getString("heading").equals("")?offersJSONObject.getString("heading"):"",
                                                !offersJSONObject.getString("body").equals("")?offersJSONObject.getString("body"):"",
                                                offerMatch_id

                                        )
                                        );
                            }
                            for (int i=0;i<data.length();i++) {
                                Log.d(TAG, "onResponse: "+ data.getString(i));
                                JSONObject object = data.getJSONObject(i);
                                String dataMatch_id = object.getString("match_id");
                                match.add(new MatchModal(pos,dataMatch_id,
                                        object.getString("game_id"),
                                        object.getString("match_date"),
                                        object.getString("match_time"),
                                        object.getString("prize_pool"),
                                        object.getString("per_kill"),
                                        object.getString("entry_fee"),
                                        object.getString("type"),
                                        object.getString("version"),
                                        object.getString("map"),
                                        object.getString("total_slot"),
                                        object.getString("alloted_slot"),
                                        object.getString("remaining_slot"),
                                        !object.getString("yt").equals("") ?object.getString("yt"):null,
                                        game.getPic(),
                                        !object.getString("first_prize").equals("") ?object.getString("first_prize"):"00"
                                        ));
                                if (offerModalHashMap.containsKey(dataMatch_id))
                                    match.get(i).setOffers(offerModalHashMap.get(dataMatch_id));
                            }
//                                Log.d(TAG, "onResponse: after p"+response.length());
                            sendToAdapter(match);

                        }
                        /*else {
                            alert("error that hate ",response.getString("error"),requireActivity(),false);
//                                Toast.makeText(requireActivity(), response.getString("error"), Toast.LENGTH_LONG).show();
                                    group.setVisibility(View.VISIBLE);
                        }*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                        Toast.makeText(requireActivity(), ""+response, Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }, error -> {

                    msg.setText(R.string.error_occurred);
                    Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage() );

            if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                        alert("","error occurred: try after sometime",mActivity,false);
    //                    Toast.makeText(requireActivity(), "", Toast.LENGTH_LONG).show();
                    else
                        alert("error",error.getLocalizedMessage(),mActivity,false);
    //                    Toast.makeText(requireActivity(), ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    public void sendToAdapter(ArrayList<MatchModal> map){
        if (map.isEmpty() || mActivity == null) return;
        recyclerView.setVisibility(View.VISIBLE);
        msg.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        recyclerView.setAdapter(new MatchViewAdapter(mActivity,map,game));
        LinearLayoutManager layoutManager =new LinearLayoutManager(mActivity);
        if(pos != 1){
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

    }


}