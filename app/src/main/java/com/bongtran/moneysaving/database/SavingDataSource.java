package com.bongtran.moneysaving.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.bongtran.moneysaving.models.Saving;
import com.bongtran.moneysaving.utils.Parser;
import com.bongtran.moneysaving.utils.TimeUtils;
import java.util.ArrayList;
import java.util.Calendar;

public class SavingDataSource extends DataSourceBase {
    public SavingDataSource(Context context) {
        super(context);
    }

    public ArrayList<Saving> getSaving() {
        ArrayList<Saving> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_HISTORY ;

        Cursor cursor = database.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String savingString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA));
                    long time = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.CREATED_DATE));
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SAVING_ID));
                    if (savingString != null && !savingString.isEmpty()) {
                        Saving saving = Parser.parseSaving(savingString);
                        saving.setId(id);
                        result.add(saving);
                    }
                }
                while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }


        return result;
    }

    public ArrayList<Saving> getSavingOfThisMonth(long day) {
        ArrayList<Saving> result = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_HISTORY + " WHERE " + DatabaseHelper.CREATED_DATE + ">" + day;

        Cursor cursor = database.rawQuery(selectQuery, null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SAVING_ID));
                    String savingString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATA));
                    if (savingString != null && !savingString.isEmpty()) {
                        Saving saving = Parser.parseSaving(savingString);
                        saving.setId(id);
                        result.add(saving);
                    }
                }
                while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }


        return result;
    }

    public boolean addSaving(Saving tc) {
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DATA, Parser.parseSavingToString(tc));
            values.put(DatabaseHelper.CREATED_DATE, Calendar.getInstance().getTimeInMillis());

            long val = database.insert(DatabaseHelper.TABLE_HISTORY, null, values);

            return val >= 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteSaving(Saving saving){
        try {
            database.delete(DatabaseHelper.TABLE_HISTORY, DatabaseHelper.SAVING_ID + "=?", new String[]{String.valueOf(saving.getId())});
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean clearData() {
        try {
            String deleteQuery = "DELETE FROM " + DatabaseHelper.TABLE_HISTORY;
            database.execSQL(deleteQuery);
//            database.delete(TABLE_TIME_CARD, null, null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
