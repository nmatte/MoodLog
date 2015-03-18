package com.nmatte.mood.moodlog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "logbook.db";
    private static final int DATABASE_VERSION = 1;


    public static final String MEDICATION_TABLE = "medications";
    public static final String MEDICATION_NAME_COLUMN = "medicationName";
    public static final String MEDICATION_ID_COLUMN = "_ID";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
        String medTableQuery = "CREATE TABLE IF NOT EXISTS "+ MEDICATION_TABLE +
                " (" + MEDICATION_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MEDICATION_NAME_COLUMN + " TEXT UNIQUE" + ")";

        db.execSQL(medTableQuery);
        } catch(Exception e){
            Log.e("database failure", "error creating database");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
