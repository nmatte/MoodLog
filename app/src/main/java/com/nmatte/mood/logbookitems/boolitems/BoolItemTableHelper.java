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
    public static void addBoolItem(Context context, BoolItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LogbookItemContract.BOOL_ITEM_NAME_COLUMN, item.getName());

        try{
            db.insert(LogbookItemContract.BOOL_ITEM_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding medication");
        }
        db.close();
    }

    public static void updateName(Context context, BoolItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LogbookItemContract.BOOL_ID_COLUMN, item.getID());
        values.put(LogbookItemContract.BOOL_ITEM_NAME_COLUMN, item.getName());

        try{
            db.insertWithOnConflict(LogbookItemContract.BOOL_ITEM_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db","",e);
        }
        db.close();
    }

    public static void deleteBoolItem(Context context, BoolItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = LogbookItemContract.BOOL_ID_COLUMN + "=?";



        try{
            db.delete(LogbookItemContract.BOOL_ITEM_TABLE, whereClause, new String[]{String.valueOf(item.getID())});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }
        db.close();
    }

    public static ArrayList<BoolItem> getAll(Context context){
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
                BoolItem m = new BoolItem(c.getLong(0),c.getString(1));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return boolItems;
    }

    public static ArrayList<String>  mapIDsToNames(ArrayList<Long> ids, Context context) {
        ArrayList<String> result = new ArrayList<>();

        for (BoolItem m : getAll(context)){
            if (ids.contains(m.getID())){
                result.add(m.getName());
            }
        }
        return result;
    }

}

