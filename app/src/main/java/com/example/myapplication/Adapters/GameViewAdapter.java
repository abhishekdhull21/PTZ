package com.example.myapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.fragments.GameFragment;
import com.example.myapplication.fragments.LoginFragment;
import com.example.myapplication.fragments.MatchActivity;

import java.util.zip.Inflater;

public class GameViewAdapter extends RecyclerView.Adapter<GameViewAdapter.ViewHolder>  {
    Context context;
    MainActivity main;

    public GameViewAdapter(MainActivity main) {
        this.main = main;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new MatchActivity()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.game_card_layout);
        }
    }
}
