package com.nmatte.mood.logbookentries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.util.CalendarDatabaseUtil;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class LogbookEntryTableHelper {
    public static LogbookEntry getEntry(Context context, int date){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        LogbookEntry e = null;
        String [] columns = new String[] {
                LogBookContract.LOGBOOKENTRY_DATE_COLUMN,
                LogBookContract.LOGBOOKENTRY_MOOD_COLUMN,
                LogBookContract.LOGBOOKENTRY_IRRITABILITY_COLUMN,
                LogBookContract.LOGBOOKENTRY_ANXIETY_COLUMN,
                LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_COLUMN,
                LogBookContract.LOGBOOKENTRY_MEDICATION_COLUMN
        };
        String [] selection = new String[] {String.valueOf(date)};

        Cursor c = db.query(LogBookContract.LOGBOOKENTRY_TABLE, columns,LogBookContract.LOGBOOKENTRY_DATE_COLUMN + "=?",selection,null,null,null);
        c.moveToFirst();
        if (c.getCount() > 0){
            e = new LogbookEntry(
                    c.getInt(0),
                    c.getString(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getString(5));
        }
        c.close();
        db.close();
        return e;
    }

    public static LogbookEntry getEntryToday(Context context){
        return getEntry(context, CalendarDatabaseUtil.calendarToInt(Calendar.getInstance()));
    }



    public static ArrayList<LogbookEntry> getEntryGroup (Context context, Calendar startDate, Calendar endDate){
        //TODO ensure this works properly
        ArrayList<LogbookEntry> entries = new ArrayList<>();
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String [] columns = new String[] {
                LogBookContract.LOGBOOKENTRY_DATE_COLUMN,
                LogBookContract.LOGBOOKENTRY_MOOD_COLUMN,
                LogBookContract.LOGBOOKENTRY_IRRITABILITY_COLUMN,
                LogBookContract.LOGBOOKENTRY_ANXIETY_COLUMN,
                LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_COLUMN,
                LogBookContract.LOGBOOKENTRY_MEDICATION_COLUMN
        };
        String [] selection = new String[] {String.valueOf(CalendarDatabaseUtil.calendarToInt(startDate)),
                String.valueOf(CalendarDatabaseUtil.calendarToInt(endDate))};

        Cursor c = db.query(LogBookContract.LOGBOOKENTRY_TABLE, columns,LogBookContract.LOGBOOKENTRY_DATE_COLUMN + " BETWEEN ? AND ?",selection,null,null,null);
        c.moveToFirst();
        if (c.getCount() > 0){
            do {
                try {
                    entries.add(new LogbookEntry(c.getInt(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getInt(3),
                            c.getInt(4),
                            c.getString(5)));
                } catch (Exception e){
                    Log.e("db","",e);
                }
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return entries;
    }

    public static ArrayList<LogbookEntry> getGroupWithBlanks(Context context, Calendar startDate, Calendar endDate){
        ArrayList<LogbookEntry> result = new ArrayList<>();
        ArrayList<LogbookEntry> fullEntries = getEntryGroup(context, startDate, endDate);
        Iterator<LogbookEntry> it = fullEntries.iterator();
        ArrayList<Calendar> dates = CalendarDatabaseUtil.datesBetween(startDate, endDate);
        LogbookEntry currentEntry = null;
        if (it.hasNext())
            currentEntry = it.next();


        // map an array of dates to an array of entries; null for no entry
        for (Calendar date : dates){
            if ((currentEntry != null)){
                // if the dates match then add to result...
                if (CalendarDatabaseUtil.sameDayOfYear(date,currentEntry.getDate())){
                currentEntry.setDate(date);
                result.add(currentEntry);
                    // and get next entry to check
                    if(it.hasNext()){
                        currentEntry = it.next();
                    }
                } else {
                    // not the same so add a null and move on
                    LogbookEntry newEntry = new LogbookEntry(date);
                    newEntry.blank = true;
                    result.add(newEntry);
                }
            } else {
                // no more entries so add null
                // result.add(null);
            }
        }
        return result;
    }



    public static void addOrUpdateEntry(Context context, LogbookEntry entry) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LogBookContract.LOGBOOKENTRY_DATE_COLUMN,entry.getDateInt());
        values.put(LogBookContract.LOGBOOKENTRY_MOOD_COLUMN,entry.moodString());
        values.put(LogBookContract.LOGBOOKENTRY_ANXIETY_COLUMN,entry.getAnxValue());
        values.put(LogBookContract.LOGBOOKENTRY_IRRITABILITY_COLUMN,entry.getIrrValue());
        values.put(LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_COLUMN,entry.getHoursSleptValue());
        values.put(LogBookContract.LOGBOOKENTRY_MEDICATION_COLUMN,entry.medicationString());

        try{
            db.insertWithOnConflict(LogBookContract.LOGBOOKENTRY_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db","",e);
        }

        db.close();
    }
}


