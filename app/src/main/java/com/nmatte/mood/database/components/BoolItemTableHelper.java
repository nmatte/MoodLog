package com.nmatte.mood.database.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class BoolItemTableHelper extends ComponentTableHelper{
    private static final int TRUE = 1,FALSE = 0;

    /*
        -- Item names and IDs should be unique.
        -- If the BoolComponent has the same ID as one in the database, simply update the name and set to
        visible.
        -- If the BoolComponent has the same name as one in the database, update neither name nor ID, but set
        to visible (in case user previously removed from chart, but re-made the item)
        If the BoolComponent was not already in the database, add a column to the BoolChartEntry table.
         */
    public BoolComponent save(Context context, BoolComponent item){
        if (item == null)
            return item;
        if (item.getId() == null && item.getName() == null)
            return item;

        SQLiteDatabase db =  new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            if (item.getId() != null)
                values.put(LogbookItemContract.Bool.ITEM_ID_COLUMN, item.getId());
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
        addColumn(db, item);
        db.close();
        return item;
    }

    public BoolComponent getItemWithName(SQLiteDatabase db, String itemName){
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
                null, null,
                LogbookItemContract.Bool.ITEM_ID_COLUMN);

        BoolComponent item = null;
        if(c.getCount() > 0){
            c.moveToFirst();
            item = new BoolComponent(c.getLong(0),c.getString(1));
        }
        c.close();
        return item;
    }

    public BoolComponent getItemWithName(Context context, String itemName){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        BoolComponent item = getItemWithName(db,itemName);
        db.close();
        return item;
    }

    public void delete(Context context, BoolComponent item){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String whereClause = LogbookItemContract.Bool.ITEM_ID_COLUMN + "=?";

        try{
            db.delete(LogbookItemContract.Bool.ITEM_TABLE,
                    whereClause,
                    new String[]{String.valueOf(item.getId())});
        } catch (Exception e) {
            Log.e("SQL exception", "error deleting medication");
        }
        db.close();
        // TODO delete column as well
    }

    public ArrayList<BoolComponent> getAll(Context context){
        DatabaseHelper DBHelper = new DatabaseHelper(context);
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String [] columns = new String[] {
                LogbookItemContract.Bool.ITEM_ID_COLUMN,
                LogbookItemContract.Bool.ITEM_NAME_COLUMN
        };

        Cursor c = db.query(LogbookItemContract.Bool.ITEM_TABLE, columns, null, null, null, null,
                LogbookItemContract.Bool.ITEM_ID_COLUMN);
        c.moveToFirst();

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(c.getLong(0),c.getString(1));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        db.close();
        return boolItems;
    }


    public ArrayList<BoolComponent> getAllVisible(Context context){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        ArrayList<BoolComponent> result = getAllVisible(db);
        db.close();
        return result;
    }

    public ArrayList<BoolComponent> getAllVisible(SQLiteDatabase db){
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

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(c.getLong(0),c.getString(1));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        return boolItems;
    }
}

