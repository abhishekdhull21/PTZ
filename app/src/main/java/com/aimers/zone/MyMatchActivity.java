package com.aimers.zone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimers.zone.Adapters.MyMatchAdapter;

import java.util.Objects;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MyMatchActivity extends AppCompatActivity implements View.OnClickListener {
private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_match);
        customActionbar("Match List");
        initViews();
        setAdapter();

    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyMatchAdapter());
    }

    private void customActionbar(String title){
        ActionBar actionBar = getSupportActionBar();
        Log.d(TAG, "customActionbar: "+actionBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_backbutton);
        View view =getSupportActionBar().getCustomView();
        ImageView backBtn = view.findViewById(R.id.back_btn_actionbar);
        TextView txt_title = view.findViewById(R.id.txt_action_bar_title);
        txt_title.setText(title);
        backBtn.setOnClickListener(v -> MyMatchActivity.super.onBackPressed());
    }

    @Override
    public void onClick(View v) {

    }
}