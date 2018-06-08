package com.bongtran.moneysaving.utils;

import com.bongtran.moneysaving.models.Currency;
import com.bongtran.moneysaving.models.Saving;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Parser {
    public static ArrayList<Saving> parseSavingHistory(String listString){
        ArrayList<Saving> list = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Saving>>(){}.getType();
        list = new Gson().fromJson(listString, type);
        return list;
    }

    public static String parseSavingHistoryToString(ArrayList<Saving> list){
        return new Gson().toJson(list);
    }

    public static Currency parseCurrency(String currencyString){
       return new Gson().fromJson(currencyString, Currency.class);
    }

    public static String parseCurrencyToString(Currency currency){
        return new Gson().toJson(currency);
    }

    public static Saving parseSaving(String currencyString){
        return new Gson().fromJson(currencyString, Saving.class);
    }

    public static String parseSavingToString(Saving currency){
        return new Gson().toJson(currency);
    }
}
