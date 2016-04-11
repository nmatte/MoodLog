package com.nmatte.mood.database.components;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

public class NumItemTableHelper extends ComponentTableHelper {
    private static final int TRUE = 1,FALSE = 0;

    public NumItemTableHelper(Context context) {
        super(context);
    }

    public void delete(NumComponent component){
//        String whereClause = LogbookItemContract.Num.ITEM_NAME_COLUMN + "=?";
//
//        try{
//            db.delete(LogbookItemContract.Num.ITEM_TABLE, whereClause, new String[]{name});
//        } catch (Exception e){
//            e.printStackTrace();
//        }

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

    public ArrayList<NumComponent> getByParentId(long id) {
        ArrayList<NumComponent> components = new ArrayList<>();


        return components;
    }
}
