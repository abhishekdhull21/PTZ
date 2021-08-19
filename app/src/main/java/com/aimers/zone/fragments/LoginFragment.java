package com.aimers.zone.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aimers.zone.Interface.RedeemRequestResponse;
import com.aimers.zone.MainActivity;
import com.aimers.zone.R;
import com.aimers.zone.Utils.NetworkRequest;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.UserInfo;
import com.aimers.zone.Utils.Utils;
import com.aimers.zone.databinding.FragmentLoginBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.aimers.zone.Utils.Constant.LOGIN_URL;
import static com.aimers.zone.fragments.RegisterFragment.TAG;


public class LoginFragment extends Fragment implements View.OnClickListener {
    private FragmentLoginBinding binding;
    NetworkRequest request;

    private ProgressDialog dialog;
    private User user;
    public LoginFragment() {
        // Required empty public constructor
    }
    public static LoginFragment newInstance(String text) {
        LoginFragment f = new LoginFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentLoginBinding.inflate(getLayoutInflater());
       View v = binding.getRoot();
       request = new NetworkRequest(getActivity());
       dialog = new ProgressDialog(requireActivity());
       dialog.setCancelable(false);
        binding.btnLogin.setOnClickListener(this);
        user = new User(requireActivity());

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            login(loginInfo());
        }
    }

    private void login(Map<String, String> params) {
        if(params == null) return;
        Context context =requireActivity();
        request.sendRequest(params, LOGIN_URL, new RedeemRequestResponse() {
            @Override
            public void onSuccessResponse(JSONObject response) throws JSONException {
                try {
                    if (response.getBoolean("success")){
                        Log.d("TAG", "onResponse: "+response);
                        Intent i = new Intent(context, MainActivity.class);
                        UserInfo userInfo = new UserInfo(response.getString("token"));
                        i.putExtra("user", userInfo);
                        context.startActivity(i);
                        requireActivity().finishActivity(1);
                    }
                    else {

                        Toast.makeText(context, response.getString("error"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();

            }

            @Override
            public void onErrorResponse(JSONObject response) throws JSONException {
                dialog.dismiss();
                Log.d(TAG, "onErrorResponse: "+response);

                Toast.makeText(requireActivity(), ""+response.getString("error"), Toast.LENGTH_LONG).show();

            }
        });
    }

    private Map<String,String> loginInfo(){
        Map<String,String> map = new HashMap<String, String>();
        boolean err = false;
        String mobile =  Objects.requireNonNull(binding.LoginMobile.getText()).toString();
        String password =  Objects.requireNonNull(binding.LoginPassword.getText()).toString();
        if (password.equals("")){
            Toast.makeText(getActivity(), "name required", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (mobile.equals("")){
            Toast.makeText(getActivity(),  "mobile required", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (err) return null;
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("device_id",  Utils.androidId(requireActivity()));
        return map;
    }
}