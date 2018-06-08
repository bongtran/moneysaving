package com.bongtran.moneysaving.app;

import android.app.Application;

import com.bongtran.moneysaving.utils.Preferences;

public class MoneySavingApplication extends Application {
    private static MoneySavingApplication _instance;

    private static Preferences mPrefs;

    public static synchronized MoneySavingApplication getInstance() {
        return _instance;
    }

    private static void initPreferences() {
        mPrefs = Preferences.getInstance();
    }

    public Preferences getPrefs() {
        return mPrefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initPreferences();
    }
}
