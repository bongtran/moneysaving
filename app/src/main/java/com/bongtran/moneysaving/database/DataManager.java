package com.bongtran.moneysaving.database;

import android.content.Context;

import com.bongtran.moneysaving.app.MoneySavingApplication;
import com.bongtran.moneysaving.models.Saving;
import com.bongtran.moneysaving.utils.TimeUtils;
import com.bongtran.moneysaving.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Shazam_ORG on 12/20/2017.
 */

public class DataManager {
    static DataManager _instance;
    static Context _context;
    final static String _lock = "";

    DataManager() {
    }

    public static DataManager Instance() {
        synchronized (_lock) {
            if (_instance == null) {
                _instance = new DataManager();
                initDataManager(MoneySavingApplication.getInstance());
            }
            return _instance;
        }
    }

    private static void initDataManager(Context applicationContext) {
        _context = applicationContext;
    }

    public ArrayList<Saving> getAllHistory() {
        ArrayList<Saving> result = new ArrayList<>();

        SavingDataSource dataSource = new SavingDataSource(_context);
        dataSource.open();
        result = dataSource.getSaving();
        dataSource.close();
        return result;
    }

    public ArrayList<Saving> geHistoryOfThisMonth() {
        ArrayList<Saving> result = new ArrayList<>();

        SavingDataSource dataSource = new SavingDataSource(_context);
        dataSource.open();
        result = dataSource.getSavingOfThisMonth(TimeUtils.getFirstTimeOfThisMonth());
        dataSource.close();
        return result;
    }

    public boolean addSaving(Saving saving) {
        SavingDataSource dataSource = new SavingDataSource(_context);
        dataSource.open();
        boolean result = dataSource.addSaving(saving);
        dataSource.close();
        return result;
    }

    public boolean deleteSaving(Saving saving){
        SavingDataSource dataSource = new SavingDataSource(_context);
        dataSource.open();
        boolean result = dataSource.deleteSaving(saving);
        dataSource.close();

        return result;
    }

    public void clearData() {

    }
}
