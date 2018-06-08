package com.bongtran.moneysaving.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bongtran.moneysaving.R;
import com.bongtran.moneysaving.fragments.DialogRecyclerViewFragment;
import com.bongtran.moneysaving.models.Currency;

import java.util.ArrayList;

/**
 * Created by Dat on 7/26/2016.
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private ArrayList<Currency> data;
    private DialogRecyclerViewFragment context;
    private boolean isChecked = false;
    private RadioButton lastCheckedRB = null;

    public int mSelectedItem = -1;


    public CurrencyAdapter(DialogRecyclerViewFragment context, ArrayList<Currency> data) {
        this.data = data;
        this.context = context;
    }

    public CurrencyAdapter(DialogRecyclerViewFragment context, ArrayList<Currency> data, Currency currency) {
        this.data = data;
        this.context = context;
        mSelectedItem = getCurrentIndex(currency);
    }

    public void reloadItems(ArrayList<Currency> currencies){
        data.clear();
        data.addAll(currencies);
        notifyDataSetChanged();
    }

    public void setCurrentCurrency(Currency currency){
        mSelectedItem = getCurrentIndex(currency);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_currency, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        /** GET ITEM */
        Currency item = data.get(position);

        String name = item.getShortName() + " " + item.getSymbol();
        holder.tvBeacon.setText(name);

        holder.radioButton.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
//        Log.d(">>>>", String.valueOf(data.size()));
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout;
        private TextView tvBeacon;
        private RadioButton radioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout) itemView.findViewById(R.id.layout_currency);
            tvBeacon = (TextView) itemView.findViewById(R.id.item_tv_currency);
            radioButton = (RadioButton) itemView.findViewById(R.id.radio_button);
            radioButton.setClickable(false);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedItem = getAdapterPosition();
                    if(mSelectedItem!=-1)
                    {
                        context.showBtnOK();
                    }
                    notifyItemRangeChanged(0, data.size());
                }
            };
            layout.setOnClickListener(clickListener);
        }
    }

    private int getCurrentIndex(Currency currency){
        int index = - 1;
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getShortName().equals(currency.getShortName())){
                index = i;
                break;
            }
        }
        return index;
    }
}
