package com.nmatte.mood.logbookentries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.logbookitems.LogbookItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.util.DatabaseHelper;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Iterator;

public class ChartEntryTableHelper {

    public static ArrayList<ChartEntry> getEntryGroup(Context context, DateTime startDate, DateTime endDate){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        String [] columns = new String [] {
                ChartEntryContract.ENTRY_DATE_COLUMN,
                ChartEntryContract.ENTRY_MOOD_COLUMN,
                ChartEntryContract.ENTRY_NUMITEM_COLUMN,
                ChartEntryContract.ENTRY_BOOLITEM_COLUMN
        };

        String [] selection = new String[] {
                String.valueOf(startDate.toLocalDate().toString("YYYYDDD")),
                        String.valueOf(endDate.toLocalDate().toString("YYYYDDD"))
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
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYYDDD");
                    ChartEntry entry = new ChartEntry(
                            DateTime.parse(c.getString(0),formatter),
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

    public static ArrayList<ChartEntry> getGroupWithBlanks(Context context, DateTime startDate, DateTime endDate){
        // swap dates if out of order
        if (startDate.isAfter(endDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        int numDays = Days.daysBetween(startDate.toLocalDate(),endDate.toLocalDate()).getDays();
        // Joda-time's daysBetween function takes the end date as exclusive.
        // We need to add 1 so that numDays includes the end date.
        numDays++;
        ArrayList<ChartEntry> sparseEntries = getEntryGroup(context, startDate, endDate);
        ArrayList<ChartEntry> result = new ArrayList<>();
        DateTime currentDate = new DateTime(startDate);

        ChartEntry currentEntry = null;
        Iterator<ChartEntry> it = sparseEntries.iterator();
        if (it.hasNext()){
            currentEntry = it.next();
        }

        // for the number of days between start and end
        for (int i = 0; i < numDays; i++){
           if (currentEntry != null){
               // if current entry (from sparse entries) has same as current date
               if (currentEntry.getLogDate().getDayOfYear() == currentDate.getDayOfYear()){
                   result.add(currentEntry);
                   if (it.hasNext())
                       currentEntry = it.next();
               } else {
               // current entry doesn't have same date so just add a "blank" one
                   result.add(new ChartEntry(currentDate));
               }
           } else {
               result.add(new ChartEntry(currentDate));
           }
            currentDate = currentDate.plusDays(1);
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
