package com.nmatte.mood.models;

import android.content.ContentValues;

public class ChartEntry {
    ContentValues values;

    public ChartEntry(ContentValues values) {
        this.values = values;
    }

    public ContentValues values() {
        return values;
    }
}
