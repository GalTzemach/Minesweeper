package com.example.galtzemach.minesweeper.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gal Tzemach on 13/01/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "db";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBConfig.TABLE_NAME + "(" +
                    DBConfig.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DBConfig.COLUMN_PERSON_NAME + " TEXT," +
                    DBConfig.COLUMN_TIME_MS + " INTEGER," +
                    DBConfig.COLUMN_LEVEL + " INTEGER," +
                    DBConfig.COLUMN_LONG + " DOUBLE," +
                    DBConfig.COLUMN_LATI +" DOUBLE)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBConfig.TABLE_NAME;
}
