package com.nmatte.mood.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.database.components.ComponentContract;
import com.nmatte.mood.database.entries.ChartEntryContract;

public class ColumnProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.nmatte.mood.col_provider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final int
            BOOL_INSERT_ID = 1,
            BOOL_QUERY_ID = 2,
            NUM_INSERT_ID = 3,
            NUM_QUERY_ID = 4;
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, "bools/*", BOOL_INSERT_ID);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "bools",   BOOL_QUERY_ID);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "nums/*",  NUM_INSERT_ID);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "nums",    NUM_QUERY_ID);
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
        String table = ChartEntryContract.ENTRY_TABLE_NAME;


        String columnCheckQuery = "SELECT * FROM "+ table +" LIMIT 0,1";
        return db.rawQuery(columnCheckQuery, null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String col = null;
        String type = null;
        switch (sURIMatcher.match(uri)) {
            case BOOL_INSERT_ID:
                col = uri.getLastPathSegment();
                type = ComponentContract.Bool.LOG_VALUE_TYPE;
                break;
            case NUM_INSERT_ID:
                col = uri.getLastPathSegment();
                type = ComponentContract.Num.LOG_VALUE_TYPE;
                break;
        }

        try {
            if (col != null && type != null ) {
                String addColumnQuery =
                        "ALTER TABLE "+ ChartEntryContract.ENTRY_TABLE_NAME+" ADD COLUMN ? ? ";

                String[] args = new String[] {
                        col,
                        type
                };

                db.execSQL(addColumnQuery, args);

                return uri;
            } else {
                Log.e("ColumnProvider", "Failed to add column");
            }

        } catch (Exception e) {
            Log.e("ColumnProvider", "Failed to add column " + col);
            e.printStackTrace();
        }
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
