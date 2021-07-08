package com.aimers.zone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.aimers.zone.Adapters.MyMatchAdapter;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MyMatchActivity extends AppCompatActivity implements View.OnClickListener {
private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_match);
        customActionbar();
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

    private void customActionbar(){
        ActionBar actionBar = getSupportActionBar();
        Log.d(TAG, "customActionbar: "+actionBar);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//        View view =getSupportActionBar().getCustomView();
//        assert actionBar != null;
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
////        view.setBackgroundColor(getColor(R.color.colorPrimary));
////        view.setPadding(0,0,0,0);
//        ImageView notification = view.findViewById(R.id.notification_custom_navbar);
//        notification.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}