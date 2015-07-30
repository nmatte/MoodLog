package com.nmatte.mood.logbookentries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.CalendarDatabaseUtil;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class ChartEntryTableHelper {
    public static ChartEntry getEntry(Context context, Calendar date){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String [] columns = new String[] {
                ChartEntryContract.ENTRY_DATE_COLUMN,
                ChartEntryContract.ENTRY_MOOD_COLUMN,
                ChartEntryContract.ENTRY_NUMITEM_COLUMN,
                ChartEntryContract.ENTRY_BOOLITEM_COLUMN
        };
        String [] selection = new String[] {String.valueOf(date)};

        Cursor c = db.query(
                LogBookContract.LOGBOOKENTRY_TABLE,
                columns,
                LogBookContract.LOGBOOKENTRY_DATE_COLUMN + "=?",
                selection,
                null,null,null);

        ChartEntry entry = null;

        c.moveToFirst();
        if (c.getCount() > 0){
            entry = new ChartEntry(
                    CalendarDatabaseUtil.intToCalendar(c.getInt(0)),
                    ChartEntry.parseMoodString(c.getString(1)),
                    NumItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(2))),
                    BoolItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(3))));
        }
        c.close();
        db.close();
        return entry;
    }

    public static ArrayList<ChartEntry> getEntryGroup(Context context, Calendar startDate, Calendar endDate){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String [] columns = new String [] {
                ChartEntryContract.ENTRY_DATE_COLUMN,
                ChartEntryContract.ENTRY_MOOD_COLUMN,
                ChartEntryContract.ENTRY_NUMITEM_COLUMN,
                ChartEntryContract.ENTRY_BOOLITEM_COLUMN
        };

        String [] selection = new String[] {
                String.valueOf(CalendarDatabaseUtil.calendarToInt(startDate)),
                        String.valueOf(CalendarDatabaseUtil.calendarToInt(endDate))
        };

        Cursor c = db.query(
                ChartEntryContract.ENTRY_TABLE_NAME,
                columns,
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null, null, null);

        ArrayList<ChartEntry> result = new ArrayList<>();

        c.moveToFirst();
        if (c.getCount() > 0){
            do {
                try {
                    ChartEntry entry = new ChartEntry(
                            CalendarDatabaseUtil.intToCalendar(c.getInt(0)),
                            ChartEntry.parseMoodString(c.getString(1)),
                            NumItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(2))),
                            BoolItem.mapFromStringArray(LogbookItem.extractStringArray(c.getString(3))));

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

    public static ArrayList<ChartEntry> getGroupWithBlanks(Context context, Calendar startDate, Calendar endDate){
        ArrayList<ChartEntry> sparseEntries = getEntryGroup(context,startDate,endDate);
        ArrayList<ChartEntry> result = new ArrayList<>();
        Iterator<ChartEntry> it = sparseEntries.iterator();
        ChartEntry currentEntry = null;
        if(it.hasNext())
            currentEntry = it.next();

        // fill the result array with entries marked blank for dates that don't have an entry saved
        for (Calendar date : CalendarDatabaseUtil.datesBetween(startDate,endDate)){
            if(currentEntry != null){
                if (CalendarDatabaseUtil.sameDayOfYear(currentEntry.getDate(),date)){
                    result.add(currentEntry);
                    currentEntry = it.next();
                } else {
                    result.add(ChartEntry.getBlankEntry(date));
                }
            } else {
                result.add(ChartEntry.getBlankEntry(date));
            }
        }


        return result;
    }

    public static void addOrUpdateEntry(Context context, ChartEntry entry) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChartEntryContract.ENTRY_DATE_COLUMN,entry.getDateInt());
        values.put(ChartEntryContract.ENTRY_MOOD_COLUMN,entry.getMoodString());
        values.put(ChartEntryContract.ENTRY_NUMITEM_COLUMN,entry.getNumMapString());
        values.put(ChartEntryContract.ENTRY_BOOLITEM_COLUMN,entry.getBoolMapString());

        try{
            db.insertWithOnConflict(ChartEntryContract.ENTRY_TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db", "", e);
        }

        db.close();
    }

}
