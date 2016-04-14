package com.nmatte.mood.database.entries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.providers.EntryProvider;
import com.nmatte.mood.util.DateUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntryTableHelper {
    Context context;
    public ChartEntryTableHelper(Context context) {
        this.context = context;
    }

    public SimpleArrayMap<DateTime, ChartEntry> getEntryGroup(DateTime startDate, DateTime endDate){
        ModuleConfig config = new ModuleTableHelper(context).getModules();
        config.all();
        EntryCursorAdapter adapter = new EntryCursorAdapter(config);
        ArrayList<String> allColumns = config.allColumns();
        SimpleArrayMap<DateTime, ChartEntry> result = DateUtils.getEmptyMapInRange(startDate, endDate, config);

        String [] selection = new String[] {
                DateUtils.getString(startDate),
                DateUtils.getString(endDate)
        };

        Cursor c = context.getContentResolver().query(
                Uri.withAppendedPath(EntryProvider.BASE_URI, "entries"),
                allColumns.toArray(new String[allColumns.size()]),
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null);
        c.moveToFirst();

        try {
            int dateColumnIndex = c.getColumnIndexOrThrow(config.dateColumn());

            if (c.getCount() > 0) {
                do {
                    int dateInt = c.getInt(dateColumnIndex);
                    DateTime date = DateUtils.fromInt(dateInt);

                    result.put(date, adapter.getEntry(c));
                } while(c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        c.close();
        return result;

    }

    public long save(ChartEntry entry) {
        long id = -1;

        return id;
    }

    public ChartEntry get(int dateId) {
        ModuleConfig config = new ModuleTableHelper(context).getModules();



        return new ChartEntry(new ContentValues());
    }

}
