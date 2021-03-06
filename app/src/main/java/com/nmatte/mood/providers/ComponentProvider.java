package com.nmatte.mood.providers;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.nmatte.mood.database.DatabaseHelper;
import com.nmatte.mood.database.components.ComponentContract;

public class ComponentProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.nmatte.mood.comp_provider";
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
                table = ComponentContract.Bool.ITEM_TABLE;
                break;
            case 2:
                selection = "_ID = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                table = ComponentContract.Bool.ITEM_TABLE;
                break;
            case 3:
                table = ComponentContract.Num.ITEM_TABLE;
                break;
            case 4:
                table = ComponentContract.Num.ITEM_TABLE;
                selection = "_ID = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                break;
        }

        Cursor c = db.query(table, projection, selection, selectionArgs, sortOrder, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        //TODO
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = null;

        switch(sURIMatcher.match(uri)) {
            case 1:
            case 2:
                table = ComponentContract.Bool.ITEM_TABLE;
                break;
            case 3:
            case 4:
                table = ComponentContract.Num.ITEM_TABLE;
                break;
        }

        try {
            long id = db.insertOrThrow(
                    table,
                    null,
                    values);

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
            case 2:
                table = ComponentContract.Bool.ITEM_TABLE;
                break;
            case 3:
            case 4:
                table = ComponentContract.Num.ITEM_TABLE;
                break;
        }


        return db.delete(table, ComponentContract.ID_COLUMN + "=?", new String []{uri.getLastPathSegment()});
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO
        return 0;
    }
}
