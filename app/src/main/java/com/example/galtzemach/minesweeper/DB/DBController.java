package com.example.galtzemach.minesweeper.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galtzemach.minesweeper.logic.Record;

import java.util.ArrayList;

/**
 * Created by Gal Tzemach on 12/01/2017.
 */

public class DBController {
    private DbHelper dbHelper;
    private SQLiteDatabase dbForWrite;
    private SQLiteDatabase dbForRead;
    private static final String TAG = "DbController";


    public DBController(Context context) {
        this.dbHelper = new DbHelper(context);
        this.dbForWrite     = dbHelper.getWritableDatabase();
        this.dbForRead      = dbHelper.getReadableDatabase();
    }

    public void addNewRecord(String name, int timeMs, int level, double longitude, double latitude){

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBConfig.COLUMN_PERSON_NAME, name);
        contentValues.put(DBConfig.COLUMN_TIME_MS, timeMs);
        contentValues.put(DBConfig.COLUMN_LEVEL, level);
        contentValues.put(DBConfig.COLUMN_LONG, longitude);
        contentValues.put(DBConfig.COLUMN_LATI, latitude);

        long newRowId = dbForWrite.insert(DBConfig.TABLE_NAME, null, contentValues);

        if (newRowId != -1)
            Log.v(TAG, "Insert new record");
        else
            Log.v(TAG, "Error: can't Insert new record");
    }

    public ArrayList<Record> getRecords(int level){

        ArrayList<Record> recordsArr = new ArrayList<>();

        String[] projection = {
                DBConfig.COLUMN_ID,
                DBConfig.COLUMN_PERSON_NAME,
                DBConfig.COLUMN_TIME_MS,
                DBConfig.COLUMN_LEVEL,
                DBConfig.COLUMN_LONG,
                DBConfig.COLUMN_LATI
        };

        String selection = DBConfig.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { ((Integer)level).toString() };

        String sortOrder = DBConfig.COLUMN_TIME_MS + " ASC";

        String limit = DBConfig.LIMIT;

        Cursor cursor = dbForRead.query(
                DBConfig.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder,
                limit
        );

        while(cursor.moveToNext()) {
            int recordId = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBConfig.COLUMN_ID));
            String personName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DBConfig.COLUMN_PERSON_NAME));
            int timeMs = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DBConfig.COLUMN_TIME_MS));
            double longitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DBConfig.COLUMN_LONG));
            double latitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DBConfig.COLUMN_LATI));

            Record record = new Record(recordId, timeMs, level, personName, longitude, latitude);

            recordsArr.add(record);
        }
        cursor.close();

        return recordsArr;
    }
}
