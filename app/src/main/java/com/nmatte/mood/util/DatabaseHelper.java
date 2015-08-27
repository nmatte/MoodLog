package com.nmatte.mood.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmatte.mood.logbookentries.ChartEntryContract;
import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.notifications.MedNotificationContract;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "logbook.db";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            makeChartEntryTable(db);
            makeBoolChartEntryTable(db);
            makeNumChartEntryTable(db);

            makeBoolItemTable(db);
            makeNumItemTable(db);

            makeMedReminderTable(db);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void makeNumChartEntryTable(SQLiteDatabase db) {
        String numChartEntryQuery =
                "CREATE TABLE IF NOT EXISTS " + LogbookItemContract.Num.LOG_TABLE + " ("
                + LogbookItemContract.Num.LOG_DATE_COLUMN + " " + LogbookItemContract.Num.LOG_DATE_TYPE + ")";
        db.execSQL(numChartEntryQuery);
    }

    private void makeChartEntryTable(SQLiteDatabase db){
        String logbookEntryQuery =
                "CREATE TABLE IF NOT EXISTS "+ ChartEntryContract.ENTRY_TABLE_NAME+ " ("
                + ChartEntryContract.ENTRY_DATE_COLUMN + " " + ChartEntryContract.ENTRY_DATE_TYPE + ", "
                + ChartEntryContract.ENTRY_MOOD_COLUMN + " " + ChartEntryContract.ENTRY_MOOD_TYPE + ", "
                + ChartEntryContract.ENTRY_NUMITEM_COLUMN + " " + ChartEntryContract.ENTRY_NUMITEM_TYPE + ", "
                + ChartEntryContract.ENTRY_BOOLITEM_COLUMN + " " + ChartEntryContract.ENTRY_BOOLITEM_TYPE + ")";

        db.execSQL(logbookEntryQuery);
    }

    private void makeBoolChartEntryTable(SQLiteDatabase db){
        String boolChartEntryQuery =
                "CREATE TABLE IF NOT EXISTS " + LogbookItemContract.Bool.LOG_TABLE + " ("
                + LogbookItemContract.Bool.LOG_DATE_COLUMN + " " + LogbookItemContract.Bool.LOG_DATE_TYPE + ")";
        db.execSQL(boolChartEntryQuery);
    }

    private void makeNumItemTable(SQLiteDatabase db){
        String numItemTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.Num.ITEM_TABLE + " ("
                + LogbookItemContract.Num.ITEM_ID_COLUMN + " " + LogbookItemContract.Num.ITEM_ID_TYPE + ", "
                + LogbookItemContract.Num.ITEM_NAME_COLUMN + " " + LogbookItemContract.Num.ITEM_NAME_TYPE + ", "
                + LogbookItemContract.Num.ITEM_VISIBLE_COLUMN + " " + LogbookItemContract.Num.ITEM_VISIBLE_TYPE + ", "
                + LogbookItemContract.Num.ITEM_MAX_COLUMN + " " + LogbookItemContract.Num.ITEM_MAX_TYPE + ", "
                + LogbookItemContract.Num.ITEM_DEFAULT_COLUMN + " " + LogbookItemContract.Num.ITEM_DEFAULT_TYPE + ")";

        db.execSQL(numItemTableQuery);
    }

    private void makeBoolItemTable(SQLiteDatabase db){
        String medTableQuery =
                "CREATE TABLE IF NOT EXISTS "+ LogbookItemContract.Bool.ITEM_TABLE + " ("
                + LogbookItemContract.Bool.ITEM_ID_COLUMN + " " + LogbookItemContract.Bool.ITEM_ID_TYPE + ", "
                + LogbookItemContract.Bool.ITEM_NAME_COLUMN + " " + LogbookItemContract.Bool.ITEM_NAME_TYPE + ", "
                + LogbookItemContract.Bool.ITEM_VISIBLE_COLUMN + " " + LogbookItemContract.Bool.ITEM_VISIBLE_TYPE + ")";
        db.execSQL(medTableQuery);
    }

    private void makeMedReminderTable(SQLiteDatabase db){
        String medReminderTableQuery =
                "CREATE TABLE IF NOT EXISTS " + MedNotificationContract.MED_NOTIFICATION_TABLE + " ("
                + MedNotificationContract.MED_REMINDER_TIME_COLUMN + " " + MedNotificationContract.MED_REMINDER_TIME_TYPE + ", "
                + MedNotificationContract.MED_REMINDER_INTENT_COLUMN + " " + MedNotificationContract.MED_REMINDER_INTENT_TYPE + ", "
                + MedNotificationContract.MED_REMINDER_MEDICATIONS_COLUMN + " " + MedNotificationContract.MED_REMINDER_MEDICATIONS_TYPE + ")";

        db.execSQL(medReminderTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
