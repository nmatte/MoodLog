package com.nmatte.mood.reminders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.util.DatabaseHelper;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ReminderTableHelper {

    public static void addReminder(Context context, Reminder reminder){
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ReminderContract.REMINDER_MESSAGE_COLUMN, reminder.getMessage());
        values.put(ReminderContract.REMINDER_MILLIS_COLUMN,reminder.getTime().getMillis());
        values.put(ReminderContract.REMINDER_TIME_OF_DAY_COLUMN, reminder.timeOfDay());

        try{
            db.insertWithOnConflict(ReminderContract.REMINDER_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }

        db.close();
    }


    public static ArrayList<Reminder> getAll(Context context){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        String [] columns = new String[] {
                ReminderContract.REMINDER_TIME_OF_DAY_COLUMN,
                ReminderContract.REMINDER_MILLIS_COLUMN,
                ReminderContract.REMINDER_MESSAGE_COLUMN
        };

        Cursor c = db.query(ReminderContract.REMINDER_TABLE, columns, null, null, null, null,
                ReminderContract.REMINDER_TIME_OF_DAY_COLUMN);
        c.moveToFirst();

        ArrayList<Reminder> reminders = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                reminders.add(new Reminder(new DateTime(c.getLong(1)),c.getString(2)));
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return reminders;
    }


    public static void deleteReminder(Context context,Reminder reminder) {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        String whereClause = ReminderContract.REMINDER_TIME_OF_DAY_COLUMN + "=?";

        try{
            db.delete(ReminderContract.REMINDER_TABLE,whereClause,new String[]{String.valueOf(reminder.timeOfDay())});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }


}
