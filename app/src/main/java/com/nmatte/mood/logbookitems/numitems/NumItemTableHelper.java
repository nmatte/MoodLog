package com.nmatte.mood.logbookitems.numitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmatte.mood.logbookitems.LogbookItemContract;
import com.nmatte.mood.util.DatabaseHelper;

import java.util.ArrayList;

public class NumItemTableHelper {
    private static final int TRUE = 1,FALSE = 0;


    public static NumItem insertOrUpdate(Context context, NumItem item){
        if (item == null)
            return item;
        if (item.getID() == null && item.getName() == null)
            return item;

        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            if (item.getID() != null)
                values.put(LogbookItemContract.Num.ITEM_ID_COLUMN, item.getID());
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

        item = getFullItem(db,item);
        addItemColumn(db, item);
        db.close();
        return item;
    }

    private static void addItemColumn(SQLiteDatabase db, NumItem item) {
        if (item.getID() == null){
            return;
        }
        String query = "SELECT * FROM "+LogbookItemContract.Num.LOG_TABLE +" LIMIT 0,1";
        Cursor c = db.rawQuery(query, null);

        // column with this name wasn't found so you can safely add a new column.
        if (c.getColumnIndex(item.getColumnName()) == -1){
            String addColumnQuery = "ALTER TABLE " + LogbookItemContract.Num.LOG_TABLE +
                    " ADD COLUMN " + item.getColumnName() + " " + LogbookItemContract.Bool.LOG_VALUE_TYPE ;

            db.execSQL(addColumnQuery);
        }
        c.close();
    }

    private static NumItem getFullItem(SQLiteDatabase db, NumItem item) {
        // without name or ID there is no way to identify item
        if (item == null)
            return item;
        if (item.getID() == null && item.getName() == null)
            return null;

        String [] columns = new String [] {
                LogbookItemContract.Num.ITEM_ID_COLUMN,
                LogbookItemContract.Num.ITEM_NAME_COLUMN,
                LogbookItemContract.Num.ITEM_MAX_COLUMN,
                LogbookItemContract.Num.ITEM_DEFAULT_COLUMN
        };
        String selection = "? = ?";
        String [] args = new String [2];
        if (item.getID() != null){
            args[0] = LogbookItemContract.Num.ITEM_ID_COLUMN;
            args[1] = item.getID().toString();
        } else {
            args[0] = LogbookItemContract.Num.ITEM_NAME_COLUMN;
            args[1] = item.getName();
        }

        Cursor c = db.query(
                LogbookItemContract.Num.ITEM_TABLE,
                columns,
                selection,
                args,
                null,null,
                LogbookItemContract.Num.ITEM_ID_COLUMN);


        if(c.getCount() > 0) {
            c.moveToFirst();
            item = new NumItem(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
        }

        c.close();
        return item;
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
