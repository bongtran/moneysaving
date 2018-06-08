package com.bongtran.moneysaving;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.bongtran.moneysaving.database.DataManager;
import com.bongtran.moneysaving.fragments.DialogRecyclerViewFragment;
import com.bongtran.moneysaving.models.Currencies;
import com.bongtran.moneysaving.models.Currency;
import com.bongtran.moneysaving.models.Saving;
import com.bongtran.moneysaving.utils.Parser;
import com.bongtran.moneysaving.utils.Preferences;
import com.bongtran.moneysaving.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvTotal, tvTotalCurrency, tvCurrentCurrency, tvDiscount, tvDiscountCurrency, tvDefaultCurrency;
    private EditText tvPrice, tvDiscountPercent, tvNote, tvCurrentAmount;
    private CheckBox cbThisMonth;
    private Button btnSave, btnCalculate;
    private boolean thisMonth = false;
    private DialogRecyclerViewFragment dialogRecyclerViewFragment;
    private Currency currentCurrency;
//    private Currency savingCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCurrentAmount = findViewById(R.id.txt_current_saving);
        tvCurrentCurrency = findViewById(R.id.txt_current_currency);
        tvDiscount = findViewById(R.id.txt_total_discount);
        tvDiscountPercent = findViewById(R.id.txt_discount_percent);
        tvDiscountCurrency = findViewById(R.id.txt_discount_currency);
        tvDefaultCurrency = findViewById(R.id.txt_default_currency);
        tvNote = findViewById(R.id.txt_note);
        tvPrice = findViewById(R.id.txt_price);
        tvTotal = findViewById(R.id.txt_saving_total);
        tvTotalCurrency = findViewById(R.id.txt_total_currency);
        cbThisMonth = findViewById(R.id.chk_this_month);
        btnCalculate = findViewById(R.id.btn_calculate);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);
        findViewById(R.id.btn_detail).setOnClickListener(this);
        findViewById(R.id.btn_default_currency).setOnClickListener(this);

//        calculateTotal();
        initDefaultCurrency();
//        savingCurrency = currentCurrency;
        boolean checked = Preferences.getInstance().getCalculateThisMonthOnly();
        Log.d(">>> checked save",String.valueOf(checked));
        thisMonth = checked;
        cbThisMonth.setChecked(checked);
        cbThisMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkChanged(b);
                Log.d(">>> checked",String.valueOf(b));
            }
        });

    }

    private void checkChanged(boolean b) {
        thisMonth = b;
        calculateTotal();
        Preferences.getInstance().setCalculateThisMonthOnly(b);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_calculate:
                calculateDiscount();
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_detail:
                showDetail();
                break;
            case R.id.btn_default_currency:
                chooseCurrency();
                break;
        }
    }

    private void calculateDiscount() {
        String price = tvPrice.getText().toString();
        String discountPercent = tvDiscountPercent.getText().toString();
        if (!price.isEmpty() && !discountPercent.isEmpty()) {
            float discount = Float.parseFloat(price) * Float.parseFloat(discountPercent) / 100;

            tvDiscount.setText(String.valueOf(discount));
            tvCurrentAmount.setText(String.valueOf(discount));
        }
    }

    private void calculateTotal() {
//        String got = Preferences.getInstance().getLocationHistory();
//        if (!got.isEmpty()) {
//            Log.d(">>>>", got);
//            ArrayList<Saving> history = Parser.parseSavingHistory(got);
//            float total = 0;
//            if (history != null) {
//                total = Utils.calculateTotal(history, thisMonth, currentCurrency);
//            }
//            tvTotal.setText(String.valueOf(total));
//        }
        Log.d(">>> checked cal",String.valueOf(thisMonth));
        ArrayList<Saving> history;
        if (thisMonth) {
            history = DataManager.Instance().geHistoryOfThisMonth();
        } else {
            history = DataManager.Instance().getAllHistory();
        }
        double total = 0;
        if (history != null) {
            total = Utils.calculateTotal(history, currentCurrency);
        }
        if (currentCurrency.getShortName().equals("VND") || currentCurrency.getDollarRate() >= 1000){
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            tvTotal.setText(decimalFormat.format(total));
        }
        else{
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            tvTotal.setText(decimalFormat.format(total));
        }
    }

    private void save() {
        String amount = tvCurrentAmount.getText().toString();
        String note = tvNote.getText().toString();

//        Log.d(">>>>", amount);
//        if (!amount.isEmpty() && Float.parseFloat(amount) > 0) {
//            String got = Preferences.getInstance().getLocationHistory();
//            Log.d(">>>>", got);
//            ArrayList<Saving> history = null;
//            if (!got.isEmpty()) {
//                history = Parser.parseSavingHistory(got);
//            }
//            if (history == null) {
//                history = new ArrayList<>();
//            }
//            Saving saving = new Saving(Float.parseFloat(amount), currentCurrency, new Date(), note);
//            history.add(saving);
//
//            String historyString = Parser.parseSavingHistoryToString(history);
//            MoneySavingApplication.getInstance().getPrefs().setLocationHistory(historyString);
        Saving saving = new Saving(Float.parseFloat(amount), currentCurrency, new Date(), note);
        DataManager.Instance().addSaving(saving);

        tvCurrentAmount.setText("0");
        calculateTotal();
    }

    private void showDetail() {
        Intent intent = new Intent(this, SavingHistory.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("total", 0);
        intent.putExtra("thisMonthOnly", thisMonth);
        intent.putExtra("currency", Parser.parseCurrencyToString(currentCurrency));
        startActivity(intent);
    }

    private void chooseCurrency() {
        FragmentManager fm = getSupportFragmentManager();
        dialogRecyclerViewFragment = new DialogRecyclerViewFragment();
        dialogRecyclerViewFragment.setData(Currencies.getInstance().getCurrencies());
        dialogRecyclerViewFragment.setCurrentCurrency(currentCurrency);
        dialogRecyclerViewFragment.show(fm, "TAG");
    }

    public void setCurrency(Currency currency) {
        currentCurrency = currency;
        String c = currency.getShortName() + " " + currency.getSymbol();
        tvDefaultCurrency.setText(c);
        tvCurrentCurrency.setText(currency.getSymbol());
        tvDiscountCurrency.setText(currency.getSymbol());
        tvTotalCurrency.setText(currency.getSymbol());
        Preferences.getInstance().setCurrentCurrency(Parser.parseCurrencyToString(currency));
        calculateTotal();
    }

    private void initDefaultCurrency() {
        setCurrency(Preferences.getInstance().getCurrentCurrency());
    }

    @Override
    protected void onResume() {
        super.onResume();
        calculateTotal();
    }
}
