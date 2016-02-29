package com.nmatte.mood.database.components;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class BoolItemTableHelper extends ComponentTableHelper{
    private static final String TRUE = "1",FALSE ="0";

    public BoolComponent save(SQLiteDatabase db, BoolComponent item){
        ContentValues values = new ContentValues();

        try {
            if (item.getId() != -1)
                values.put(LogbookItemContract.ID_COLUMN, item.getId());

            values.put(LogbookItemContract.NAME_COLUMN,item.getName());

            long id = db.insertWithOnConflict(
                    LogbookItemContract.Bool.ITEM_TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);

            item.setId(id);
        } catch (Exception e){
            e.printStackTrace();
        }

        addColumn(db, item.columnLabel());
        return item;
    }

    public void delete(SQLiteDatabase db, BoolComponent component) {
    }

    public ArrayList<BoolComponent> getAll(SQLiteDatabase db){
        String [] columns = new String[] {
                LogbookItemContract.ID_COLUMN,
                LogbookItemContract.NAME_COLUMN
        };

        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                null,
                null,
                null,
                null,
                LogbookItemContract.ID_COLUMN);
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

    public ArrayList<BoolComponent> getByParentId(SQLiteDatabase db, long parentId){
        String [] columns = new String[] {
                LogbookItemContract.ID_COLUMN,
                LogbookItemContract.NAME_COLUMN,
                LogbookItemContract.PARENT_MODULE_COLUMN
        };

        String selection = LogbookItemContract.PARENT_MODULE_COLUMN + "= ?";

        String [] args = new String[] {
                String.valueOf(parentId)
        };

        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                selection,
                args,
                null,
                null,
                LogbookItemContract.ID_COLUMN);
        c.moveToFirst();

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(c.getLong(0),c.getString(1),c.getLong(2));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        return boolItems;
    }
}

