package com.nmatte.mood.database.entries;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.nmatte.mood.database.modules.ModuleTableHelper;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.providers.EntryProvider;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class ChartEntryTableHelper {
    Context context;
    public ChartEntryTableHelper(Context context) {
        this.context = context;
    }

    public ArrayList<ChartEntry> getEntryGroup(DateTime startDate, DateTime endDate){
        ModuleConfig config = new ModuleTableHelper(context).getModules();
        EntryCursorAdapter adapter = new EntryCursorAdapter(config);

        // swap dates if out of order
        if (startDate.isAfter(endDate)){
            DateTime tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        ArrayList<String> allColumns = new ArrayList<>();

        allColumns.add(ChartEntryContract.ENTRY_DATE_COLUMN);

        String [] selection = new String[] {
                String.valueOf(startDate.toLocalDate().toString(LogDateModule.DATE_PATTERN)),
                String.valueOf(endDate.toLocalDate().toString(LogDateModule.DATE_PATTERN))
        };

        Cursor c = context.getContentResolver().query(
                Uri.withAppendedPath(EntryProvider.BASE_URI, "entries"),
                allColumns.toArray(new String[allColumns.size()]),
                ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?",
                selection,
                null);

        ArrayList<ChartEntry> result = new ArrayList<>();

        if (c.getCount() > 0) {
            try {
                do {
                    DateTime entryDate = DateTime.parse(
                            String.valueOf(c.getInt(c.getColumnIndex(ChartEntryContract.ENTRY_DATE_COLUMN))),
                            LogDateModule.YEAR_DAY_FORMATTER
                    );

                    ArrayList<Module> mods = new ArrayList<>();



//                    ChartEntry entry = new ChartEntry(entryDate, mods);

//                    result.add(entry);
                } while (c.moveToNext());

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
