package com.aimers.zone.wallet;

import android.app.Activity;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.Interface.WalletFetchResponse;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.aimers.zone.Utils.Constant.WALLET_URL;

public class Wallet {
    public static void fetch(Activity activity, WalletFetchResponse walletFetchResponse){
        Map<String, String> params = new HashMap<>();
        params.put("token", User.userToken(activity));
        NetworkRequest request = new NetworkRequest(activity);
        request.sendRequest(params, WALLET_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                if (response.getBoolean("success")) {
                    JSONObject object = response.getJSONObject("data");
                    com.aimers.zone.Modals.Wallet.wallet.setCoins(object.getString("coins"));
                    com.aimers.zone.Modals.Wallet.wallet.setUser_id(object.getString("user_id"));
                    com.aimers.zone.Modals.Wallet.wallet.setId(object.getString("id"));
                    walletFetchResponse.onResponse(true);
                }
            }

            @Override
            public void onErrorResponse(JSONObject response) {
                walletFetchResponse.onResponse(false);
            }
        });
    }

}
