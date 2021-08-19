package com.aimers.zone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimers.zone.Adapters.NotificationAdapter;

import static com.aimers.zone.MainActivity.notifications;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        RecyclerView recyclerView = findViewById(R.id.notification_recycler);
        TextView textView = findViewById(R.id.textView43);
        ImageView imageView = findViewById(R.id.imageView17);
        if (!notifications.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
            recyclerView.setAdapter(new NotificationAdapter(this, notifications));
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
//        ShimmerRecyclerView shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);
//        shimmerRecycler.showShimmerAdapter();

    }
}