package com.nmatte.mood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmatte.mood.database.components.LogbookItemContract;
import com.nmatte.mood.reminders.ReminderContract;


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

            makeBoolItemTable(db);
            makeNumItemTable(db);

            makeReminderTable(db);

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    private void makeChartEntryTable(SQLiteDatabase db){
        String logbookEntryQuery =
                "CREATE TABLE IF NOT EXISTS "+ ChartEntryContract.ENTRY_TABLE_NAME+ " ("
                + ChartEntryContract.ENTRY_DATE_COLUMN + " " + ChartEntryContract.ENTRY_DATE_TYPE + ", "
                + ChartEntryContract.ENTRY_NOTE_COLUMN + " " + ChartEntryContract.ENTRY_NOTE_TYPE +  ")";

        db.execSQL(logbookEntryQuery);
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

    private void makeReminderTable(SQLiteDatabase db){
        String medReminderTableQuery =
                "CREATE TABLE IF NOT EXISTS " + ReminderContract.REMINDER_TABLE + " ("
                + ReminderContract.REMINDER_TIME_OF_DAY_COLUMN + " " + ReminderContract.REMINDER_TIME_TYPE + ", "
                + ReminderContract.REMINDER_MILLIS_COLUMN + " " + ReminderContract.REMINDER_MILLIS_TYPE + ", "
                + ReminderContract.REMINDER_MESSAGE_COLUMN + " " + ReminderContract.REMINDER_MESSAGE_TYPE + ")";

        db.execSQL(medReminderTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
