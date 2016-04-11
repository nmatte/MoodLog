package com.nmatte.mood.database.components;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.providers.ComponentProvider;

import java.util.ArrayList;

public class NumItemTableHelper extends ComponentTableHelper {
    private static final int TRUE = 1,FALSE = 0;
    Uri NUM_URI = Uri.withAppendedPath(ComponentProvider.BASE_URI, "nums");

    public NumItemTableHelper(Context context) {
        super(context);
    }

    public void delete(NumComponent component){
//        String whereClause = ComponentContract.Num.ITEM_NAME_COLUMN + "=?";
//
//        try{
//            db.delete(ComponentContract.Num.ITEM_TABLE, whereClause, new String[]{name});
//        } catch (Exception e){
//            e.printStackTrace();
//        }

    }


    public ArrayList<NumComponent> getAll(){
        ArrayList<NumComponent> components = new ArrayList<>();

        try {
            Cursor cursor = context.getContentResolver().query(NUM_URI, columns(), null, null, ComponentContract.ID_COLUMN);

            if (cursor == null) {
                return components;
            }
            cursor.moveToFirst();

            if (cursor.getCount() > 0){
                do {
                    NumComponent component = new NumComponent(cursor);
                    components.add(component);
                } while(cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("NumComponentTableHelper", e.getMessage());
        }

        return components;
    }

    public ArrayList<NumComponent> getByParentId(long id) {
        ArrayList<NumComponent> components = new ArrayList<>();

        return components;
    }

    public String[] columns() {
        return new String[] {
                ComponentContract.ID_COLUMN,
                ComponentContract.NAME_COLUMN,
                ComponentContract.COLOR_COLUMN,
                ComponentContract.PARENT_MODULE_COLUMN,
                ComponentContract.Num.ITEM_MAX_COLUMN,
                ComponentContract.Num.ITEM_DEFAULT_COLUMN
        };
    }
}
