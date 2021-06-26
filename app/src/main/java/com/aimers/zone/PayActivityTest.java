package com.aimers.zone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aimers.zone.Utils.User;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.aimers.zone.Utils.Constant.PAYMENT_INIT;
import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class PayActivityTest extends AppCompatActivity {
    private String orderId = "order_from_app";
    private final String MID ="VCWQWH26061118544596";
    private RequestQueue queue;
    private  Map<String, String> trans;
    private String txnTokenString;
    private String orderIdString;
    private String midString="OgdBig44888892307561";
    private String txnAmountString;
    private int ActivityRequestCode=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_test);
        Button btn = findViewById(R.id.btn10);
        trans = new HashMap<>();
        queue = Volley.newRequestQueue(this);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PayActivityTest.this,"PaymentInitiate",Toast.LENGTH_LONG).show();
                trans();
            }
        });

    }
    public void trans()  {
        txnAmountString = "10";
        orderIdString = generateOrderId();
        trans.put("user_token",User.userToken(this));
        trans.put("order_id",orderIdString);
        trans.put("amount",txnAmountString);
        trans.put("MID",midString);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST  ,PAYMENT_INIT,new JSONObject(trans),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")){

                                JSONObject data = response.getJSONObject("data");
                                startPaytmPayment(data.getString("txnToken"),data.getString("callback"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("TAG", "onResponse: "+response);
                        Toast.makeText(PayActivityTest.this, ":"+response, Toast.LENGTH_LONG).show();
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error );
                if (error.getLocalizedMessage() == null || error.getLocalizedMessage().isEmpty() )
                    Toast.makeText(PayActivityTest.this, ""+error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PayActivityTest.this, "error occurred: try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public String generateOrderId() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HMSddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return  date+String.valueOf(randomNum);
    }
    public void startPaytmPayment (String token,String callBackUrl){

        txnTokenString = token;
        // for test mode use it
         String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
//        String host = "https://securegw.paytm.in/";
        String orderDetails = "MID: " + trans.get("MID") + ", OrderId: " + trans.get("order_id") + ", TxnToken: " + txnTokenString
                + ", Amount: " + trans.get("amount");
        //Log.e(TAG, "order details "+ orderDetails);

//        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
//        Log.e(TAG, " callback URL "+callBack);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, txnTokenString, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
            }

            @Override
            public void networkNotAvailable() {
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                Log.e(TAG, " onErrorProcess "+s.toString());
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host+"theia/api/v1/showPaymentPage" );//+ "theia/api/v1/showPaymentPage"
        transactionManager.startTransaction(this, ActivityRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG ," result code "+resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
            Log.e(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e(TAG, " data response - "+data.getStringExtra("response"));
/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1394221115",
 "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmM+VHP5pH0ekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcP3138556","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"2020061011121280011018328631290118"}
  */
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }else{
            Log.e(TAG, " payment failed");
        }
    }
}