package com.bongtran.moneysaving.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bongtran.moneysaving.MainActivity;
import com.bongtran.moneysaving.R;
import com.bongtran.moneysaving.adapter.CurrencyAdapter;
import com.bongtran.moneysaving.models.Currency;

import java.util.ArrayList;

/**
 * Created by Dat on 7/26/2016.
 */
public class DialogRecyclerViewFragment extends DialogFragment implements View.OnClickListener {
    /**
     * VIEW
     */
    private View rootView;
    private RecyclerView mRecyclerView;
//    private ProgressBar progressBar;
    private TextView btnOk;
    private TextView btnCancel;

    private Context context;
    private CurrencyAdapter adapter;
    private ArrayList<Currency> data = new ArrayList<>();
    private Currency currentCurrency;

    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //inflate layout with recycler view
        rootView = inflater.inflate(R.layout.dialog_recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        btnOk = (TextView) rootView.findViewById(R.id.btn_ok);
        btnOk.setVisibility(View.GONE);
        btnOk.setOnClickListener(this);
        btnCancel = (TextView) rootView.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);

        //setadapter
        adapter = new CurrencyAdapter(this, data, currentCurrency);
        mRecyclerView.setAdapter(adapter);


        return rootView;
    }

    public void setData(ArrayList<Currency> currencies){
        data = currencies;
//        adapter.reloadItems(currencies);
    }

    public void setCurrentCurrency(Currency currency){
//        adapter.setCurrentCurrency(currency);
        currentCurrency = currency;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_ok:
                Currency currency = data.get(adapter.mSelectedItem);
                ((MainActivity) getActivity()).setCurrency(currency);
                this.dismiss();
                break;
        }
    }

    public void showBtnOK() {
        btnOk.setVisibility(View.VISIBLE);
    }

}
