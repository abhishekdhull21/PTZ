package com.aimers.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.fragments.MatchViewFragment;
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
    private static final int NUM_PAGES = 3;
    private final String[] titles = new String[]{"ONGOING", "UPCOMING","RESULT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        GameModal game = (GameModal) getIntent().getSerializableExtra("game");

        FragmentStateAdapter pagerAdapter = new MyPagerAdapter(this, game);
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
        final GameModal game;
        public MyPagerAdapter(FragmentActivity fa,GameModal game) {
            super(fa);
            this.game = game;
        }


        @NotNull
        @Override
        public MatchViewFragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return MatchViewFragment.newInstance(game,0);
                }
                case 1: {
                   return MatchViewFragment.newInstance(game, 1);
                }
                case 2:
                   return MatchViewFragment.newInstance(game,2);

            }
            return null;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}