package com.bongtran.moneysaving.models;

import java.util.ArrayList;

public class Currencies {
    private ArrayList<Currency> list;
    private static  Currencies instance;
    private Currencies(){
        init();
    }

    public static synchronized Currencies getInstance(){
        if(instance == null)
            instance = new Currencies();

        return instance;
    }

    private void init(){
        list = new ArrayList<>();

        list.add(new Currency("United States dollar", "USD", "$", 1));
        list.add(new Currency("British pound", "GBP", "£", 0.72493));
        list.add(new Currency("Australian dollar", "AUD", "$", 1.30888));
        list.add(new Currency("Canadian dollar", "CAD", "$", 1.28476));
        list.add(new Currency("Chinese yuan", "CNY", "元", 6.33431));
        list.add(new Currency("Euro", "EUR", "€", 0.82680));
        list.add(new Currency("Indian rupee", "INR", "₹", 66.2236));
        list.add(new Currency("Israeli new shekel", "ILS", "₪", 3.53815));
        list.add(new Currency("Japanese yen", "JPY", "¥", 108.013));
        list.add(new Currency("Singapore dollar", "SGD", "$", 1.32471));
        list.add(new Currency("South Korean won", "KRW", "₩", 899.903));
        list.add(new Currency("Việt Nam đồng", "VND", "₫", 22785.6));
    }

    public ArrayList<Currency> getCurrencies() {
        return list;
    }
}
