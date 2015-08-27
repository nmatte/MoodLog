package com.nmatte.mood.logbookitems.numitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;


// TODO give same functionality as BoolItems
public class NumItemTableHelper {
    public static void addNumItem(Context context, NumItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LogbookItemContract.Num.ITEM_NAME_COLUMN,item.getName());
        values.put(LogbookItemContract.Num.ITEM_MAX_COLUMN,item.getMaxNum());
        values.put(LogbookItemContract.Num.ITEM_DEFAULT_COLUMN,item.getDefaultNum());

        try{
            db.insert(LogbookItemContract.Num.ITEM_TABLE,null,values);
        } catch (Exception e){
            Log.e("SQL exception", "error adding NumItem");
        }
        db.close();
    }

    public static void updateItem (Context context, NumItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LogbookItemContract.Num.ITEM_NAME_COLUMN,item.getName());
        values.put(LogbookItemContract.Num.ITEM_MAX_COLUMN,item.getMaxNum());
        values.put(LogbookItemContract.Num.ITEM_DEFAULT_COLUMN,item.getDefaultNum());
        values.put(LogbookItemContract.Num.ITEM_ID_COLUMN,item.getID());

        try{
            db.insertWithOnConflict(LogbookItemContract.Num.ITEM_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            Log.e("db","",e);
        }
        db.close();
    }

    public static void deleteItemWithName(Context context, String name){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = LogbookItemContract.Num.ITEM_NAME_COLUMN + "=?";

        try{
            db.delete(LogbookItemContract.Num.ITEM_TABLE, whereClause, new String[]{name});
        } catch (Exception e){
            e.printStackTrace();
        }

        db.close();
    }

    public static ArrayList<NumItem> getAll(Context context){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String [] columns = new String[] {
                LogbookItemContract.Num.ITEM_ID_COLUMN,
                LogbookItemContract.Num.ITEM_NAME_COLUMN,
                LogbookItemContract.Num.ITEM_MAX_COLUMN,
                LogbookItemContract.Num.ITEM_DEFAULT_COLUMN
        };

        Cursor c = db.query(
                LogbookItemContract.Num.ITEM_TABLE,
                columns,
                null,null,null,null,
                LogbookItemContract.Num.ITEM_ID_COLUMN);
        c.moveToFirst();

        ArrayList<NumItem> numItems = new ArrayList<>();
        if (c.getCount() > 0){
            do {
                NumItem item = new NumItem(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
                numItems.add(item);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return numItems;
    }

}
