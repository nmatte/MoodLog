package com.nmatte.mood.database.components;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.providers.ComponentProvider;

public class ComponentTableHelper {
    private static final String TRUE = "1", FALSE ="0";
    private static final Uri BOOL_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("bools").build();
    Context context;


    public ComponentTableHelper(Context context) {
        this.context = context;
    }

    public long save(BoolComponent comp) {
        long id = -1;
        try {
            ContentValues values = new ContentValues();
            values.put(LogbookItemContract.NAME_COLUMN, comp.getName());

            if (comp.getId() != -1) {
                values.put(LogbookItemContract.ID_COLUMN, comp.getId());
                context.getContentResolver().update(Uri.withAppendedPath(BOOL_URI, String.valueOf(comp.getId())), values, null, null);
            } else {
                Uri result = context.getContentResolver().insert(BOOL_URI, values);
                id = Long.valueOf(result.getLastPathSegment());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public void save(NumComponent comp) {

    }


}
