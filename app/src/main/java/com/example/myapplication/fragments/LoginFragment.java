package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;


public class LoginFragment extends Fragment {

    private static final String ARG_COUNT = "param1";
    private Integer counter;
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
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_login, container, false);

        return v;
    }

}