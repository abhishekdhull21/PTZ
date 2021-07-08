package com.aimers.zone.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aimers.zone.R;
import com.aimers.zone.Utils.User;
import com.aimers.zone.Utils.Utils;
import com.aimers.zone.databinding.FragmentLoginBinding;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class LoginFragment extends Fragment implements View.OnClickListener {
    private FragmentLoginBinding binding;
    private static final String ARG_COUNT = "param1";
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentLoginBinding.inflate(getLayoutInflater());
       View v = binding.getRoot();
       dialog = new ProgressDialog(requireActivity());
       dialog.setCancelable(false);
        binding.btnLogin.setOnClickListener(this);
        user = new User(requireActivity());

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            user.login(loginInfo(), dialog);
        }
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