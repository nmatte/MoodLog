package com.nmatte.mood.logbookentries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.util.DatabaseHelper;

import java.util.Calendar;

public class FlexibleLogbookEntryTableHelper {
    public static FlexibleLogbookEntry getEntry(Context context, Calendar date){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        FlexibleLogbookEntry entry = null;
        String [] columns = new String[] {
                FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_MOOD_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_NUMITEM_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_BOOLITEM_COLUMN
        };
        String [] selection = new String[] {String.valueOf(date)};

        Cursor c = db.query(LogBookContract.LOGBOOKENTRY_TABLE, columns,LogBookContract.LOGBOOKENTRY_DATE_COLUMN + "=?",selection,null,null,null);
        c.moveToFirst();
        if (c.getCount() > 0){
            entry = new FlexibleLogbookEntry(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3));
        }
        c.close();
        db.close();
        return entry;
    }

    public static void addOrUpdateEntry(Context context, FlexibleLogbookEntry entry) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN,entry.getDateInt());
        values.put(FlexibleLogbookEntryContract.ENTRY_MOOD_COLUMN,entry.getMoodString());
        values.put(FlexibleLogbookEntryContract.ENTRY_NUMITEM_COLUMN,entry.getNumString());
        values.put(FlexibleLogbookEntryContract.ENTRY_BOOLITEM_COLUMN,entry.getBoolString());

        try{
            db.insertWithOnConflict(FlexibleLogbookEntryContract.ENTRY_TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db", "", e);
        }

        db.close();
    }

}
