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

public class DbController {
    private DbHelper dbHelper;
    private SQLiteDatabase dbForWrite;
    private SQLiteDatabase dbForRead;
    private static final String TAG = "DbController";

    public DbController(Context context) {
        this.dbHelper = new DbHelper(context);
        this.dbForWrite     = dbHelper.getWritableDatabase();
        this.dbForRead      = dbHelper.getReadableDatabase();
    }

    public void addNewRecord(String name, int timeMs, int level, double longitude, double latitude){

        ContentValues contentValues = new ContentValues();

        contentValues.put(DbConfig.COLUMN_PERSON_NAME, name);
        contentValues.put(DbConfig.COLUMN_TIME_MS, timeMs);
        contentValues.put(DbConfig.COLUMN_LEVEL, level);
        contentValues.put(DbConfig.COLUMN_LONG, longitude);
        contentValues.put(DbConfig.COLUMN_LATI, latitude);

        long newRowId = dbForWrite.insert(DbConfig.TABLE_NAME, null, contentValues);

        if (newRowId != -1)
            Log.v(TAG, "Insert new record");
        else
            Log.v(TAG, "Error: can't Insert new record");
    }

    public ArrayList<Record> getRecords(int level){

        ArrayList<Record> recordsArr = new ArrayList<>();

        String[] projection = {
                DbConfig.COLUMN_ID,
                DbConfig.COLUMN_PERSON_NAME,
                DbConfig.COLUMN_TIME_MS,
                DbConfig.COLUMN_LEVEL,
                DbConfig.COLUMN_LONG,
                DbConfig.COLUMN_LATI
        };

        String selection = DbConfig.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { ((Integer)level).toString() };

        String sortOrder = DbConfig.COLUMN_TIME_MS + " ASC";

        String limit = DbConfig.LIMIT;

        Cursor cursor = dbForRead.query(
                DbConfig.TABLE_NAME,
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
                    cursor.getColumnIndexOrThrow(DbConfig.COLUMN_ID));
            String personName = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbConfig.COLUMN_PERSON_NAME));
            int timeMs = cursor.getInt(
                    cursor.getColumnIndexOrThrow(DbConfig.COLUMN_TIME_MS));
            double longitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DbConfig.COLUMN_LONG));
            double latitude = cursor.getDouble(
                    cursor.getColumnIndexOrThrow(DbConfig.COLUMN_LATI));

            Record record = new Record(recordId, timeMs, level, personName, longitude, latitude);

            recordsArr.add(record);
        }
        cursor.close();

        return recordsArr;
    }
}
