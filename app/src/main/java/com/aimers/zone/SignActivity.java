package com.aimers.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.aimers.zone.fragments.LoginFragment;
import com.aimers.zone.fragments.RegisterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class SignActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    private static final int NUM_PAGES = 2;
    private final String[] titles = new String[]{"Login", "Register"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(this);
//        TextView textView = findViewById(R.id.textView49);
        viewPager =  findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabLayout  = null;
        tabLayout = findViewById(R.id.tabs);

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


        @NotNull
        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return LoginFragment.newInstance("fragment 1");
                }
                case 1: {

                    return RegisterFragment.newInstance("fragment 2");
                }
                default:
                    return LoginFragment.newInstance("fragment 1, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}