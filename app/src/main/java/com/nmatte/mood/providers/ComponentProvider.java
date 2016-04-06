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

public class ComponentProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.nmatte.mood.provider";
    public static final Uri BASE_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(CONTENT_AUTHORITY, "bools", 1);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "bools/#", 2);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "nums", 3);
        sURIMatcher.addURI(CONTENT_AUTHORITY, "nums/#", 4);
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
        String table = null;
        switch(sURIMatcher.match(uri)) {
            case 1:
                table = LogbookItemContract.Bool.ITEM_TABLE;
                projection = new String[] {
                    LogbookItemContract.ID_COLUMN,
                    LogbookItemContract.PARENT_MODULE_COLUMN,
                    LogbookItemContract.NAME_COLUMN,
                    LogbookItemContract.COLOR_COLUMN,
            };
                break;
            case 2:
                selection = "_ID = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                projection = new String[] {
                        LogbookItemContract.ID_COLUMN,
                        LogbookItemContract.PARENT_MODULE_COLUMN,
                        LogbookItemContract.NAME_COLUMN,
                        LogbookItemContract.COLOR_COLUMN,
                };
                table = LogbookItemContract.Bool.ITEM_TABLE;
                break;
            case 3:
                table = LogbookItemContract.Num.ITEM_TABLE;
                break;
            case 4:
                table = LogbookItemContract.Num.ITEM_TABLE;
                selection = "_ID = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                break;
        }
        Cursor c = db.query(table, projection, selection, selectionArgs, sortOrder, null, null);
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
        String table = null;
        switch(sURIMatcher.match(uri)) {
            case 1:
                table = LogbookItemContract.Bool.ITEM_TABLE;
                break;
            case 2:
                table = LogbookItemContract.Bool.ITEM_TABLE;
                break;
            case 3:
                table = LogbookItemContract.Num.ITEM_TABLE;
                break;
            case 4:
                table = LogbookItemContract.Num.ITEM_TABLE;
                break;
        }

        try {
            long id = db.insertWithOnConflict(
                    table,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);

            return Uri.withAppendedPath(uri, String.valueOf(id));
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String table = null;
        switch(sURIMatcher.match(uri)) {
            case 1:
                return 0;
            case 2:
                table = LogbookItemContract.Bool.ITEM_TABLE;
                break;
            case 3:
                return 0;
            case 4:
                table = LogbookItemContract.Num.ITEM_TABLE;
                break;
        }


        return db.delete(table, LogbookItemContract.ID_COLUMN + "=?", new String []{uri.getLastPathSegment()});
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
