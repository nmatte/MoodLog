package com.nmatte.mood.database.components;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nmatte.mood.database.ChartEntryContract;

public class ComponentTableHelper {

    public void addColumn(SQLiteDatabase db, String columnName){
        String columnCheckQuery = "SELECT * FROM "+ ChartEntryContract.ENTRY_TABLE_NAME +" LIMIT 0,1";
        Cursor c = db.rawQuery(columnCheckQuery, null);

        if (c.getColumnIndex(columnName) == -1){
            // column with this name wasn't found so you can safely add a new column.
            String addColumnQuery = "ALTER TABLE " + ChartEntryContract.ENTRY_TABLE_NAME +
                    " ADD COLUMN " + columnName + " " + LogbookItemContract.Bool.LOG_VALUE_TYPE ;
            db.execSQL(addColumnQuery);
            Log.i("ComponentTableHelper", "Added column " + columnName);
        } else {
            Log.i("ComponentTableHelper", "Skipped adding column " + columnName);
        }
        c.close();
    }

}
