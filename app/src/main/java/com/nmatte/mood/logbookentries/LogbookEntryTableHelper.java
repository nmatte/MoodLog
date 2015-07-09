package com.nmatte.mood.logbookentries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.moodlog.DatabaseHelper;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.valueOf;

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
        return getEntry(context, getIntFromDate(Calendar.getInstance()));
    }

    public static int getIntFromDate( Calendar c){
        DateFormat df = new SimpleDateFormat("yyyyDDD");
        String ds = df.format(c.getTime());
        return valueOf(ds);

    }



    public ArrayList<LogbookEntry> getEntryGroup (Calendar startDate, Calendar endDate){
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
        String [] selection = new String[] {String.valueOf(getIntFromDate(startDate)),String.valueOf(getIntFromDate(endDate))};

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

    public ArrayList<LogbookEntry> getLast28Days (){
        ArrayList<LogbookEntry> result;
        Calendar endDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_YEAR, -28);
        result = this.getEntryGroup(startDate,endDate);
        return result;
    }

    public static void addOrUpdateEntry(Context context, LogbookEntry entry) {
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LogBookContract.LOGBOOKENTRY_DATE_COLUMN,entry.getDateAsInt());
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


