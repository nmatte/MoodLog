package com.nmatte.mood.database.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.providers.ComponentProvider;

import java.util.ArrayList;

public class BoolItemTableHelper extends ComponentTableHelper{
    private static final String TRUE = "1", FALSE ="0";
    private static final Uri BOOL_URI = Uri.withAppendedPath(ComponentProvider.BASE_URI, "bools");

    public BoolItemTableHelper(Context context) {
        super(context);
    }

    public int delete(BoolComponent component) {
        try {
            Uri uri = Uri.withAppendedPath(BOOL_URI, String.valueOf(component.getId()));
            return context.getContentResolver().delete(uri, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public BoolComponent find(long id) {
        BoolComponent item = null;
        if (id == -1) {
            return null;
        }

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

    public ArrayList<BoolComponent> getAll(){
        ArrayList<BoolComponent> boolItems = new ArrayList<>();

        try {
            Cursor cursor = context.getContentResolver().query(BOOL_URI, columns(), null, null, ComponentContract.ID_COLUMN);
            if (cursor == null) {
                return boolItems;
            }

            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                do {
                    BoolComponent item = new BoolComponent(cursor);
                    boolItems.add(item);
                } while(cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boolItems;
    }

    public ArrayList<BoolComponent> getByParentId(long parentId){
        String selection = ComponentContract.PARENT_MODULE_COLUMN + "= ?";

        String [] args = new String[] {
                String.valueOf(parentId)
        };

        Cursor cursor = context.getContentResolver().query(
                BOOL_URI,
                columns(),
                selection,
                args,
                ComponentContract.ID_COLUMN);
        cursor.moveToFirst();

        ArrayList<BoolComponent> boolItems = new ArrayList<>();
        if(cursor.getCount() > 0){
            do{
                BoolComponent m = new BoolComponent(cursor);
                boolItems.add(m);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return boolItems;
    }

    public String[] columns() {
        return new String[] {
                ComponentContract.ID_COLUMN,
                ComponentContract.NAME_COLUMN,
                ComponentContract.COLOR_COLUMN,
                ComponentContract.PARENT_MODULE_COLUMN
        };
    }
}

