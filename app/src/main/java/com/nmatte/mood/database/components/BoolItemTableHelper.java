package com.nmatte.mood.database.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.providers.ComponentProvider;

import java.util.ArrayList;

public class BoolItemTableHelper extends ComponentTableHelper{
    private static final String TRUE = "1", FALSE ="0";
    private static final Uri BOOL_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("bools").build();

    public BoolItemTableHelper(Context context) {
        super(context);
    }

    public BoolComponent find(long id) {
        BoolComponent item = null;
    try {
        Uri uri = Uri.withAppendedPath(BOOL_URI, String.valueOf(id));
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            item = new BoolComponent(cursor);
            cursor.close();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return item;
}

    public void delete(BoolComponent component) {
        try {
            Uri uri = Uri.withAppendedPath(BOOL_URI, String.valueOf(component.getId()));
            context.getContentResolver().delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BoolComponent> getAll(){
        ArrayList<BoolComponent> boolItems = new ArrayList<>();

        try {
            String [] columns = new String[] {
                    LogbookItemContract.ID_COLUMN,
                    LogbookItemContract.NAME_COLUMN,
                    LogbookItemContract.COLOR_COLUMN,
                    LogbookItemContract.PARENT_MODULE_COLUMN
            };

            Cursor cursor = context.getContentResolver().query(
                    BOOL_URI,
                    columns,
                    null,
                    null,
                    LogbookItemContract.ID_COLUMN);

            if(cursor != null && cursor.getCount() > 0){
                cursor.moveToFirst();
                do{
                    BoolComponent item = new BoolComponent(cursor.getLong(0),cursor.getString(1), cursor.getInt(2));
                    boolItems.add(item);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boolItems;
    }

    public ArrayList<BoolComponent> getByParentId(long parentId){
        String [] columns = new String[] {
                LogbookItemContract.ID_COLUMN,
                LogbookItemContract.NAME_COLUMN,
                LogbookItemContract.PARENT_MODULE_COLUMN
        };

        String selection = LogbookItemContract.PARENT_MODULE_COLUMN + "= ?";

        String [] args = new String[] {
                String.valueOf(parentId)
        };

        Cursor cursor = context.getContentResolver().query(
                BOOL_URI,
                columns,
                selection,
                args,
                LogbookItemContract.ID_COLUMN);
        cursor.moveToFirst();

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(cursor.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(cursor.getLong(0),cursor.getString(1),cursor.getLong(2));
                boolItems.add(m);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return boolItems;
    }
}

