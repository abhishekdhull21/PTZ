package com.aimers.zone.Interface;

import org.json.JSONObject;

public interface RedeemRequestResponse {
   void onSuccessResponse(JSONObject response);
   void onErrorResponse(JSONObject response);
}
