package com.aimers.zone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aimers.zone.Modals.Notification;
import com.aimers.zone.NotificationShowActivity;
import com.aimers.zone.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    final Context context;
    private ArrayList<Notification> notifications;
    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_msg_node,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.txt_header.setText(notification.getHeader());
        holder.notification_cardview.setOnClickListener(v -> context.startActivity(new Intent(context, NotificationShowActivity.class)));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView notification_cardview;
        public TextView txt_header;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_cardview = itemView.findViewById(R.id.notification_msg_node_cardview);
            txt_header= itemView.findViewById(R.id.txt_notification_child_node);
        }
    }
}
