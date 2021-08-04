package com.aimers.zone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.aimers.zone.Utils.Constant.TEST_URL;
import static com.aimers.zone.Utils.Utils.*;

public class SupportFragment extends Fragment {
    private Button btnSubmit;
    private EditText txtQuery;
    private View v;
    private NetworkRequest request;
    public SupportFragment() {
        // Required empty public constructor
    }


    public static SupportFragment newInstance() {
        return new SupportFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_supportk, container, false);
        btnSubmit = v.findViewById(R.id.btnQuerySubmit);
        txtQuery = v.findViewById(R.id.txtQuery);
        setRules();
        request = new NetworkRequest(getActivity());
        btnSubmit.setOnClickListener(v -> {
            sendRequest();
        });
        return v;
    }
    private void setRules(){
        TextView txt = v.findViewById(R.id.txtview_rules);
        txt.setText(Html.fromHtml("\"<p style='margin:0cm;margin-bottom:.0001pt;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;\">READ EACH RULE VERY CAREFULLY DON'T BLAME ROOM ORGANISERS OR ADMIN FOR ANY UNFOLLOWED RULE FROM YOUR SIDE  </span></strong></span></p>\n" +
                "<p style='margin:0cm;margin-bottom:.0001pt;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;\"></span></strong></span></p>\n" +
                "<h3 style='margin-top:2.0pt;margin-right:0cm;margin-bottom:.0001pt;margin-left:0cm;line-height:107%;font-size:16px;font-family:\"Calibri Light\",\"sans-serif\";color:#1F3763;font-weight:normal;'><span style=\"color:#404040;font-style:italic;\"><strong><span style=\"font-size:27px;line-height:107%;\">RuLes And Regulations ---&gt;</span></strong></span></h3>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'></p>\n" +
                "<ol style=\"list-style-type: undefined;margin-left:8px;\">\n" +
                "    <li>For <strong>BGMI   &amp; PUBG Lite</strong> Minimum 45 level id are allowed to participate in tournaments. If you still join the room <strong>No Refund</strong> will Be given.</li>\n" +
                "    <li>No <strong>Emulators</strong> are allowed.</li>\n" +
                "    <li>If we found Hacks or teaming up with other players permanent ban will be given.</li>\n" +
                "    <li>If we found sharing id and password with an unregistered player you will be lost your rewards and withdrawals also.</li>\n" +
                "    <li>Id &amp; password will upload <strong>15min</strong> <strong>Before</strong>.</li>\n" +
                "    <li>Once you join any match you will be allotted with your slot number make sure you join and hold your place before start time</li>\n" +
                "</ol>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'> </p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;\">NOTE -&gt;</span></strong></p>\n" +
                "<ol style=\"list-style-type: undefined;\">\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\"> If anyone is already sitting on your slot relax you can change it in squad matches please try to be as soon as possible to join the room with your team I can&apos;t help if any random player is popping and interrupting your team at the last minutes.</span></strong></li>\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\"> If there are fewer than 25 registration before 15 minutes or the meaning Faisal be reduced</span></strong></li>\n" +
                "    <li><strong><span style=\"line-height:107%;font-size:27px;\">Fixed winning price will be given if registration is above 60%</span></strong></li>\n" +
                "</ol>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\"> </span></strong></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:0cm;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><strong><span style=\"font-size:27px;line-height:107%;color:red;\">Some important points </span></strong><strong><span style=\"font-size:27px;line-height:107%;font-family:Wingdings;color:red;\">-></span></strong></p>\n" +
                "<ul style=\"list-style-type: disc;\">\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If you are found suspicious by the admin then you have to record every game you play on the app if any player refuses to do it so so and argues with the admin action will be taken.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If you are playing with the suspicious player or confirm the hacker winning be on hold it doesn&apos;t matter he is random or your friend.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">If anyone of you fails to join the room by the match start time then we are not responsible for IT refund in such cases was not we proceed so make sure to join before time.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Do not share the ID and password with anyone who has not to join the match if you are founded doing so your account gets terminated and all the winning will be lost.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Please note that the list of today&apos;s entry fees is per individual player not for squad or Duo team.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Use only a mobile device to join match no emulator players are allowed.</span></li>\n" +
                "    <li><span style=\"line-height:107%;font-size:20.0pt;color:black;\">Creating multi-fake accounts or adding external fake and trees male lead for a permanent ban from app.</span></li>\n" +
                "</ul>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:.0001pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\n" +
                "<p style='margin-top:0cm;margin-right:0cm;margin-bottom:8.0pt;margin-left:36.0pt;line-height:107%;font-size:15px;font-family:\"Calibri\",\"sans-serif\";'><span style=\"font-size:27px;line-height:107%;color:black;\"> </span></p>\"\n" +
                "\n"));
    }
    public void sendRequest(){
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