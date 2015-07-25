package com.nmatte.mood.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmatte.mood.logbookentries.LogBookContract;
import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.notifications.MedNotificationContract;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "logbook.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String medTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.BOOL_ITEM_TABLE + " ("
                + LogbookItemContract.BOOL_ID_COLUMN + " " + LogbookItemContract.BOOL_ID_TYPE + ", "
                + LogbookItemContract.BOOL_ITEM_NAME_COLUMN + " " + LogbookItemContract.BOOL_ITEM_TYPE + ")";

            String numItemTableQuery =
                    "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.NUM_ITEM_TABLE + " ("
                            + LogbookItemContract.NUM_ITEM_ID_COLUMN + " " + LogbookItemContract.NUM_ITEM_ID_TYPE + ", "
                            + LogbookItemContract.NUM_ITEM_NAME_COLUMN + " " + LogbookItemContract.NUM_ITEM_NAME_TYPE + ", "
                            + LogbookItemContract.NUM_ITEM_MAX_COLUMN + " " + LogbookItemContract.NUM_ITEM_MAX_TYPE + ", "
                            + LogbookItemContract.NUM_ITEM_DEFAULT_COLUMN + " " + LogbookItemContract.NUM_ITEM_DEFAULT_TYPE + ")";

            String logTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogBookContract.LOGBOOKENTRY_TABLE + " ("
                + LogBookContract.LOGBOOKENTRY_DATE_COLUMN + " " + LogBookContract.LOGBOOKENTRY_DATE_TYPE + ", "
                + LogBookContract.LOGBOOKENTRY_MOOD_COLUMN + " " + LogBookContract.LOGBOOKENTRY_MOOD_TYPE + ", "
                + LogBookContract.LOGBOOKENTRY_ANXIETY_COLUMN + " " + LogBookContract.LOGBOOKENTRY_ANXIETY_TYPE + ", "
                + LogBookContract.LOGBOOKENTRY_IRRITABILITY_COLUMN + " " + LogBookContract.LOGBOOKENTRY_IRRITABILITY_TYPE + ", "
                + LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_COLUMN + " " + LogBookContract.LOGBOOKENTRY_HOURS_SLEPT_TYPE + ", "
                + LogBookContract.LOGBOOKENTRY_MEDICATION_COLUMN + " " + LogBookContract.LOGBOOKENTRY_MEDICATION_TYPE + ")";

            String medReminderTableQuery =
                    "CREATE TABLE IF NOT EXISTS " + MedNotificationContract.MED_NOTIFICATION_TABLE + " ("
                            + MedNotificationContract.MED_REMINDER_TIME_COLUMN + " " + MedNotificationContract.MED_REMINDER_TIME_TYPE + ", "
                            + MedNotificationContract.MED_REMINDER_INTENT_COLUMN + " " + MedNotificationContract.MED_REMINDER_INTENT_TYPE + ", "
                            + MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN + " " + MedNotificationContract.MED_REMINDER_MEDICATIONS_TYPE + ")";

            db.execSQL(medReminderTableQuery);
            db.execSQL(medTableQuery);
            db.execSQL(logTableQuery);
            db.execSQL(numItemTableQuery);

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ((oldVersion == 1) && (newVersion == 2)){
            String medReminderTableQuery =
                    "CREATE TABLE IF NOT EXISTS " + MedNotificationContract.MED_NOTIFICATION_TABLE + " ("
                            + MedNotificationContract.MED_REMINDER_TIME_COLUMN + " " + MedNotificationContract.MED_REMINDER_TIME_TYPE + ", "
                            + MedNotificationContract.MED_REMINDER_INTENT_COLUMN + " " + MedNotificationContract.MED_REMINDER_INTENT_TYPE + ", "
                            + MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN + " " + MedNotificationContract.MED_REMINDER_MEDICATIONS_TYPE + ")";
            db.execSQL(medReminderTableQuery);
        }

    }
}
