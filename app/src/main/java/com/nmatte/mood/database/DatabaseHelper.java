package com.nmatte.mood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nmatte.mood.database.components.ComponentContract;
import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.database.modules.ModuleContract;
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
            makeModuleTable(db);

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
        String query =
                "CREATE TABLE IF NOT EXISTS "+ ComponentContract.Num.ITEM_TABLE + " ("
                + ComponentContract.ID_COLUMN + " " + ComponentContract.ID_TYPE + ", "
                + ComponentContract.NAME_COLUMN + " " + ComponentContract.NAME_TYPE + ", "
                + ComponentContract.COLOR_COLUMN + " " + ComponentContract.COLOR_TYPE + ", "
                + ComponentContract.Num.ITEM_MAX_COLUMN + " " + ComponentContract.Num.ITEM_MAX_TYPE + ", "
                + ComponentContract.Num.ITEM_DEFAULT_COLUMN + " " + ComponentContract.Num.ITEM_DEFAULT_TYPE + ", "
                + ComponentContract.PARENT_MODULE_COLUMN + " " + ComponentContract.PARENT_MODULE_TYPE + ", "
                + ComponentContract.FOREIGN_KEY_CONSTRAINT + ")";

        db.execSQL(query);
    }

    private void makeBoolItemTable(SQLiteDatabase db){
        String query =
                "CREATE TABLE IF NOT EXISTS "+ ComponentContract.Bool.ITEM_TABLE + " ("
                + ComponentContract.ID_COLUMN + " " + ComponentContract.ID_TYPE + ", "
                + ComponentContract.NAME_COLUMN + " " + ComponentContract.NAME_TYPE + ", "
                + ComponentContract.COLOR_COLUMN + " " + ComponentContract.COLOR_TYPE + ", "
                + ComponentContract.PARENT_MODULE_COLUMN + " " + ComponentContract.PARENT_MODULE_TYPE + ", "
                + ComponentContract.FOREIGN_KEY_CONSTRAINT + ")";
        db.execSQL(query);
    }

    private void makeReminderTable(SQLiteDatabase db){
        String medReminderTableQuery =
                "CREATE TABLE IF NOT EXISTS " + ReminderContract.REMINDER_TABLE + " ("
                + ReminderContract.REMINDER_TIME_OF_DAY_COLUMN + " " + ReminderContract.REMINDER_TIME_TYPE + ", "
                + ReminderContract.REMINDER_MILLIS_COLUMN + " " + ReminderContract.REMINDER_MILLIS_TYPE + ", "
                + ReminderContract.REMINDER_MESSAGE_COLUMN + " " + ReminderContract.REMINDER_MESSAGE_TYPE + ")";

        db.execSQL(medReminderTableQuery);
    }

    private void makeModuleTable(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS " + ModuleContract.MODULE_TABLE_NAME + " ("
                + ModuleContract.MODULE_ID_COLUMN + " " + ModuleContract.MODULE_ID_TYPE + ", "
                + ModuleContract.MODULE_NAME_COLUMN + " " + ModuleContract.MODULE_NAME_TYPE + ", "
                + ModuleContract.MODULE_VISIBLE_COLUMN + " " + ModuleContract.MODULE_VISIBLE_TYPE + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
