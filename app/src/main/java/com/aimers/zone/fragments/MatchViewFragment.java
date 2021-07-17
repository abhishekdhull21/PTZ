package com.aimers.zone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aimers.zone.Utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    private final ArrayList <MatchModal> match = new ArrayList<>();
    private static  GameModal game;
    private final int pos;

    public MatchViewFragment(GameModal param1, int pos) {
        game = param1;
       this.pos = pos;
    }


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
        Log.d(TAG, "onCreateView: "+game.getTitle());
        queue = Volley.newRequestQueue(requireActivity());
        View v = inflater.inflate(R.layout.fragment_match_virew, container, false);
        recyclerView= v.findViewById(R.id.match_view_recyler);

        getMatchInfo(pos);
        return v;
    }

    public void getMatchInfo(int pos){
        Map<String,String> map = new HashMap<>();
        map.put("game_id",game.getId());
        map.put("status",MATCH_STATUS[pos]);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,MATCH_URL,new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")){
//                                Log.d("TAGa", "onResponse: "+response);
//                                String img_url = response.getString("img_url");
                                JSONArray data = response.getJSONArray("data");

                                for (int i=0;i<data.length();i++) {
                                    Log.d(TAG, "onResponse: "+ data.getString(i));
                                    JSONObject object = data.getJSONObject(i);
                                    match.add(new MatchModal(pos,
                                            object.getString("match_id"),
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
                                            object.getString("remaining_slot")
                                            ));
                                }
//                                Log.d(TAG, "onResponse: after p"+response.length());
                                sendToAdapter(match);

                            }
                            else {
                                alert("error that hate ",response.getString("error"),requireActivity(),false);
//                                Toast.makeText(requireActivity(), response.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(requireActivity(), ""+response, Toast.LENGTH_SHORT).show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error.getLocalizedMessage() );
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                    alert("","error occurred: try after sometime",requireActivity(),false);
//                    Toast.makeText(requireActivity(), "", Toast.LENGTH_LONG).show();
                else
                    alert("error",error.getLocalizedMessage(),requireActivity(),false);
//                    Toast.makeText(requireActivity(), ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

      public void sendToAdapter(ArrayList<MatchModal> map){
        recyclerView.setAdapter(new MatchViewAdapter(requireActivity(),map,game));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}