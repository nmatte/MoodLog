package com.nmatte.mood.moodlog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MedTableHelper {
    DatabaseHelper DBHelper;
    Context context;

    MedTableHelper(Context c) {
        context = c;
        DBHelper = new DatabaseHelper(c);
    }

    public void addMedication(String name){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DBHelper.MEDICATION_NAME_COLUMN, name);

        try{
            db.insert(DBHelper.MEDICATION_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }

        db.close();
    }

    public void deleteMedication(String name){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = DBHelper.MEDICATION_NAME_COLUMN + "=?";

        try{
            db.delete(DBHelper.MEDICATION_TABLE,whereClause,new String[]{name});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }

    private Cursor getMedCursor(){
        SQLiteDatabase db =  DBHelper.getReadableDatabase();
        String [] columns = new String [] {
                "rowid _id",
                DBHelper.MEDICATION_NAME_COLUMN
        };

        Cursor c = db.query(DBHelper.MEDICATION_TABLE,columns,null,null,null,null,null);
        c.moveToFirst();
        return c;
    }

    public ArrayList<String> getMedNames(){
        Cursor c = getMedCursor();
        ArrayList<String> medNames = new ArrayList<>();
        if(c.getCount() > 0) {
            do {
                medNames.add(c.getString(1));
            } while (c.moveToNext());
        }
        return medNames;
    }


}

