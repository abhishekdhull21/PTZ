package com.aimers.zone.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.MainActivity;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.aimers.zone.Utils.Constant.PAYMENT_INIT;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class AddMoneyWalletFragment extends Fragment implements View.OnClickListener {


    private final String orderId = "order_from_app";
    private RequestQueue queue;
    private Map<String, String> trans;
    private String orderIdString;
    private final String midString="OgdBig44888892307561";
    private String txnAmountString;
    private final int ActivityRequestCode=2;
    private ProgressDialog progressDialog;

    public AddMoneyWalletFragment() {
        // Required empty public constructor
    }

    public static AddMoneyWalletFragment newInstance(String param1) {

        return new AddMoneyWalletFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_money_wallet, container, false);
        trans = new HashMap<String,String>();
        queue = Volley.newRequestQueue(requireActivity());
        progressDialog = new ProgressDialog(requireActivity());
        Button btn10 = v.findViewById(R.id.btnPay10);
        Button btn50 = v.findViewById(R.id.btnPay50);
        Button btn100 = v.findViewById(R.id.btnPay100);
        btn10.setOnClickListener(this);
        btn50.setOnClickListener(this);
        btn100.setOnClickListener(this);

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPay10:
                trans("10");
                break;
            case R.id.btnPay50:
                trans("50");
                break;
            case R.id.btnPay100:
                trans("100");
                break;
        }
    }
    public void trans(String amt)  {
        progressDialog.setTitle("Initializing Payment");
        progressDialog.setMessage("Wait for gateway response");
        progressDialog.setCancelable(false);
        progressDialog.show();
        txnAmountString = amt;
        orderIdString = generateOrderId();
        trans.put("user_token", User.userToken(requireActivity()));
        trans.put("order_id",orderIdString);
        trans.put("amount",txnAmountString);
        trans.put("MID",midString);
        NetworkRequest request = new NetworkRequest(requireActivity());
        request.sendRequest(trans, PAYMENT_INIT, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                if (response.getBoolean("success")){
                                JSONObject data = response.getJSONObject("data");
                                startPaytmPayment(data.getString("txnToken"),data.getString("callback"));
                }
                Log.d("TAG", "onResponse: "+response);
//                Toast.makeText(requireActivity(), ":"+response, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
            @Override
            public void onErrorResponse(JSONObject response) {
                Log.d("TAG", "onResponse: "+response);
//                Toast.makeText(requireActivity(), ":"+response, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

    }

    public String generateOrderId() {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HMSddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return  date+ randomNum;
    }
    public void startPaytmPayment (String token,String callBackUrl){

        // for test mode use it
//        String host = "https://securegw-stage.paytm.in/";
        // for production mode use it
        String host = "https://securegw.paytm.in/";
//        String orderDetails = "MID: " + trans.get("MID") + ", OrderId: " + trans.get("order_id") + ", TxnToken: " + token+ ", Amount: " + trans.get("amount");
//        Log.e(TAG, "order details "+ orderDetails);

//        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;
//        Log.e(TAG, " callback URL "+callBack);
        PaytmOrder paytmOrder = new PaytmOrder(orderIdString, midString, token, txnAmountString, callBackUrl);
        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){
            @Override
            public void onTransactionResponse(Bundle bundle) {
                alert(bundle.toString(),true);
                Intent i = new Intent(requireActivity(),MainActivity.class);
                requireActivity().startActivityFromFragment(AddMoneyWalletFragment.this,i, -1);

                Log.e(TAG, "Response (onTransactionResponse) : "+bundle.toString());
//                Log.e(TAG, "Response (onTransactionResponse) : "+ Objects.requireNonNull(bundle.getStringArray("response"))[0]);
            }

            @Override
            public void networkNotAvailable() {
                alert("network not available ",false);
                Log.e(TAG, "network not available ");
            }

            @Override
            public void onErrorProceed(String s) {
                alert(s,false);
                Log.e(TAG, " onErrorProcess "+ s);
            }

            @Override
            public void clientAuthenticationFailed(String s) {
                alert(s,false);
                Log.e(TAG, "Clientauth "+s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
                alert(s,false);
                Log.e(TAG, " UI error "+s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
                alert(s+"--"+s1,false);
                Log.e(TAG, " error loading web "+s+"--"+s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
                alert("Payment Cancelled by User",false);
                Log.e(TAG, "backPress ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                alert(s,false);
                Log.e(TAG, " transaction cancel "+s);
            }
        });

        transactionManager.setShowPaymentUrl(host+"theia/api/v1/showPaymentPage" );//+ "theia/api/v1/showPaymentPage"
        transactionManager.startTransaction(requireActivity(), ActivityRequestCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG ," result code "+resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "   NULL"));
                }
            }
            String response = data.getStringExtra("response");
            Log.e(TAG, " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e(TAG, " data response - "+response);
            /*
             data response - {"BANKNAME":"WALLET","BANKTXNID":"1394221115",
             "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmM+VHP5pH0ekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
             "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcP3138556","ORDERID":"100620202152",
             "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
             "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"2020061011121280011018328631290118"}
              */
            Alerter alerter = Alerter.create(requireActivity())
                    .enableVibration(true)
                    .setText(Objects.requireNonNull(data.getStringExtra("nativeSdkForMerchantMessage")));
            alerter.show();
            Toast.makeText(requireActivity(), data.getStringExtra("nativeSdkForMerchantMessage")
                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }else{
            Log.e(TAG, " payment failed");
        }
    }

    public void alert(String msg, boolean success){

        Alerter alerter = Alerter.create(requireActivity())
                .setText(msg)
                .enableVibration(true);
        if (success) {
            alerter.setBackgroundColorRes(R.color.colorBackgroundSuccess)
                    .show();
        } else {
            alerter.setBackgroundColorRes(R.color.alert_default_error_background).show();
        }


    }
}