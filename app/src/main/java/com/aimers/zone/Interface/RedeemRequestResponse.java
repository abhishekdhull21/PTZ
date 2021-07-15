package com.aimers.zone.Interface;

import org.json.JSONException;
import org.json.JSONObject;

public interface RedeemRequestResponse {
   void onSuccessResponse(JSONObject response) throws JSONException;
   void onErrorResponse(JSONObject response);
}
