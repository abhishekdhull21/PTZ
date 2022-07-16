package com.aimers.zone.Adapters;

import static com.aimers.zone.fragments.RegisterFragment.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aimers.zone.Modals.MatchResultModal;
import com.aimers.zone.R;

import java.util.ArrayList;

public class MatchResultAdapter extends RecyclerView.Adapter<MatchResultAdapter.ViewHolder> {
    ArrayList<MatchResultModal> resultModals;
    Activity context;
    public MatchResultAdapter(ArrayList<MatchResultModal> resultModals,Activity context) {
        this.resultModals = resultModals;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_result,parent,false);
        return new MatchResultAdapter.ViewHolder(v);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchResultModal matchResult = resultModals.get(position);
        Log.e(TAG, "onBindViewHolder: "+matchResult.getKill() );
//        holder.txtRank.setText((matchResult.getPosition() != null)?matchResult.getPosition(): "0");
        holder.txtRemarks.setText(matchResult.getRemarks()!= null?matchResult.getRemarks():"");
        holder.txtKills.setText(matchResult.getKill() != null ? matchResult.getKill():"0");
        holder.txtNo.setText(String.format("%d", position + 1));
        holder.txtName.setText(matchResult.getUsername()!=null?matchResult.getUsername():"no name");

        holder.txtWin.setText(matchResult.getWin()!=null ? matchResult.getWin() : "0");
    }

    @Override
    public int getItemCount() {
        return resultModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName,txtKills,txtWin,txtRemarks,txtNo;//txtRank;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
              txtName  =  itemView.findViewById(R.id.textViewName);
              txtKills  =  itemView.findViewById(R.id.textViewKills);
              txtNo  =  itemView.findViewById(R.id.textViewNo);
//              txtRank  =  itemView.findViewById(R.id.textViewRank);
              txtWin  =  itemView.findViewById(R.id.textViewWin);
              txtRemarks  =  itemView.findViewById(R.id.textViewRemarks);
        }
    }
}
