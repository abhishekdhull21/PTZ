package com.example.myapplication.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.RegisterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MatchActivity extends Fragment {
    ViewPager2 viewPager;
    private static final int NUM_PAGES = 3;
    private String[] titles = new String[]{"ONGOING", "UPCOMING","RESULT"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=  inflater.inflate(R.layout.activity_match, container, false);


        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(getActivity());
        viewPager =  v.findViewById(R.id.match_view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        TabLayout tabLayout  = null;
        tabLayout = v.findViewById(R.id.match_tabs);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

        return v;
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
                    return RegisterFragment.newInstance("fragment 1");
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