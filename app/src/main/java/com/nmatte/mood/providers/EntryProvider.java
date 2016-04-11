package com.nmatte.mood.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.models.modules.LogDateModule;

import org.joda.time.DateTime;

public class EntryProvider extends ContentProvider{
    public static final String CONTENT_AUTHORITY = "com.nmatte.mood.entry_provider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, "entries", 1);
    }

    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        db = new DatabaseHelper(getContext()).getWritableDatabase();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sURIMatcher.match(uri)) {
            case 1:
                break;
        }

        if (selection == null && selectionArgs == null) {
            String end = DateTime.now().toLocalDate().toString(LogDateModule.DATE_PATTERN);
            String begin = DateTime.now().minusDays(28).toLocalDate().toString(LogDateModule.DATE_PATTERN);
            selection = ChartEntryContract.ENTRY_DATE_COLUMN + " BETWEEN ? AND ?";
            selectionArgs = new String[] {begin, end};
        }


        Cursor c =  db.query(ChartEntryContract.ENTRY_TABLE_NAME, projection, selection, selectionArgs, sortOrder, null, null);
        c.moveToFirst();
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
