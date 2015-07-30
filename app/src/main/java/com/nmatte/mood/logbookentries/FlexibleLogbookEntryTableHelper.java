package com.nmatte.mood.logbookentries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.util.CalendarDatabaseUtil;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class FlexibleLogbookEntryTableHelper {
    public static FlexibleLogbookEntry getEntry(Context context, Calendar date){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String [] columns = new String[] {
                FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_MOOD_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_NUMITEM_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_BOOLITEM_COLUMN
        };
        String [] selection = new String[] {String.valueOf(date)};

        Cursor c = db.query(
                LogBookContract.LOGBOOKENTRY_TABLE,
                columns,
                LogBookContract.LOGBOOKENTRY_DATE_COLUMN + "=?",
                selection,
                null,null,null);

        FlexibleLogbookEntry entry = null;

        c.moveToFirst();
        if (c.getCount() > 0){
            ArrayList<NumItem> newNumItems = NumItemTableHelper.getAll(context);
            ArrayList<BoolItem> newBoolItems = BoolItemTableHelper.getAll(context);
            entry = new FlexibleLogbookEntry(
                    CalendarDatabaseUtil.intToCalendar(c.getInt(0)),
                    FlexibleLogbookEntry.parseMoodString(c.getString(1)),
                    NumItem.refreshMap(newNumItems,NumItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(2)))),
                    BoolItem.refreshMap(newBoolItems,BoolItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(3)))));
        }
        c.close();
        db.close();
        return entry;
    }

    public static ArrayList<FlexibleLogbookEntry> getEntryGroup(Context context, Calendar startDate, Calendar endDate){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String [] columns = new String [] {
                FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_MOOD_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_NUMITEM_COLUMN,
                FlexibleLogbookEntryContract.ENTRY_BOOLITEM_TYPE
        };

        String [] selection = new String[] {
                String.valueOf(CalendarDatabaseUtil.calendarToInt(startDate)),
                        String.valueOf(CalendarDatabaseUtil.calendarToInt(endDate))
        };

        Cursor c = db.query(
                FlexibleLogbookEntryContract.ENTRY_TABLE_NAME,
                columns,
                FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null,null,null);

        ArrayList<FlexibleLogbookEntry> result = new ArrayList<>();
        ArrayList<NumItem> newNumItems = NumItemTableHelper.getAll(context);
        ArrayList<BoolItem> newBoolItems = BoolItemTableHelper.getAll(context);

        c.moveToFirst();
        if (c.getCount() > 0){
            do {
                try {
                    FlexibleLogbookEntry entry = new FlexibleLogbookEntry(
                            CalendarDatabaseUtil.intToCalendar(c.getInt(0)),
                            FlexibleLogbookEntry.parseMoodString(c.getString(1)),
                            NumItem.refreshMap(newNumItems,NumItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(2)))),
                            BoolItem.refreshMap(newBoolItems,BoolItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(3)))));

                    result.add(entry);
                } catch (Exception e){
                    e.printStackTrace();
                }
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return result;

    }

    public static ArrayList<FlexibleLogbookEntry> getGroupWithBlanks(Context context, Calendar startDate, Calendar endDate){
        ArrayList<FlexibleLogbookEntry> result = new ArrayList<>();

        return result;
    }

    public static void addOrUpdateEntry(Context context, FlexibleLogbookEntry entry) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FlexibleLogbookEntryContract.ENTRY_DATE_COLUMN,entry.getDateInt());
        values.put(FlexibleLogbookEntryContract.ENTRY_MOOD_COLUMN,entry.getMoodString());
        values.put(FlexibleLogbookEntryContract.ENTRY_NUMITEM_COLUMN,entry.getNumMapString());
        values.put(FlexibleLogbookEntryContract.ENTRY_BOOLITEM_COLUMN,entry.getBoolMapString());

        try{
            db.insertWithOnConflict(FlexibleLogbookEntryContract.ENTRY_TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db", "", e);
        }

        db.close();
    }

}
