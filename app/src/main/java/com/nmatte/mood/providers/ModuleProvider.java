package com.nmatte.mood.providers;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.database.modules.ModuleContract;

public class ModuleProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.nmatte.mood.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, "modules", 1);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "modules/#", 2);
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
        switch(sURIMatcher.match(uri)) {
            case 1:
                break;
            case 2:
                selection = ModuleContract.MODULE_ID_COLUMN + "=?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                break;
        }
        return db.query(ModuleContract.MODULE_TABLE_NAME, projection, selection, selectionArgs, null, null, null);
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
