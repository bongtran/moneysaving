package com.bongtran.moneysaving.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bongtran.moneysaving.R;
import com.bongtran.moneysaving.models.Currency;
import com.bongtran.moneysaving.models.Saving;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.SavingViewHolder> {
    private Context context;
    private ArrayList<Saving> history;
    private Currency currency;

    public HistoryAdapter(Context context, ArrayList<Saving> history, Currency currency) {
        this.context = context;
        this.history = new ArrayList<>();
        this.history.addAll(history);
        this.currency = currency;
    }

    public void removeItem(Saving saving) {
        history.remove(saving);
        notifyDataSetChanged();
    }

    public void reload(ArrayList<Saving> history) {
        this.history.clear();
        this.history.addAll(history);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saving_item, parent, false);
        return new SavingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingViewHolder holder, int position) {
        final Saving saving = history.get(position);

        DateFormat format = DateFormat.getDateInstance();

        holder.tvAmount.setText(String.valueOf(saving.getAmount()));
        holder.tvCurrency.setText(saving.getCurrency().getShortName());
        holder.tvTime.setText(format.format(saving.getSavingDate()));
        if (saving.getNote().isEmpty())
            holder.tvNote.setVisibility(View.GONE);
        else
            holder.tvNote.setText(saving.getNote());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRemove(saving);
            }
        });
    }

    @Override
    public int getItemCount() {
//        Log.d(">>>getItemCount", String.valueOf(history.size()));
        return history.size();
    }

    public class SavingViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAmount, tvCurrency, tvTime, tvNote;
        public ImageButton btnRemove;

        public SavingViewHolder(View itemView) {
            super(itemView);
            tvAmount = itemView.findViewById(R.id.txt_detail_amount);
            tvCurrency = itemView.findViewById(R.id.txt_detail_currency);
            tvNote = itemView.findViewById(R.id.txt_detail_note);
            tvTime = itemView.findViewById(R.id.txt_detail_time);
            btnRemove = itemView.findViewById(R.id.btn_detail_remove);
        }
    }

    private SavingAction listener;

    public void setListener(SavingAction savingAction) {
        listener = savingAction;
    }

    public interface SavingAction {
        public void onRemove(Saving saving);
    }
}
