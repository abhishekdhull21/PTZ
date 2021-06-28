package com.aimers.zone.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aimers.zone.Modals.GameModal;
import com.aimers.zone.Modals.MatchModal;
import com.aimers.zone.R;

import java.util.ArrayList;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

public class MatchViewAdapter extends RecyclerView.Adapter<MatchViewAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<MatchModal> matches ;
    GameModal game;
    private Context context;

    public MatchViewAdapter(Context context,ArrayList<MatchModal> match, GameModal game) {
        this.matches = match;
        this.game = game;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_card_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchModal match = matches.get(position);
        if(match.getPos() == 1){
            holder.tableRow.setVisibility(View.VISIBLE);
            holder.btnJoin.setVisibility(View.VISIBLE);
            holder.btnYT.setVisibility(View.GONE);
            holder.txtCompleted.setVisibility(View.GONE);
            holder.btnJoin.setOnClickListener(this);
        }
        else if (match.getPos() == 0)        {
            holder.tableRow.setVisibility(View.VISIBLE);
            holder.btnJoin.setVisibility(View.GONE);
            holder.btnYT.setVisibility(View.VISIBLE);
            holder.txtCompleted.setVisibility(View.GONE);
            holder.btnYT.setOnClickListener(this);
        }else if(match.getPos() ==2){
            holder.tableRow.setVisibility(View.GONE);
            holder.btnJoin.setVisibility(View.GONE);
            holder.btnYT.setVisibility(View.GONE);
            holder.txtCompleted.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "onBindViewHolder: "+matches.size());
        holder.txtTime.setText(String.format("Time :%s at %s", match.getMatch_date(), match.getMatch_time()));
        holder.progressBar.setMax(Integer.parseInt(match.getTotal_slot()));
        holder.progressBar.setProgress(Integer.parseInt(match.getAlloted_slot()));
        holder.txtRemaining.setText(String.format("Only %s spots left", match.getRemaining_slot()));
        holder.txtVersion.setText(match.getVersion());
        holder.txtType.setText(match.getType());
        holder.txtSlot.setText(String.format("%s/%s", match.getAlloted_slot(), match.getTotal_slot()));
        holder.txtMap.setText(match.getMap());
        holder.txtFee.setText(match.getEntry_fee());
        holder.txtPerKill.setText(match.getPer_kill());
        holder.txtPrize.setText(match.getPrize_pool());
        //game title
        holder.txtGameName.setText(game.getTitle());

        //load image
        Glide.with(context).load(game.getPic()).into(holder.gameImg);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yt:
                Log.d(TAG, "onClick: btnyt");
                break;
            case R.id.btn_join:
                Log.d(TAG, "onClick: btn join");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTime,txtPrize,txtPerKill,txtFee,txtType,txtVersion,txtMap,
        txtSlot,txtRemaining,txtGameName,txtCompleted;
        public ProgressBar progressBar;
        public ImageView gameImg;
        public Button btnJoin,btnYT;
        public TableRow tableRow;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPrize = itemView.findViewById(R.id.txt_pool);
            txtPerKill = itemView.findViewById(R.id.txt_kill);
            txtFee = itemView.findViewById(R.id.txt_fee);
            txtVersion = itemView.findViewById(R.id.txt_version);
            txtType = itemView.findViewById(R.id.txt_type);
            txtMap = itemView.findViewById(R.id.txt_map);
            txtSlot = itemView.findViewById(R.id.txt_slot);
            txtRemaining = itemView.findViewById(R.id.txt_remaining_slot);
            txtTime = itemView.findViewById(R.id.txt_time);
            txtGameName = itemView.findViewById(R.id.txt_game_name);
            txtCompleted = itemView.findViewById(R.id.txtCompleted);
            progressBar = itemView.findViewById(R.id.progressBar_slot);
            btnJoin = itemView.findViewById(R.id.btn_join);
            btnYT = itemView.findViewById(R.id.btn_yt);
            gameImg = itemView.findViewById(R.id.match_game_img);
            tableRow = itemView.findViewById(R.id.tblRow);

        }
    }
}
