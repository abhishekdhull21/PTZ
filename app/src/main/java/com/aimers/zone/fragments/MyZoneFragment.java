package com.aimers.zone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aimers.zone.Interface.UsersFromServer;
import com.aimers.zone.NotificationActivity;
import com.aimers.zone.R;
import com.aimers.zone.Utils.User;
import com.aimers.zone.WalletActivity;
import com.aimers.zone.databinding.FragmentMyZoneBinding;

import static com.aimers.zone.Modals.UserBio.*;


public class MyZoneFragment extends Fragment implements View.OnClickListener, UsersFromServer {
    private FragmentMyZoneBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyZoneBinding.inflate(inflater);
        View v  = binding.getRoot();
        CardView walletView = v.findViewById(R.id.my_wallet);
        CardView notification = v.findViewById(R.id.myzone_notification);
        CardView logout = v.findViewById(R.id.myzone_logout);
        setProfileName();
        notification.setOnClickListener(this);
        walletView.setOnClickListener(this);
        logout.setOnClickListener(this);
        return v;
    }

    public void setProfileName(){
        UserFromServer(requireActivity(),this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            setProfileName();
        }
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
            case R.id.myzone_logout:
                    User.logout(requireActivity());
                break;
        }
        if (i!= null)
            startActivity(i);
    }

    @Override
    public void SetProfileUser() {
        binding.myzoneName.setText(user.getName());
        binding.txtWinCoin.setText(user.getCoins());
        binding.txtTotalKillCount.setText(user.getKills());
        binding.txtMatchPlayedCount.setText(user.getMatch_played());
    }
}