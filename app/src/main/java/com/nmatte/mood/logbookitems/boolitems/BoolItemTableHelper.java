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
    private static final int TRUE = 1,FALSE = 0;

    /*
        -- Item names and IDs should be unique.
        -- If the BoolItem has the same ID as one in the database, simply update the name and set to
        visible.
        -- If the BoolItem has the same name as one in the database, update neither name nor ID, but set
        to visible (in case user previously removed from chart, but re-made the item)
        If the BoolItem was not already in the database, add a column to the BoolChartEntry table.
         */
    public static BoolItem insertOrUpdate(Context context, BoolItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        try {
            if (item.getID() == null && item.getName() == null){
                return item;
            } else {
                values.put(LogbookItemContract.Bool.ITEM_NAME_COLUMN,item.getName());
                values.put(LogbookItemContract.Bool.ITEM_VISIBLE_COLUMN, TRUE);
                if (item.getID() != null){
                    values.put(LogbookItemContract.Bool.ITEM_ID_COLUMN, item.getID());
                }
            }

            db.insertWithOnConflict(
                    LogbookItemContract.Bool.ITEM_TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);

        } catch (Exception e){
            e.printStackTrace();
        }

        String [] columns = new String [] {
                LogbookItemContract.Bool.ITEM_ID_COLUMN,
                LogbookItemContract.Bool.ITEM_NAME_COLUMN
        };
        String selection;
        String [] args = new String [1];

        if (item.getID() == null){
            selection = LogbookItemContract.Bool.ITEM_NAME_COLUMN;
            args[0] = item.getName();
        } else {
            selection = LogbookItemContract.Bool.ITEM_ID_COLUMN;
            args[0] = item.getID().toString();
        }
        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                selection,
                args,
                null,null,
                LogbookItemContract.Bool.ITEM_ID_COLUMN);

        if(c.getCount() > 0){
            c.moveToFirst();
            item = new BoolItem(c.getLong(0),c.getString(1));
        }
        c.close();
        addItemColumn(db,item);
        db.close();
        return item;
    }

    // this is a helper method to add a column if the column doesn't already exist.
    private static void addItemColumn(SQLiteDatabase db, BoolItem item){

        String query1 = "SELECT * FROM ? LIMIT 0,1";
        Cursor c = db.rawQuery(query1, new String[]{LogbookItemContract.Bool.LOG_TABLE});

        if (item.getID() == null){
            return;
        }
        // column with this name wasn't found so you can safely add a new column.
        if (c.getColumnIndex(item.getID().toString()) == -1){
            String addColumnQuery = "ALTER TABLE " + LogbookItemContract.Bool.LOG_TABLE +
                    " ADD COLUMN " + item.getID().toString() + " " + LogbookItemContract.Bool.LOG_VALUE_TYPE ;

            db.execSQL(addColumnQuery);
        }
    }



    public static void setVisibility(Context context, BoolItem item, boolean isVisible){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();


        try {
            if (item.getID() == null)
                return;

            values.put(LogbookItemContract.Bool.ITEM_ID_COLUMN, item.getID());
            values.put(LogbookItemContract.Bool.ITEM_VISIBLE_COLUMN, isVisible ? TRUE : FALSE);
            db.update(
                    LogbookItemContract.Bool.ITEM_TABLE,
                    values,
                    "WHERE ? = ?",
                    new String[]{
                            LogbookItemContract.Bool.ITEM_ID_COLUMN,
                            item.getID().toString()
                    });
        } catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void deleteBoolItem(Context context, BoolItem item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = LogbookItemContract.Bool.ITEM_ID_COLUMN + "=?";

        try{
            db.delete(LogbookItemContract.Bool.ITEM_TABLE,
                    whereClause,
                    new String[]{String.valueOf(item.getID())});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }
        db.close();
        // TODO delete column as well
    }

    public static ArrayList<BoolItem> getAll(Context context){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                LogbookItemContract.Bool.ITEM_ID_COLUMN,
                LogbookItemContract.Bool.ITEM_NAME_COLUMN
        };

        Cursor c = db.query(LogbookItemContract.Bool.ITEM_TABLE, columns, null, null, null, null,
                LogbookItemContract.Bool.ITEM_ID_COLUMN);
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


    //TODO: public static ArrayList<BoolItem> getAllVisible(Context context)


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

