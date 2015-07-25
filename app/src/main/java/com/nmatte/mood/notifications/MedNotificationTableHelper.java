package com.nmatte.mood.notifications;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;

public class MedNotificationTableHelper {
    DatabaseHelper DBHelper;
    Context context;

    public MedNotificationTableHelper(Context context){
        this.context = context;
        this.DBHelper = new DatabaseHelper(context);
    }

    public void addNotification(MedNotification notification){
        MedNotification oldNotification = getNotification(notification.timeID);
        if (oldNotification != null){
            notification.intentID = oldNotification.intentID;
        }
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(MedNotificationContract.MED_REMINDER_TIME_COLUMN, notification.timeID);
        values.put(MedNotificationContract.MED_REMINDER_INTENT_COLUMN, notification.intentID);
        values.put(MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN,notification.medIDString());

        try{
            db.insert(MedNotificationContract.MED_NOTIFICATION_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }

        db.close();
    }

    public MedNotification getNotification(int hour, int minute){
        return getNotification(hour * 100 + minute);
    }

    public MedNotification getNotification(int timeID){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                MedNotificationContract.MED_REMINDER_TIME_COLUMN,
                MedNotificationContract.MED_REMINDER_INTENT_COLUMN,
                MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN
        };

        String [] query = new String[] {String.valueOf(timeID)};

        Cursor c = db.query(MedNotificationContract.MED_NOTIFICATION_TABLE, columns, MedNotificationContract.MED_REMINDER_TIME_COLUMN + "=?", query, null, null,
                null);
        c.moveToFirst();

        MedNotification notification = null;

        if(c.getCount() > 0){
            notification = new MedNotification(c.getInt(0),c.getLong(1), BoolItem.parseIDString(c.getString(2)));
        }
        c.close();
        db.close();
        return notification;
    }

    public ArrayList<MedNotification> getMedReminderList(){
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                MedNotificationContract.MED_REMINDER_TIME_COLUMN,
                MedNotificationContract.MED_REMINDER_INTENT_COLUMN,
                MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN
        };

        Cursor c = db.query(MedNotificationContract.MED_NOTIFICATION_TABLE, columns, null, null, null, null,
                MedNotificationContract.MED_REMINDER_TIME_COLUMN);
        c.moveToFirst();

        ArrayList<MedNotification> notifications = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                MedNotification m = new MedNotification(c.getInt(0),c.getLong(1), BoolItem.parseIDString(c.getString(2)));
                notifications.add(m);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return notifications;
    }


    public void deleteMedReminder(MedNotification n) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = MedNotificationContract.MED_REMINDER_TIME_COLUMN + "=?";

        try{
            db.delete(MedNotificationContract.MED_NOTIFICATION_TABLE,whereClause,new String[]{String.valueOf(n.timeID)});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }

    public void deleteMedReminder(int timeID){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = MedNotificationContract.MED_REMINDER_TIME_COLUMN + "=?";

        try{
            db.delete(MedNotificationContract.MED_NOTIFICATION_TABLE,whereClause,new String[]{String.valueOf(timeID)});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }
}
