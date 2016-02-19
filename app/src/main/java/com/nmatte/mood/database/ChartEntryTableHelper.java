package com.nmatte.mood.database;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nmatte.mood.database.modules.ModuleDatabaseAdapter;
import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.Module;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntryTableHelper {
    ArrayList<ModuleDatabaseAdapter> adapters;
    private DatabaseHelper helper;

    public ChartEntryTableHelper(DatabaseHelper helper) {
        this.helper = helper;
        setAdapters();
    }

    private void setAdapters() {
        SQLiteDatabase db = helper.getReadableDatabase();

        adapters = new ModuleTableHelper(db).getAdapters();

        db.close();
    }

    public ArrayList<ChartEntry> getEntryGroup(DateTime startDate, DateTime endDate){
        // swap dates if out of order
        if (startDate.isAfter(endDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }

        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> allColumns = new ArrayList<>();

        allColumns.add(ChartEntryContract.ENTRY_DATE_COLUMN);
        for (ModuleDatabaseAdapter adapter : adapters) {
            allColumns.addAll(adapter.getColumnNames());
        }

        String [] selection = new String[] {
                String.valueOf(startDate.toLocalDate().toString(LogDateModule.DATE_PATTERN)),
                String.valueOf(endDate.toLocalDate().toString(LogDateModule.DATE_PATTERN))
        };

        Cursor c = db.query(
                ChartEntryContract.ENTRY_TABLE_NAME,
                allColumns.toArray(new String[allColumns.size()]),
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null, null, null);

        ArrayList<ChartEntry> result = new ArrayList<>();

        c.moveToFirst();

        if (c.getCount() > 0) {
            try {
                do {
                    DateTime entryDate = DateTime.parse(
                            String.valueOf(c.getInt(c.getColumnIndex(ChartEntryContract.ENTRY_DATE_COLUMN))),
                            LogDateModule.YEAR_DAY_FORMATTER
                    );

                    ArrayList<Module> mods = new ArrayList<>();

                    for (ModuleDatabaseAdapter adapter : adapters) {
                        mods.add(adapter.constructModule(c));
                    }

                    ChartEntry entry = new ChartEntry(entryDate, mods);

                    result.add(entry);
                } while (c.moveToNext());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        c.close();
        db.close();
        return result;

    }


//    public void addOrUpdateEntry(ChartEntry entry) {
//        SQLiteDatabase db = helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ChartEntryContract.ENTRY_DATE_COLUMN,entry.getDateInt());
//
//        for (ModuleDatabaseAdapter adapter :
//                adapters) {
//            adapter.putModule(values, );
//        }
//
//        try{
//            db.insertWithOnConflict(ChartEntryContract.ENTRY_TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        db.close();
//    }

}
