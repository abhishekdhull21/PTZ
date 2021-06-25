package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Modals.GameModal;
import com.example.myapplication.R;
import com.example.myapplication.MatchActivity;

import java.util.List;

import static com.example.myapplication.fragments.RegisterFragment.TAG;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewAdapter.ViewHolder>  {
    Context context;
    List<GameModal> games;

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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,MatchActivity.class);
                i.putExtra("game", game);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView game_img;
        public TextView game_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.game_card_layout);
            game_img = itemView.findViewById(R.id.game_img);
            game_title = itemView.findViewById(R.id.game_title);
        }
    }
}
