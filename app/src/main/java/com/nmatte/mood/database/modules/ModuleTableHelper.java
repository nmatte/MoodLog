package com.nmatte.mood.database.modules;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.database.components.NumItemTableHelper;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.models.modules.NoteModule;
import com.nmatte.mood.models.modules.NumModule;
import com.nmatte.mood.providers.ComponentProvider;

import java.util.ArrayList;

public class ModuleTableHelper {
    private static final Uri MODULE_URI = ComponentProvider.BASE_URI.buildUpon().appendPath("modules").build();
    Context context;

    public ModuleTableHelper(Context context) {
        this.context = context;
    }


    public void save(Module module) {
//        ContentValues values = new ContentValues();
//        try {
//            values.put(ModuleContract.MODULE_VISIBLE_COLUMN, true);
//            values.put(ModuleContract.MODULE_NAME_COLUMN, name);
//
//            db.insertWithOnConflict(
//                    ModuleContract.MODULE_TABLE_NAME,
//                    null,
//                    values,
//                    SQLiteDatabase.CONFLICT_REPLACE);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }



    public ModuleConfig getModules(){
        ArrayList<Module> modules = new ArrayList<>();

        try {
            String[] projection = new String[] {
                    ModuleContract.MODULE_ID_COLUMN,
                    ModuleContract.MODULE_NAME_COLUMN,
                    ModuleContract.MODULE_VISIBLE_COLUMN
            };


            Cursor cursor = context.getContentResolver().query(MODULE_URI, projection, null, null, null);

            cursor.moveToFirst();


            BoolItemTableHelper bHelper = new BoolItemTableHelper(context);
            NumItemTableHelper nHelper = new NumItemTableHelper(context);
            do {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                boolean isVisible = cursor.getInt(2) == 1;
                switch (name) {
                    case ModuleContract.BOOL_MODULE_NAME:
                        modules.add(new BoolModule(id, name, isVisible, bHelper.getByParentId(id)));
                        break;
                    case ModuleContract.MOOD_MODULE_NAME:
                        modules.add(new MoodModule(id, name, isVisible, bHelper.getByParentId(id)));
                        break;
                    case ModuleContract.NOTE_MODULE_NAME:
                        modules.add(new NoteModule(id, name, isVisible));
                        break;
                    case ModuleContract.NUM_MODULE_NAME:
                        modules.add(new NumModule(id, name, isVisible, nHelper.getByParentId(id)));
                        break;
                }
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("ModuleTableHelper", e.getMessage());
        }

        return new ModuleConfig(modules);
    }


}
