package com.aimers.zone.Adapters;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.R;
import com.aimers.zone.MatchActivity;

import java.util.List;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewAdapter.ViewHolder>  {
    Context context;
    final List<GameModal> games;

    public GameViewAdapter(Context context, List<GameModal> games) {
        this.games =games;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card_layout,parent,false);
      this.context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameModal game = games.get(position);
        holder.game_title.setText(game.getTitle());
        Log.d(TAG, "onBindViewHolder: "+game.getPic());
        Glide.with(context).load(game.getPic()).into(holder.game_img);
        holder.game_title.setOnClickListener(v -> {
            Intent i = new Intent(context,MatchActivity.class);
            i.putExtra("game", game);
            context.startActivity(i);
        });
        holder.cardView.setOnClickListener(v -> {
            Intent i = new Intent(context,MatchActivity.class);
            i.putExtra("game", game);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;
        public final ImageView game_img;
        public final TextView game_title;
        public final LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.game_card_layout);
            game_img = itemView.findViewById(R.id.game_img);
            game_title = itemView.findViewById(R.id.game_title);
            layout = itemView.findViewById(R.id.linear_layout_game);
        }
    }
}
