package com.bongtran.moneysaving.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ASUS on 2/21/2017.
 */

public class DataSourceBase {
    protected Context _context;
    protected SQLiteDatabase database;
    protected DatabaseHelper dbHelper;

    public DataSourceBase(Context context)
    {
        _context = context;
        dbHelper = new DatabaseHelper(_context);
    }



    public void open() throws SQLException
    {
        database = dbHelper.getWritableDatabase();
    }

    public void close()
    {
        dbHelper.close();
    }
}
