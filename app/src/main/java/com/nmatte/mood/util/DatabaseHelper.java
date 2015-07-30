package com.nmatte.mood.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmatte.mood.logbookentries.ChartEntryContract;
import com.nmatte.mood.logbookentries.LogBookContract;
import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.notifications.MedNotificationContract;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "logbook.db";
    private static final int DATABASE_VERSION = 4;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String medTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.BOOL_ITEM_TABLE + " ("
                + LogbookItemContract.BOOL_ID_COLUMN + " " + LogbookItemContract.BOOL_ID_TYPE + ", "
                + LogbookItemContract.BOOL_ITEM_NAME_COLUMN + " " + LogbookItemContract.BOOL_ITEM_TYPE + ")";



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
            makeNumItemTable(db);
            makeLogbookEntryTable(db);

        } catch(Exception e){
            e.printStackTrace();
        }


    }

    private void makeNumItemTable(SQLiteDatabase db){
        String numItemTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.NUM_ITEM_TABLE + " ("
                        + LogbookItemContract.NUM_ITEM_ID_COLUMN + " " + LogbookItemContract.NUM_ITEM_ID_TYPE + ", "
                        + LogbookItemContract.NUM_ITEM_NAME_COLUMN + " " + LogbookItemContract.NUM_ITEM_NAME_TYPE + ", "
                        + LogbookItemContract.NUM_ITEM_MAX_COLUMN + " " + LogbookItemContract.NUM_ITEM_MAX_TYPE + ", "
                        + LogbookItemContract.NUM_ITEM_DEFAULT_COLUMN + " " + LogbookItemContract.NUM_ITEM_DEFAULT_TYPE + ")";

        db.execSQL(numItemTableQuery);
    }

    private void makeLogbookEntryTable(SQLiteDatabase db){
        String logbookEntryQuery =
                "CREATE TABLE IF NOT EXISTS "+ ChartEntryContract.ENTRY_TABLE_NAME+ " ("
                        + ChartEntryContract.ENTRY_DATE_COLUMN + " " + ChartEntryContract.ENTRY_DATE_TYPE + ", "
                        + ChartEntryContract.ENTRY_MOOD_COLUMN + " " + ChartEntryContract.ENTRY_MOOD_TYPE + ", "
                        + ChartEntryContract.ENTRY_NUMITEM_COLUMN + " " + ChartEntryContract.ENTRY_NUMITEM_TYPE + ", "
                        + ChartEntryContract.ENTRY_BOOLITEM_COLUMN + " " + ChartEntryContract.ENTRY_BOOLITEM_TYPE + ")";

        db.execSQL(logbookEntryQuery);
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

        if ((oldVersion == 2) && (newVersion == 3)){
            makeNumItemTable(db);
        }

        if ( (newVersion == 4)){
            makeNumItemTable(db);
            makeLogbookEntryTable(db);
        }


    }
}
