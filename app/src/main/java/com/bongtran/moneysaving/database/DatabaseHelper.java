package com.bongtran.moneysaving.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Table contact
    public static final String TABLE_HISTORY = "HISTORY";
    public static final String SAVING_ID = "Id";
    public static final String DATA = "Data";
    public static final String CREATED_DATE = "CreatedDate";
    public static final String DELETED = "Deleted";

    static final String CREATE_HISTORY_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_HISTORY + " ("
            + SAVING_ID + " INTEGER PRIMARY KEY autoincrement NOT NULL, "
            + DATA + " TEXT, "
            + CREATED_DATE + " INTEGER, "
            + DELETED + " INTEGER DEFAULT 0"
            + ") ";

    static final String dbName = "dbsaving";

    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_HISTORY_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
