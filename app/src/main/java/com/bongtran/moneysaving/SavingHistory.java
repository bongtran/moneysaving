package com.bongtran.moneysaving;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bongtran.moneysaving.adapter.HistoryAdapter;
import com.bongtran.moneysaving.app.MoneySavingApplication;
import com.bongtran.moneysaving.database.DataManager;
import com.bongtran.moneysaving.models.Currency;
import com.bongtran.moneysaving.models.Saving;
import com.bongtran.moneysaving.utils.Parser;
import com.bongtran.moneysaving.utils.Preferences;
import com.bongtran.moneysaving.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SavingHistory extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvTotal, tvTotalCurrency;
    private CheckBox cbThisMonth;
    private ArrayList<Saving> history;
    private HistoryAdapter adapter;
    private HistoryAdapter.SavingAction listener;
    private boolean thisMonth = false;
    private Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_history);
        thisMonth = getIntent().getBooleanExtra("thisMonthOnly", false);
        String currencyString = getIntent().getStringExtra("currency");
        currency = Parser.parseCurrency(currencyString);
        tvTotal = findViewById(R.id.txt_saving_total);
        tvTotalCurrency = findViewById(R.id.txt_total_currency);
        tvTotalCurrency.setText(currency.getSymbol());
        cbThisMonth = findViewById(R.id.chk_this_month);
        cbThisMonth.setChecked(thisMonth);
        cbThisMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkChanged(b);
            }
        });
        history = new ArrayList<>();
        recyclerView = findViewById(R.id.lst_history);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(this, history, currency);
        adapter.setListener(new HistoryAdapter.SavingAction() {
            @Override
            public void onRemove(Saving saving) {
                removeSaving(saving);
            }
        });
        recyclerView.setAdapter(adapter);
        loadHistory();
        calculateTotal();
    }

    private void loadHistory() {
//        String got = Preferences.getInstance().getLocationHistory();
//        if (!got.isEmpty()) {
//           history = Parser.parseSavingHistory(got);
//            if (history != null) {
//                if (thisMonth) {
//                    ArrayList<Saving> list = new ArrayList<>();
//                    for (Saving saving : history) {
//                        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
//                        Calendar saveTime = Calendar.getInstance();
//                        saveTime.setTime(saving.getSavingDate());
//                        if (saveTime.get(Calendar.MONTH) == currentMonth) {
//                            list.add(saving);
//                        }
//                    }
//                    adapter.reload(list);
//                } else {
//                    adapter.reload(history);
//                }
//            }
//        }

//        if (thisMonth) {
//            history = DataManager.Instance().geHistoryOfThisMonth();
//        } else {
//            history = DataManager.Instance().getAllHistory();
//        }
//        history = DataManager.Instance().getAllHistory();
//        adapter.reload(history);

        history = DataManager.Instance().getAllHistory();
        if (history != null) {
            if (thisMonth) {
                ArrayList<Saving> list = new ArrayList<>();
                for (Saving saving : history) {
                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                    Calendar saveTime = Calendar.getInstance();
                    saveTime.setTime(saving.getSavingDate());
                    if (saveTime.get(Calendar.MONTH) == currentMonth) {
                        list.add(saving);
                    }
                }
                adapter.reload(list);
            } else {
                adapter.reload(history);
            }
        }
    }

    private void reloadHistory() {
        if (history != null) {
            if (thisMonth) {
                ArrayList<Saving> list = new ArrayList<>();
                for (Saving saving : history) {
                    int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
                    Calendar saveTime = Calendar.getInstance();
                    saveTime.setTime(saving.getSavingDate());
                    if (saveTime.get(Calendar.MONTH) == currentMonth) {
                        list.add(saving);
                    }
                }
                adapter.reload(list);
            } else {
                adapter.reload(history);
            }
        }
    }

    private void removeSaving(Saving saving) {
        history.remove(saving);
        adapter.removeItem(saving);
//        saveHistory();
        DataManager.Instance().deleteSaving(saving);
        calculateTotal();
    }

    private void saveHistory() {
        String historyString = Parser.parseSavingHistoryToString(history);
        MoneySavingApplication.getInstance().getPrefs().setLocationHistory(historyString);
    }

    private void checkChanged(boolean b) {
        thisMonth = b;
        calculateTotal();
        reloadHistory();
//        Preferences.getInstance().setCalculateThisMonthOnly(b);
    }

    private void calculateTotal() {
        double total = 0;
        if (history != null) {
            total = Utils.calculateTotal(history, thisMonth, currency);
        }
        if (currency.getShortName().equals("VND") || currency.getDollarRate() >= 1000){
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            tvTotal.setText(decimalFormat.format(total));
        }
        else{
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            tvTotal.setText(decimalFormat.format(total));
        }
    }
}
