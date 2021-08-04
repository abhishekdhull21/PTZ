package com.aimers.zone.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aimers.zone.Adapters.GameViewAdapter;
import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.Modals.Notification;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.Utils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aimers.zone.MainActivity.notifications;
import static com.aimers.zone.Utils.Constant.BASE_URL1;
import static com.aimers.zone.Utils.Constant.GAME_URL;
import static com.aimers.zone.Utils.Constant.NOTIFICATION_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class GameFragment extends Fragment {
    private RequestQueue queue;
    private List<GameModal> games;
    private RecyclerView r;
    private TextView txt_notification;
    private CardView notification_cardview;
    private ProgressDialog progressDialog;
    public GameFragment() {
        // Required empty public constructor
    }
    public static GameFragment newInstance(String text) {
        return new GameFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game, container, false);
        queue = Volley.newRequestQueue(requireActivity());
        progressDialog = new ProgressDialog(requireActivity());
//        games.add(new GameModal());
        games = new ArrayList<GameModal>();
        r= v.findViewById(R.id.recycler_game);
        txt_notification = v.findViewById(R.id.txt_notification);
        notification_cardview = v.findViewById(R.id.notification_cardview);
        loadNotification();
        gameInfo();


        return v;
    }
    private void setViewNotification(){
        if(notifications==null)return;
        notification_cardview.setVisibility(View.VISIBLE);
        txt_notification.setText(notifications.get(0).getBody());
    }
    private void loadNotification(){
        Map<String, String> params = new HashMap<>();
        params.put("token", User.userToken(requireActivity()));
        NetworkRequest request = new NetworkRequest(requireActivity());
        request.sendRequest(params, NOTIFICATION_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                Log.e(TAG, "onSuccessResponse: notification"+response );
                setNotification(response);
            }
            @Override
            public void onErrorResponse(JSONObject response) {
                Log.e(TAG, "onErrorResponse: "+response );
            }
        });
    }

    private void setNotification(JSONObject response) throws JSONException {
        notifications = new ArrayList<>();
        if (!response.getBoolean("success")) return;
        JSONArray responseArray = response.getJSONArray("data");

        for (int i = 0; i < responseArray.length(); i++) {
            JSONObject data = responseArray.getJSONObject(i);
            notifications.add(
                    new Notification(
                            data.getString("noti_header"),
                            data.getString("noti_body")

                    )
            );

        }
        setViewNotification();
    }
    private void gameInfo(){
        progressDialog.setTitle("Requesting");
        progressDialog.setMessage("Loading Matches___");
        progressDialog.show();
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,GAME_URL,null,
                    response -> {
                        progressDialog.dismiss();
                        try {
                            if (response.getBoolean("success")){
//                                    Log.d("TAGa", "onResponse: "+response);
                                String img_url = response.getString("img_url");
                                JSONArray data = response.getJSONArray("data");
                                for (int i=0;i<data.length();i++) {
                                    JSONObject object = data.getJSONObject(i);
                                    games.add(new GameModal(
                                            object.getString("game_id"),
                                            object.getString("game_title"),
                                            BASE_URL1+img_url+object.getString("game_pic")
                                            ));
                                }
                                sendToAdapter(games);
                            }
                            else {
                               Utils.alert("Error",response.getString("error"),requireActivity(),false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                        progressDialog.dismiss();
                     if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                            Toast.makeText(requireActivity(), "error occurred: try after sometime", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(requireActivity(), ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    });
            queue.add(stringRequest);
        }
    private void sendToAdapter(List<GameModal> games){
            r.setAdapter(new GameViewAdapter(requireActivity(),games));
            r.setLayoutManager(new LinearLayoutManager(getContext()));
        }

}