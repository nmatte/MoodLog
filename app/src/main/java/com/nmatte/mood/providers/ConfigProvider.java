package com.nmatte.mood.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.database.components.LogbookItemContract;

public class ConfigProvider extends ContentProvider {
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI("components", "bools", 1);
        sURIMatcher.addURI("components", "bools/#", 2);

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
        String table = LogbookItemContract.Bool.ITEM_TABLE;
        switch(sURIMatcher.match(uri)) {
            case 1:
                break;
            case 2:
                selection = "_ID = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                break;
        }

        return db.query(table, projection, selection, selectionArgs, sortOrder, null, null);
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
