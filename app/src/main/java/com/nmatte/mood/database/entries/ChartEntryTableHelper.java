package com.nmatte.mood.database.entries;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.providers.EntryProvider;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntryTableHelper {
    Context context;
    public ChartEntryTableHelper(Context context) {
        this.context = context;
    }

    public SimpleArrayMap<DateTime, ChartEntry> getEntryGroup(DateTime startDate, DateTime endDate){
        ModuleConfig config = new ModuleTableHelper(context).getModules();
        EntryCursorAdapter adapter = new EntryCursorAdapter(config);
        ArrayList<String> allColumns = config.allColumns();
        ArrayList<DateTime> dates = LogDateModule.getDatesInRange(startDate, endDate);
        SimpleArrayMap<DateTime, ChartEntry> result = new SimpleArrayMap<>(dates.size());

        String [] selection = new String[] {
                LogDateModule.getString(startDate),
                LogDateModule.getString(endDate)
        };

        Cursor c = context.getContentResolver().query(
                Uri.withAppendedPath(EntryProvider.BASE_URI, "entries"),
                allColumns.toArray(new String[allColumns.size()]),
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null);
        c.moveToFirst();
        if (c.getCount() > 0) {
            try {
                int dateIndex = 0;
                int dateColumnIndex = c.getColumnIndexOrThrow(config.dateColumn());

                for (int i = 0; i < dates.size(); i++) {
                    DateTime date = dates.get(i);
                    int dateInt = LogDateModule.getDateInt(date);
                    if (!c.isAfterLast() && dateInt == c.getInt(dateColumnIndex)) {
                        result.put(date, adapter.getEntry(c));
                        c.moveToNext();
                    } else {
                        result.put(date, adapter.getBlank(date));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        c.close();
        return result;

    }

    public long save(ChartEntry entry) {
        long id = -1;

        return id;
    }

}
