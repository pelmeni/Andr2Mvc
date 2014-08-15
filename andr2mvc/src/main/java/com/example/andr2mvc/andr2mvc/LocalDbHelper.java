package com.example.andr2mvc.andr2mvc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pichuginsi on 12.08.2014.
 */
public class LocalDbHelper extends SQLiteOpenHelper {

    final String LOG_TAG = "myLogs";

    static final String DB_PROPERTIES_CREATE="create table properties (id integer primary key autoincrement,name text,value text" + ");";

    static final String DB_NAME="testDb";

    static final int DB_VERSION=1;


    public LocalDbHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(LOG_TAG, "--- onCreate database ---");

        db.execSQL(DB_PROPERTIES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
