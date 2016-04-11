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
        ContentValues values = new ContentValues();
        values.put(ComponentContract.NAME_COLUMN, comp.getName());
        values.put(ComponentContract.COLOR_COLUMN, comp.getColor());
        values.put(ComponentContract.PARENT_MODULE_COLUMN, comp.getModuleId());

        try {
            if (comp.getId() != -1) {
                values.put(ComponentContract.ID_COLUMN, comp.getId());
                context.getContentResolver().update(Uri.withAppendedPath(BOOL_URI, String.valueOf(comp.getId())), values, null, null);
            } else {
                Uri result = context.getContentResolver().insert(BOOL_URI, values);
                id = result == null ? -1 : Long.valueOf(result.getLastPathSegment());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return id;
    }

    public long save(NumComponent component){
        ContentValues values = new ContentValues();

        try {
            values.put(ComponentContract.NAME_COLUMN,component.getName());
            values.put(ComponentContract.Num.ITEM_MAX_COLUMN,component.getMaxNum());
            values.put(ComponentContract.Num.ITEM_DEFAULT_COLUMN,component.getDefaultNum());
            Uri uri = Uri.withAppendedPath(ComponentProvider.BASE_URI, "nums");


            if (component.getId() != -1) {
                values.put(ComponentContract.ID_COLUMN, component.getId());
                context.getContentResolver().update(
                        Uri.withAppendedPath(uri, String.valueOf(component.getId())),
                        values,
                        null,
                        null
                );
                return component.getId();
            } else {
                Uri result = context.getContentResolver().insert(
                        uri,
                        values
                );

                return Long.valueOf(result.getLastPathSegment());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }


}
