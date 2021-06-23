package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Utils.User;
import com.example.myapplication.Utils.Utils;
import com.example.myapplication.databinding.FragmentRegisterBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private FragmentRegisterBinding binding;
    public static String TAG= "mtag";
    private ProgressDialog dialog;
    private User user;
    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance(String text) {

        RegisterFragment f = new RegisterFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        user = new User(requireActivity());
        dialog = new ProgressDialog(requireActivity());
        dialog.setCancelable(false);
        binding.btnRegister.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        dialog.setMessage("wait");

        switch (v.getId()){
            case R.id.btn_register:
                    dialog.show();
                    user.register(userInfo(),dialog);

                break;
        }
    }

    private Map<String,String> userInfo(){
        boolean err = false;
        String userName =  Objects.requireNonNull(binding.firstName.getText()).toString();
        String userLName =  Objects.requireNonNull(binding.lastName.getText()).toString();
        String mobile =  Objects.requireNonNull(binding.txtMobile.getText()).toString();
        String password =  Objects.requireNonNull(binding.txtPassword.getText()).toString();
        if (userName.equals("")){
            Toast.makeText(getActivity(), "name required", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (mobile.equals("")){
            Toast.makeText(getActivity(), "mobile required", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (password.equals("")){
            Toast.makeText(getActivity(), "password required", Toast.LENGTH_SHORT).show();
            err = true;
        }
        if (err) return null;
        Map<String,String> param = new HashMap<String, String>();
        param.put("username",userName + " "+userLName);
        param.put("mobile",mobile);
        param.put("password",password);
        param.put("device_id",Utils.androidId(requireActivity()));
        return  param;
    }


}