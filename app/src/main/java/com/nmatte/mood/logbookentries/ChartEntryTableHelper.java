package com.nmatte.mood.logbookentries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.logbookitems.boolitems.BoolItem;
import com.nmatte.mood.logbookitems.boolitems.BoolItemTableHelper;
import com.nmatte.mood.logbookitems.numitems.NumItem;
import com.nmatte.mood.logbookitems.numitems.NumItemTableHelper;
import com.nmatte.mood.util.DatabaseHelper;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;
import java.util.Iterator;

public class ChartEntryTableHelper {
    /*
    the ChartEntry table has two predefined columns, the ID (which is also the date) and the mood
    values.
    it also has user-defined columns in the form of NumItems and BoolItems.
     */
    public static ArrayList<ChartEntry> getEntryGroup(Context context, DateTime startDate, DateTime endDate){
        SQLiteDatabase db = new DatabaseHelper(context).getReadableDatabase();

        ArrayList<NumItem> numItems = NumItemTableHelper.getAllVisible(db);
        ArrayList<BoolItem> boolItems = BoolItemTableHelper.getAllVisible(db);

        ArrayList<String> preDefinedColumns = new ArrayList<>();
        preDefinedColumns.add(ChartEntryContract.ENTRY_DATE_COLUMN);
        preDefinedColumns.add(ChartEntryContract.ENTRY_MOOD_COLUMN);

        ArrayList<String> allColumns = new ArrayList<>();
        allColumns.addAll(preDefinedColumns);
        allColumns.addAll(NumItem.getColumnNames(numItems));
        allColumns.addAll(BoolItem.getColumnNames(boolItems));

        String [] columns = allColumns.toArray(new String[allColumns.size()]);

        String [] selection = new String[] {
                String.valueOf(startDate.toLocalDate().toString(ChartEntry.DATE_PATTERN)),
                String.valueOf(endDate.toLocalDate().toString(ChartEntry.DATE_PATTERN))
        };

        Cursor c = db.query(
                ChartEntryContract.ENTRY_TABLE_NAME,
                columns,
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null, null, null);

        ArrayList<ChartEntry> result = new ArrayList<>();

        c.moveToFirst();
        try{
            if (c.getCount() > 0){
                do {
                    DateTime entryDate = DateTime.parse(
                            c.getString(c.getColumnIndex(ChartEntryContract.ENTRY_DATE_COLUMN)),
                            ChartEntry.FORMATTER);

                    ArrayList<Boolean> chartMoods = ChartEntry.parseMoodString(
                            c.getString(c.getColumnIndex(ChartEntryContract.ENTRY_MOOD_COLUMN)));


                    SimpleArrayMap<BoolItem,Boolean> boolItemMap = new SimpleArrayMap<>();
                    for (BoolItem item : boolItems){
                        int index = c.getColumnIndex(item.getColumnName());
                        if (index > 0){
                            boolItemMap.put(item,c.getInt(index) != 0);
                        }
                    }

                    SimpleArrayMap<NumItem,Integer> numItemMap = new SimpleArrayMap<>();
                    for (NumItem item : numItems){
                        int index = c.getColumnIndex(item.getColumnName());
                        if (index > 0){
                            numItemMap.put(item,c.getInt(index));
                        }
                    }

                    ChartEntry entry = new ChartEntry(
                            entryDate,
                            chartMoods,
                            numItemMap,
                            boolItemMap);

                    result.add(entry);
                } while(c.moveToNext());
            }
        } catch (Exception e){
                e.printStackTrace();
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

        SimpleArrayMap<NumItem,Integer> numItemMap = entry.getNumItems();

        for (int i = 0; i < numItemMap.size();i++){
            values.put(numItemMap.keyAt(i).getColumnName(),numItemMap.valueAt(i));
        }

        SimpleArrayMap<BoolItem,Boolean> boolItemMap = entry.getBoolItems();
        for (int i = 0; i < boolItemMap.size(); i++){
            values.put(
                    boolItemMap.keyAt(i).getColumnName(),
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
