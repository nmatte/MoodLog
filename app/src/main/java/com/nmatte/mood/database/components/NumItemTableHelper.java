package com.nmatte.mood.database.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.database.ChartEntryContract;
import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

public class NumItemTableHelper {
    private static final int TRUE = 1,FALSE = 0;


    public static NumComponent save(Context context, NumComponent item){
        if (item == null)
            return item;
        if (item.getId() == null && item.getName() == null)
            return item;

        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            if (item.getId() != null)
                values.put(LogbookItemContract.Num.ITEM_ID_COLUMN, item.getId());
            if (item.getName() != null)
                values.put(LogbookItemContract.Num.ITEM_NAME_COLUMN,item.getName());

            values.put(LogbookItemContract.Num.ITEM_MAX_COLUMN,item.getMaxNum());
            values.put(LogbookItemContract.Num.ITEM_DEFAULT_COLUMN,item.getDefaultNum());
            values.put(LogbookItemContract.Num.ITEM_VISIBLE_COLUMN, TRUE);
            db.insertWithOnConflict(
                    LogbookItemContract.Num.ITEM_TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            e.printStackTrace();
        }

        item = getItemWithName(db,item.getName());
        addItemColumn(db, item);
        db.close();
        return item;
    }

    private static void addItemColumn(SQLiteDatabase db, NumComponent item) {
        if (item.getId() == null){
            return;
        }
        String query = "SELECT * FROM "+ ChartEntryContract.ENTRY_TABLE_NAME +" LIMIT 0,1";
        Cursor c = db.rawQuery(query, null);

        // column with this name wasn't found so you can safely add a new column.
        if (c.getColumnIndex(item.columnLabel()) == -1){
            String addColumnQuery = "ALTER TABLE " + ChartEntryContract.ENTRY_TABLE_NAME +
                    " ADD COLUMN " + item.columnLabel() + " " + LogbookItemContract.Bool.LOG_VALUE_TYPE;
            db.execSQL(addColumnQuery);
            Log.i("NumItems", "Adding column " + item.columnLabel());
        } else {
            Log.i("NumItems", "skipped adding column " +item.columnLabel());
        }
        c.close();
    }

    public static NumComponent getItemWithName(SQLiteDatabase db, String itemName){
        if (itemName == null)
            return null;

        String [] columns = new String [] {
                LogbookItemContract.Num.ITEM_ID_COLUMN,
                LogbookItemContract.Num.ITEM_NAME_COLUMN,
                LogbookItemContract.Num.ITEM_MAX_COLUMN,
                LogbookItemContract.Num.ITEM_DEFAULT_COLUMN
        };
        String selection = LogbookItemContract.Num.ITEM_NAME_COLUMN + " = ?";
        String [] args = new String[] {
            itemName
        };


        Cursor c = db.query(
                LogbookItemContract.Num.ITEM_TABLE,
                columns,
                selection,
                args,
                null, null,
                LogbookItemContract.Num.ITEM_ID_COLUMN);


        NumComponent item = null;
        if(c.getCount() > 0) {
            c.moveToFirst();
            item = new NumComponent(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
        }

        c.close();
        return item;
    }

    public static NumComponent getItemWithName(Context context, String itemName){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        NumComponent item = getItemWithName(db,itemName);
        db.close();
        return item;
    }

    public static void delete(Context context, NumComponent item){
//        DatabaseHelper DBHelper = new DatabaseHelper(context);
//        SQLiteDatabase db = DBHelper.getWritableDatabase();
//        String whereClause = LogbookItemContract.Num.ITEM_NAME_COLUMN + "=?";
//
//        try{
//            db.delete(LogbookItemContract.Num.ITEM_TABLE, whereClause, new String[]{name});
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        db.close();
    }

    public static ArrayList<NumComponent> getAll(Context context){
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

        ArrayList<NumComponent> numItems = new ArrayList<>();
        if (c.getCount() > 0){
            do {
                NumComponent item = new NumComponent(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
                numItems.add(item);
            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return numItems;
    }

    public static ArrayList<NumComponent> getAllVisible(SQLiteDatabase db){
        String [] columns = new String[] {
                LogbookItemContract.Num.ITEM_ID_COLUMN,
                LogbookItemContract.Num.ITEM_NAME_COLUMN,
                LogbookItemContract.Num.ITEM_MAX_COLUMN,
                LogbookItemContract.Num.ITEM_DEFAULT_COLUMN
        };
        String selection = LogbookItemContract.Num.ITEM_VISIBLE_COLUMN + " = ?";

        Cursor c = db.query(
                LogbookItemContract.Num.ITEM_TABLE,
                columns,
                selection,
                new String[] {
                        String.valueOf(TRUE)
                },
                null,
                null,
                LogbookItemContract.Num.ITEM_ID_COLUMN);
        c.moveToFirst();

        ArrayList<NumComponent> numItems = new ArrayList<>();
        if (c.getCount() > 0){
            do {
                NumComponent item = new NumComponent(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
                numItems.add(item);
            } while(c.moveToNext());
        }
        c.close();
        return numItems;
    }


}