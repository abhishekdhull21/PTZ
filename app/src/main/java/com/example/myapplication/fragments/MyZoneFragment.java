package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.NotificationActivity;
import com.example.myapplication.R;
import com.example.myapplication.WalletActivity;

public class MyZoneFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_zone, container, false);
        CardView walletView = v.findViewById(R.id.my_wallet);
        CardView notification = v.findViewById(R.id.myzone_notification);

        notification.setOnClickListener(this);
        walletView.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i =null;
        switch (v.getId()){
            case R.id.my_wallet:
               i = new Intent(getActivity(),WalletActivity.class);
                break;
            case R.id.myzone_notification:
                i = new Intent(getActivity(), NotificationActivity.class);
                break;

        }
        if (i!= null)
            startActivity(i);
    }
}