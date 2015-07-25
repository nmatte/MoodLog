package com.nmatte.mood.logbookitems.boolitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;

public class BoolItemTableHelper {
    DatabaseHelper DBHelper;
    Context context;

    public BoolItemTableHelper(Context context) {
        this.context = context;
        this.DBHelper = new DatabaseHelper(context);
    }



    public static void addMedication(Context context, String name){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LogbookItemContract.BOOL_ITEM_NAME_COLUMN, name);

        try{
            db.insert(LogbookItemContract.BOOL_ITEM_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }

        db.close();
    }

    public static void deleteMedication(Context context, String name){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = LogbookItemContract.BOOL_ITEM_NAME_COLUMN + "=?";

        try{
            db.delete(LogbookItemContract.BOOL_ITEM_TABLE, whereClause, new String[]{name});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }

        db.close();
    }

    public static ArrayList<BoolItem> getMedicationList(Context context){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                LogbookItemContract.BOOL_ID_COLUMN,
                LogbookItemContract.BOOL_ITEM_NAME_COLUMN
        };

        Cursor c = db.query(LogbookItemContract.BOOL_ITEM_TABLE, columns, null, null, null, null,
                LogbookItemContract.BOOL_ID_COLUMN);
        c.moveToFirst();

        ArrayList<BoolItem> boolItems = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                BoolItem m = new BoolItem(c.getInt(0),c.getString(1));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return boolItems;

    }

    public static ArrayList<String>  mapIDsToNames(ArrayList<Long> ids, Context context) {
        ArrayList<String> result = new ArrayList<>();

        for (BoolItem m : getMedicationList(context)){
            if (ids.contains(m.getID())){
                result.add(m.getName());
            }
        }

        return result;
    }

}

