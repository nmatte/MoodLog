package com.nmatte.mood.logbookentries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.moodlog.DatabaseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Nathan on 4/2/2015.
 */
public class LogbookEntryTableHelper {
    DatabaseHelper DBHelper;
    Context context;

    public LogbookEntryTableHelper (Context c){
        context = c;
        DBHelper = new DatabaseHelper(c);
    }

    public void addOrUpdateEntry(LogbookEntry entry){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LogBookContract.LOGBOOKENTRY_DATE_COLUMN,entry.getDate());
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


    public LogbookEntry getEntry(int date){
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

    public LogbookEntry getEntryToday(){
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String ds = df.format(c.getTime());
        int date = Integer.valueOf(ds);
        return getEntry(date);
    }

    public ArrayList<LogbookEntry> getEntryGroup (int startDate, int endDate){
        //TODO ensure this works properly
        ArrayList<LogbookEntry> entries = new ArrayList<>();
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String [] columns = new String[] {
                LogBookContract.LOGBOOKENTRY_DATE_COLUMN,
                LogBookContract.LOGBOOKENTRY_MOOD_COLUMN,
                LogBookContract.LOGBOOKENTRY_IRRITABILITY_COLUMN,
                LogBookContract.LOGBOOKENTRY_ANXIETY_COLUMN,
                LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_COLUMN,
                LogBookContract.LOGBOOKENTRY_MEDICATION_COLUMN
        };
        String [] selection = new String[] {String.valueOf(startDate), String.valueOf(endDate)};

        Cursor c = db.query(LogBookContract.LOGBOOKENTRY_TABLE, columns,LogBookContract.LOGBOOKENTRY_DATE_COLUMN + "=?",selection,null,null,null);
        if (c.getCount() > 0){
            do {
                entries.add( new LogbookEntry(c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getInt(4),
                        c.getString(5)));
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return entries;
    }
}
