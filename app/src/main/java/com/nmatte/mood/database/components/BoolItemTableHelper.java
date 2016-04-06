package com.nmatte.mood.database.components;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.providers.ComponentProvider;

import java.util.ArrayList;

public class BoolItemTableHelper extends ComponentTableHelper{
    private static final String TRUE = "1", FALSE ="0";
    private static final Uri BOOL_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("bools").build();
    public BoolComponent save(Context c, BoolComponent item){
        try {
            ContentValues values = new ContentValues();
            if (item.getId() != -1) {
                values.put(LogbookItemContract.ID_COLUMN, item.getId());
            }
            values.put(LogbookItemContract.NAME_COLUMN, item.getName());
            String id = c.getContentResolver().insert(BOOL_URI, values).getLastPathSegment();
            item.setId(Long.valueOf(id));
        } catch (Exception e){
            e.printStackTrace();
        }

        return item;
    }

    public BoolComponent find(Context context, long id) {
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

    public void delete(Context context, BoolComponent component) {
    }

    public ArrayList<BoolComponent> getAll(Context c){
        ArrayList<BoolComponent> boolItems = new ArrayList<>();

        try {
            String [] columns = new String[] {
                    LogbookItemContract.ID_COLUMN,
                    LogbookItemContract.NAME_COLUMN
            };

            Cursor cursor = c.getContentResolver().query(
                    BOOL_URI,
                    columns,
                    null,
                    null,
                    LogbookItemContract.ID_COLUMN);

            cursor.moveToFirst();

            if(cursor.getCount() > 0){
                do{
                    BoolComponent m = new BoolComponent(cursor.getLong(0),cursor.getString(1));
                    boolItems.add(m);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boolItems;
    }

    public ArrayList<BoolComponent> getByParentId(SQLiteDatabase db, long parentId){
        String [] columns = new String[] {
                LogbookItemContract.ID_COLUMN,
                LogbookItemContract.NAME_COLUMN,
                LogbookItemContract.PARENT_MODULE_COLUMN
        };

        String selection = LogbookItemContract.PARENT_MODULE_COLUMN + "= ?";

        String [] args = new String[] {
                String.valueOf(parentId)
        };

        Cursor c = db.query(
                LogbookItemContract.Bool.ITEM_TABLE,
                columns,
                selection,
                args,
                null,
                null,
                LogbookItemContract.ID_COLUMN);
        c.moveToFirst();

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(c.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(c.getLong(0),c.getString(1),c.getLong(2));
                boolItems.add(m);
            } while(c.moveToNext());
        }
        c.close();
        return boolItems;
    }
}

