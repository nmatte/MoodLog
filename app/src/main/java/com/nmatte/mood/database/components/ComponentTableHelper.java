package com.nmatte.mood.database.components;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.providers.ColumnProvider;
import com.nmatte.mood.providers.ComponentProvider;

public class ComponentTableHelper {
    private static final String TRUE = "1", FALSE ="0";
    private static final Uri BOOL_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("bools").build();
    private static final Uri NUM_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("nums").build();
    Context context;


    public ComponentTableHelper(Context context) {
        this.context = context;
    }

    public long insert(BoolComponent comp) {
        long id = -1;

        try {
            if (comp.getId() == -1) {
                Uri result = context.getContentResolver().insert(BOOL_URI, comp.asValues());
                id = result == null ? -1 : Long.valueOf(result.getLastPathSegment());

                if (id > 0) {
                    comp.setId(id);
                    saveBoolColumn(comp);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public long insert(NumComponent comp){
        long id = -1;
        try {
            if (comp.getId() != -1) {
                Uri result = context.getContentResolver().insert(NUM_URI, comp.asValues());
                id = result == null ? -1 : Long.valueOf(result.getLastPathSegment());

                if (id > 0) {
                    comp.setId(id);
                    saveNumColumn(comp);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public void saveBoolColumn(BoolComponent component) {
        Cursor colExistsCursor = context
                .getContentResolver()
                .query(Uri.withAppendedPath(ColumnProvider.BASE_URI, "bools"), null, null, null, null);

        if (colExistsCursor != null) {
            if (colExistsCursor.getColumnIndex(component.columnLabel()) == -1) {
                Uri insertUri = ColumnProvider.BASE_URI.buildUpon().appendPath("bools").appendPath(component.columnLabel()).build();
                context.getContentResolver().insert(insertUri, null);

            }
            colExistsCursor.close();
        }
    }

    public void saveNumColumn(NumComponent component) {
        Uri numColUri = Uri.withAppendedPath(ColumnProvider.BASE_URI, "nums");

        Cursor colExistsCursor = context
                .getContentResolver()
                .query(numColUri, null, null, null, null);

        if (colExistsCursor != null) {
            if (colExistsCursor.getColumnIndex(component.columnLabel()) == -1) {
                Uri insertUri = numColUri.buildUpon().appendPath(component.columnLabel()).build();
                context.getContentResolver().insert(insertUri, null);
            }
            colExistsCursor.close();
        }
    }


}
