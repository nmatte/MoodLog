package com.nmatte.mood.database.components;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

public class NumItemTableHelper extends ComponentTableHelper {
    private static final int TRUE = 1,FALSE = 0;


    public NumComponent save(SQLiteDatabase db, NumComponent component){
        ContentValues values = new ContentValues();

        try {
            if (component.getId() != -1)
                values.put(LogbookItemContract.ID_COLUMN, component.getId());
            values.put(LogbookItemContract.NAME_COLUMN,component.getName());
            values.put(LogbookItemContract.Num.ITEM_MAX_COLUMN,component.getMaxNum());
            values.put(LogbookItemContract.Num.ITEM_DEFAULT_COLUMN,component.getDefaultNum());

            long id = db.insertWithOnConflict(
                    LogbookItemContract.Num.ITEM_TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            component.setId(id);
        } catch (Exception e){
            e.printStackTrace();
        }

        this.addColumn(db, component.columnLabel());
        return component;
    }

    public void delete(SQLiteDatabase db, NumComponent component){
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
    }

    public ArrayList<NumComponent> getAll(SQLiteDatabase db){
        String [] columns = new String[] {
                LogbookItemContract.ID_COLUMN,
                LogbookItemContract.NAME_COLUMN,
                LogbookItemContract.Num.ITEM_MAX_COLUMN,
                LogbookItemContract.Num.ITEM_DEFAULT_COLUMN
        };

        Cursor c = db.query(
                LogbookItemContract.Num.ITEM_TABLE,
                columns,
                null,null,null,null,
                LogbookItemContract.ID_COLUMN);
        c.moveToFirst();

        ArrayList<NumComponent> numItems = new ArrayList<>();
        if (c.getCount() > 0){
            do {
                NumComponent component = new NumComponent(c.getLong(0),c.getString(1),c.getInt(2),c.getInt(3));
                numItems.add(component);
            } while(c.moveToNext());
        }
        c.close();

        return numItems;
    }
}
