package com.aimers.zone.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aimers.zone.R;


public class ShareAppFragment extends Fragment {
    private View v;

    public ShareAppFragment() {
    }
    public static ShareAppFragment newInstance() {
        return new ShareAppFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            v  =        inflater.inflate(R.layout.fragment_share_app, container, false);

        return v;
    }
}