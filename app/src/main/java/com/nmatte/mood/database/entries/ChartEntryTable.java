package com.nmatte.mood.database.entries;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.providers.EntryProvider;
import com.nmatte.mood.util.DateUtils;

import org.joda.time.DateTime;

public class ChartEntryTable {
    Context context;
    public ChartEntryTable(Context context) {
        this.context = context;
    }

    public SimpleArrayMap<DateTime, ChartEntry> getEntryGroup(DateTime startDate, DateTime endDate){
        SimpleArrayMap<DateTime, ChartEntry> result = new SimpleArrayMap<>();

        String [] selection = new String[] {
                DateUtils.getString(startDate),
                DateUtils.getString(endDate)
        };

        Cursor c = context.getContentResolver().query(
                Uri.withAppendedPath(EntryProvider.BASE_URI, "entries"),
                null,
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null);


        try {
            if (c != null) {
                c.moveToFirst();
                int dateColumnIndex = c.getColumnIndexOrThrow(ChartEntryContract.ENTRY_DATE_COLUMN);

                if (c.getCount() > 0) {
                    do {
                        DateTime date = DateUtils.fromInt(c.getInt(dateColumnIndex));
                        ChartEntry entry = new ChartEntry(new ContentValues());
                        DatabaseUtils.cursorRowToContentValues(c, entry.values());
                        result.put(date, entry);
                    } while(c.moveToNext());
                }
                c.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    public long save(ChartEntry entry) {
        long id = -1;

        context.getContentResolver().insert(EntryProvider.BASE_URI, entry.values());

        return id;
    }
}
