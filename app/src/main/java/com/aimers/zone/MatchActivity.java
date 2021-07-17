package com.aimers.zone;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.fragments.MatchViewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

public class MatchActivity extends AppCompatActivity {

    ViewPager2 viewPager;
    private static final int NUM_PAGES = 3;
    private final String[] titles = new String[]{"ONGOING", "UPCOMING","RESULT"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        GameModal game = (GameModal) getIntent().getSerializableExtra("game");
        customActionbar();
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
    private void customActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);


        actionBar.setCustomView(R.layout.custom_actionbar_backbutton);

        View view = actionBar.getCustomView();
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//        view.setBackgroundColor(getColor(R.color.colorPrimary));
//        view.setPadding(0,0,0,0);
//        ImageView backBtn = view.findViewById(R.id.back_btn_actionbar );
//        Log.d(TAG, "customActionbar: "+backBtn);
/*
        backBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MatchActivity.super.onBackPressed();
            }
        });
*/
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