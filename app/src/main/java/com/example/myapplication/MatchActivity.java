package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.Modals.GameModal;
import com.example.myapplication.R;
import com.example.myapplication.fragments.GameFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.MatchViewFragment;
import com.example.myapplication.fragments.MyZoneFragment;
import com.example.myapplication.fragments.RegisterFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MatchActivity extends AppCompatActivity {
//    final Fragment fragment1 = new GameFragment();
//    final Fragment fragment2 = new MatchViewFragment();
//    final Fragment fragment3 = new MyZoneFragment();
//    final FragmentManager fm = getSupportFragmentManager();
//    Fragment active = fragment1;
    ViewPager2 viewPager;
    private GameModal game;
    private static final int NUM_PAGES = 3;
    private String[] titles = new String[]{"ONGOING", "UPCOMING","RESULT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
         game = (GameModal) getIntent().getSerializableExtra("game");

        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(this,game);
        viewPager =  findViewById(R.id.match_view_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);
        TabLayout tabLayout  = null;
        viewPager.setOffscreenPageLimit(3);
        tabLayout = findViewById(R.id.match_tabs);
//        loadFragment();
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();


    }
    private static class MyPagerAdapter extends FragmentStateAdapter {
        GameModal game;
        public MyPagerAdapter(FragmentActivity fa,GameModal game) {
            super(fa);
            this.game = game;
        }


        @NotNull
        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return RegisterFragment.newInstance("fragment 1");
                }
                case 1: {
                   return MatchViewFragment.newInstance(game);
                }
                case 2:
                   return LoginFragment.newInstance("gfh");

            }
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
//    public void loadFragment(){
//        fm.beginTransaction().add(R.id.fragment_container, fragment3, "3").hide(fragment3).commit();
//        fm.beginTransaction().add(R.id.fragment_container, fragment2, "2").hide(fragment2).commit();
//        fm.beginTransaction().add(R.id.fragment_container,fragment1, "1").commit();
//    }
}