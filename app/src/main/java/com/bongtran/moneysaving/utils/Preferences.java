package com.bongtran.moneysaving.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.bongtran.moneysaving.app.MoneySavingApplication;
import com.bongtran.moneysaving.models.Currency;

public class Preferences {
    private SharedPreferences prefs;

    private static final String SHARED_PREFERENCES_NAME = "money_saving";
    private static final String KEY_HISTORY = "history_list";
    private static final String KEY_THIS_MONTH_ONLY = "this_month_only";
    private static final String KEY_CURRENT_CURRENCY = "current_currency";

    public Preferences() {
        prefs = MoneySavingApplication.getInstance().getApplicationContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    private static Preferences mInstance;

    public static Preferences getInstance() {
        if (null == mInstance) {
            mInstance = new Preferences();
        }
        return mInstance;
    }

    private void putIntValue(String KEY, int value) {
        prefs.edit().putInt(KEY, value).apply();
    }

    private int getIntValue(String KEY, int defValue) {
        return prefs.getInt(KEY, defValue);
    }

    private void putLongValue(String KEY, long value) {
        prefs.edit().putLong(KEY, value).apply();
    }

    private long getLongValue(String KEY, long defValue) {
        return prefs.getLong(KEY, defValue);
    }

    public void putBooleanValue(String KEY, boolean value) {
        prefs.edit().putBoolean(KEY, value).apply();
    }

    public boolean getBooleanValue(String KEY, boolean defValue) {
        return prefs.getBoolean(KEY, defValue);
    }

    public void putStringValue(String KEY, String value) {
        prefs.edit().putString(KEY, value).apply();
    }

    public String getStringValue(String KEY, String defValue) {
        return prefs.getString(KEY, defValue);
    }

    public void removeKey(String key){
        prefs.edit().remove(key).apply();
    }

    public void setLocationHistory(String listString){
        putStringValue(KEY_HISTORY, listString);
    }

    public String getLocationHistory(){
        return getStringValue(KEY_HISTORY, "");
    }

    public boolean getCalculateThisMonthOnly(){
        return getBooleanValue(KEY_THIS_MONTH_ONLY, false);
    }

    public void setCalculateThisMonthOnly(boolean bool){
        prefs.edit().putBoolean(KEY_THIS_MONTH_ONLY, bool).apply();
    }

    public Currency getCurrentCurrency(){
        Currency currency = null;
        String currencyString = getStringValue(KEY_CURRENT_CURRENCY, "");
        Log.d(">>>> C", currencyString);
        if(currencyString.isEmpty()){
            currency = Utils.getDefaultCurrency();
            Preferences.getInstance().setCurrentCurrency(Parser.parseCurrencyToString(currency));
        }else {
            currency = Parser.parseCurrency(currencyString);
        }
        return currency;
    }

    public void setCurrentCurrency(String currency){
        prefs.edit().putString(KEY_CURRENT_CURRENCY, currency).apply();
    }
}
