package com.aimers.zone;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.Objects;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Context context;
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.custom_menu_backbtn,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void customActionbar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null)return;
        Log.d(TAG, "customActionbar: "+actionBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_backbutton);
        View view =getSupportActionBar().getCustomView();
        ImageView backBtn = view.findViewById(R.id.back_btn_actionbar);
        TextView txt_title = view.findViewById(R.id.txt_action_bar_title);
        txt_title.setText("Matches");
        backBtn.setOnClickListener(v -> MatchActivity.super.onBackPressed());
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