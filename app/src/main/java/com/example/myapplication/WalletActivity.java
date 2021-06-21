package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.myapplication.fragments.AddMoneyWalletFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.RedeemMoneyWalletFragment;
import com.example.myapplication.fragments.RegisterFragment;
import com.example.myapplication.fragments.TransactionWalletFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WalletActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    private static final int NUM_PAGES = 3;
    private String[] titles = new String[]{"ADD MONEY", "REDEEM MONEY","TRANSACTION"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(this);
//        TextView textView = findViewById(R.id.textView49);
        viewPager = findViewById(R.id.wallet_view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout = null;
        tabLayout = findViewById(R.id.wallet_tabs);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

    }

    private static class MyPagerAdapter extends FragmentStateAdapter {

        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }


        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return AddMoneyWalletFragment.newInstance("fragment 1");
                }
                case 1: {

                    return RedeemMoneyWalletFragment.newInstance("fragment 2");
                }
                default:
                    return TransactionWalletFragment.newInstance("fragment 1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}