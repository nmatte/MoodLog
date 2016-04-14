package com.nmatte.mood.database.entries;

import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.util.DateUtils;

import org.joda.time.DateTime;

public class EntryCursorAdapter {
    ModuleConfig config;

    public EntryCursorAdapter(ModuleConfig config) {
        this.config = config;
    }

    public ChartEntry getEntry(Cursor cursor) {
        ContentValues values = new ContentValues();
        int dateIndex = cursor.getColumnIndexOrThrow(config.dateColumn());

        values.put(config.dateColumn(), cursor.getInt(dateIndex));

        for (String column : config.boolColumns()) {
            int index = cursor.getColumnIndex(column);
            if (index >= 0) {
                values.put(column, cursor.getInt(index) == 1);
            }
        }

        for (String column : config.numColumns()) {
            int index = cursor.getColumnIndex(column);
            if (index >= 0) {
                values.put(column, cursor.getInt(index));
            }
        }

        for (String column : config.strColumns()) {
            int index = cursor.getColumnIndex(column);
            if (index >= 0) {
                values.put(column, cursor.getString(index));

            }

        }

        return new ChartEntry(values);
    }

    public ChartEntry getBlank(DateTime date) {
        ContentValues defaults = config.getDefaults();
        defaults.put(config.dateColumn(), DateUtils.getDateInt(date));
        return new ChartEntry(defaults);
    }
}
