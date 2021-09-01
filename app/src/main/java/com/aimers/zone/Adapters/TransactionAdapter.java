package com.aimers.zone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aimers.zone.Modals.TransactionModal;
import com.aimers.zone.R;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private final ArrayList<TransactionModal> transactions;

    public TransactionAdapter(ArrayList<TransactionModal> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransactionAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_transaction_info,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionModal transaction = transactions.get(position);
        holder.txtSn.setText(transaction.getSn());
        holder.txtStatus.setText(transaction.getStatus());
        holder.txtRemarks.setText(transaction.getRemarks());
        holder.txtRs.setText(transaction.getRs());
        holder.txtType.setText(transaction.getType());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtRs,txtSn,txtType,txtStatus,txtRemarks;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRemarks = itemView.findViewById(R.id.textView48Remarks);
            txtStatus = itemView.findViewById(R.id.textView46Status);
            txtRs = itemView.findViewById(R.id.textView44RS);
            txtSn = itemView.findViewById(R.id.textView3Sno);
            txtType = itemView.findViewById(R.id.textView45TYpe);
        }
    }
}
