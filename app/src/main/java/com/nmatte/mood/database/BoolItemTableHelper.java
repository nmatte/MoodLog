package com.nmatte.mood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.models.BoolItem;

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
    public static BoolItem save(Context context, BoolItem item){
        if (item == null)
            return item;
        if (item.getID() == null && item.getName() == null)
            return item;

        SQLiteDatabase db =  new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            if (item.getID() != null)
                values.put(LogbookItemContract.Bool.ITEM_ID_COLUMN, item.getID());
            if (item.getName() != null)
                values.put(LogbookItemContract.Bool.ITEM_NAME_COLUMN,item.getName());

            values.put(LogbookItemContract.Bool.ITEM_VISIBLE_COLUMN, TRUE);
            db.insertWithOnConflict(
                    LogbookItemContract.Bool.ITEM_TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            e.printStackTrace();
        }

        item = getItemWithName(db, item.getName());
        addItemColumn(db,item);
        db.close();
        return item;
    }

    public static BoolItem getItemWithName(SQLiteDatabase db, String itemName){
        if (itemName == null)
            return null;

        String [] columns = new String [] {
                LogbookItemContract.Bool.ITEM_ID_COLUMN,
                LogbookItemContract.Bool.ITEM_NAME_COLUMN
        };
        String selection = LogbookItemContract.Bool.ITEM_NAME_COLUMN + " = ?";
        String [] args = new String []{
                itemName
        };

        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                selection,
                args,
                null,null,
                LogbookItemContract.Bool.ITEM_ID_COLUMN);

        BoolItem item = null;
        if(c.getCount() > 0){
            c.moveToFirst();
            item = new BoolItem(c.getLong(0),c.getString(1));
        }
        c.close();
        return item;
    }

    public static BoolItem getItemWithName(Context context, String itemName){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        BoolItem item = getItemWithName(db,itemName);
        db.close();
        return item;
    }

    private static void addItemColumn(SQLiteDatabase db, BoolItem item){
        if (item.getID() == null){
            return;
        }
        String query1 = "SELECT * FROM "+ ChartEntryContract.ENTRY_TABLE_NAME +" LIMIT 0,1";
        Cursor c = db.rawQuery(query1, null);

        // column with this name wasn't found so you can safely add a new column.
        if (c.getColumnIndex(item.toString()) == -1){
            String addColumnQuery = "ALTER TABLE " + ChartEntryContract.ENTRY_TABLE_NAME +
                    " ADD COLUMN " + item.toString() + " " + LogbookItemContract.Bool.LOG_VALUE_TYPE ;
            db.execSQL(addColumnQuery);
            Log.i("BoolItemTableHelper", "Added column" + item.toString());
        } else {
            Log.i("BoolItemTableHelper", "Skipped adding column" + item.toString());
        }
        c.close();

    }

    public static void delete(Context context, BoolItem item){
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


    public static ArrayList<BoolItem> getAllVisible(Context context){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        ArrayList<BoolItem> result = getAllVisible(db);
        db.close();
        return result;
    }

    public static ArrayList<BoolItem> getAllVisible(SQLiteDatabase db){
        String [] columns = new String[] {
                LogbookItemContract.Bool.ITEM_ID_COLUMN,
                LogbookItemContract.Bool.ITEM_NAME_COLUMN
        };
        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                LogbookItemContract.Bool.ITEM_VISIBLE_COLUMN + " = ?",
                new String[]{
                        String.valueOf(TRUE)
                },
                null,
                null,
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
        return boolItems;
    }
}

