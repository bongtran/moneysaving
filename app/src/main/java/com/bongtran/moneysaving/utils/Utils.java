package com.bongtran.moneysaving.utils;

import com.bongtran.moneysaving.models.Currency;
import com.bongtran.moneysaving.models.Saving;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    public static double calculateTotal(ArrayList<Saving> history, Currency currency) {
        double result = 0;
        for (Saving saving : history) {
            result += exchange(saving.getAmount(), saving.getCurrency(), currency);
        }
        return result;
    }


    public static double calculateTotal(ArrayList<Saving> history, boolean thisMonthOnly, Currency currency) {
        double result = 0;
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        for (Saving saving : history) {
            if (thisMonthOnly) {
                Calendar saveTime = Calendar.getInstance();
                saveTime.setTime(saving.getSavingDate());
                if (saveTime.get(Calendar.MONTH) == currentMonth) {
                    result += exchange(saving.getAmount(), saving.getCurrency(), currency);
                }
            } else
                result += exchange(saving.getAmount(), saving.getCurrency(), currency);
        }
        return result;
    }

    public static double exchange(float amount, Currency source, Currency destination) {
        double result = 0;
        result = amount / source.getDollarRate() * destination.getDollarRate();
        return result;
    }

    public static String getLanguageCode() {
        return Locale.getDefault().getDisplayLanguage();
    }

    public static Currency getDefaultCurrency() {
        Currency currency = new Currency("United States dollar", "USD", "$", 1);
        if (getLanguageCode().equals("vi")) {
            currency = new Currency("Việt Nam đồng", "VND", "₫", 22785.6);
        }
        return currency;
    }

    public static long getTimeOffsetInMilis() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();

        return mTimeZone.getRawOffset();
    }
}
