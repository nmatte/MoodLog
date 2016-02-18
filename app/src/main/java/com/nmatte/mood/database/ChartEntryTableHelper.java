package com.nmatte.mood.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.database.modules.BoolModuleDatabaseAdapter;
import com.nmatte.mood.database.modules.ModuleDatabaseAdapter;
import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.database.modules.NumModuleDatabaseAdapter;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.models.modules.NoteModule;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Iterator;

public class ChartEntryTableHelper {
    private static ArrayList<ModuleDatabaseAdapter> getAdapters(ModuleTableHelper helper) {
        ArrayList<ModuleDatabaseAdapter> adapters = new ArrayList<>();
        BoolModuleDatabaseAdapter boolAdapter = new BoolModuleDatabaseAdapter(helper);
        if (boolAdapter.isVisible()) {
            adapters.add(boolAdapter);
        }

        NumModuleDatabaseAdapter numAdapter = new NumModuleDatabaseAdapter(helper);
        if (numAdapter.isVisible()) {
            adapters.add(numAdapter);
        }

        return adapters;
    }

    public static ArrayList<ChartEntry> getEntryGroup(Context context, DateTime startDate, DateTime endDate){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();
        ModuleTableHelper helper = new ModuleTableHelper(db);

        ArrayList<ModuleDatabaseAdapter> adapters = getAdapters(helper);

        ArrayList<String> allColumns = new ArrayList<>();

        for (ModuleDatabaseAdapter adapter : adapters) {
            allColumns.addAll(adapter.getColumnNames());
        }

        ArrayList<String> predefinedColumns = new ArrayList<>();
        predefinedColumns.add(ChartEntryContract.ENTRY_DATE_COLUMN);
        predefinedColumns.add(ChartEntryContract.ENTRY_MOOD_COLUMN);
        predefinedColumns.add(ChartEntryContract.ENTRY_NOTE_COLUMN);

        allColumns.addAll(predefinedColumns);

        String [] columns = allColumns.toArray(new String[allColumns.size()]);

        String [] selection = new String[] {
                String.valueOf(startDate.toLocalDate().toString(LogDateModule.DATE_PATTERN)),
                String.valueOf(endDate.toLocalDate().toString(LogDateModule.DATE_PATTERN))
        };

        Cursor c = db.query(
                ChartEntryContract.ENTRY_TABLE_NAME,
                columns,
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null, null, null);

        ArrayList<ChartEntry> result = new ArrayList<>();

        c.moveToFirst();

        if (c.getCount() > 0) {
            try {
                do {
                    DateTime entryDate = DateTime.parse(
                            c.getString(c.getColumnIndex(ChartEntryContract.ENTRY_DATE_COLUMN)),
                            ChartEntry.YEAR_DAY_FORMATTER);

                    ArrayList<Boolean> chartMoods = ChartEntry.parseMoodString(
                            c.getString(c.getColumnIndex(ChartEntryContract.ENTRY_MOOD_COLUMN)));

                    String note = c.getString(c.getColumnIndex(ChartEntryContract.ENTRY_NOTE_COLUMN));


                    ArrayList<Module> mods = new ArrayList<>();

                    for (ModuleDatabaseAdapter adapter : adapters) {
                        mods.add(adapter.constructModule(c));
                    }

                    mods.add(new MoodModule(chartMoods));
                    mods.add(new NoteModule(note));
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

    public static ArrayList<ChartEntry> getGroupWithBlanks(Context context, DateTime startDate, DateTime endDate){
        // swap dates if out of order
        if (startDate.isAfter(endDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        int numDays = Days.daysBetween(startDate.toLocalDate(),endDate.toLocalDate()).getDays();
        // Joda-time's daysBetween function takes the end date as exclusive.
        // We need to add 1 so that numDays includes the end date.
        numDays++;
        ArrayList<ChartEntry> sparseEntries = getEntryGroup(context, startDate, endDate);
        ArrayList<ChartEntry> result = new ArrayList<>();
        DateTime currentDate = new DateTime(startDate);

        ChartEntry currentEntry = null;
        Iterator<ChartEntry> it = sparseEntries.iterator();
        if (it.hasNext()){
            currentEntry = it.next();
        }

        // for the number of days between start and end
        for (int i = 0; i < numDays; i++){
           if (currentEntry != null){
               // if current entry (from sparse entries) has same as current date
               if (currentEntry.getLogDate().getDayOfYear() == currentDate.getDayOfYear()){
                   result.add(currentEntry);
                   if (it.hasNext())
                       currentEntry = it.next();
               } else {
               // current entry doesn't have same date so just add a "blank" one
                   result.add(new ChartEntry(currentDate));
               }
           } else {
               result.add(new ChartEntry(currentDate));
           }
            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    public static void addOrUpdateEntry(Context context, ChartEntry entry) {
        SQLiteDatabase db = new DatabaseHelper(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ChartEntryContract.ENTRY_DATE_COLUMN,entry.getDateInt());
        values.put(ChartEntryContract.ENTRY_MOOD_COLUMN,entry.getMoodString());
        values.put(ChartEntryContract.ENTRY_NOTE_COLUMN,entry.getNote());

        SimpleArrayMap<NumComponent,Integer> numItemMap = entry.getNumItems();

        for (int i = 0; i < numItemMap.size();i++){
            values.put(numItemMap.keyAt(i).columnLabel(),numItemMap.valueAt(i));
        }

        SimpleArrayMap<BoolComponent,Boolean> boolItemMap = entry.getBoolItems();
        for (int i = 0; i < boolItemMap.size(); i++){
            values.put(
                    boolItemMap.keyAt(i).toString(),
                    boolItemMap.valueAt(i) ? 1 : 0); // 1 for true, 0 for false
        }

        try{
            db.insertWithOnConflict(ChartEntryContract.ENTRY_TABLE_NAME,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e){
            e.printStackTrace();
        }

        db.close();
    }

}
