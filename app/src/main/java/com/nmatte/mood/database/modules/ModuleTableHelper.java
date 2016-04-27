package com.nmatte.mood.database.modules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.database.components.NumItemTableHelper;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.models.modules.NoteModule;
import com.nmatte.mood.models.modules.NumModule;
import com.nmatte.mood.providers.ModuleProvider;

public class ModuleTableHelper {
    Context context;

    public ModuleTableHelper(Context context) {
        this.context = context;
    }


    public long save(String name) {
        ContentValues values = new ContentValues();
        try {
            values.put(ModuleContract.MODULE_VISIBLE_COLUMN, true);
            values.put(ModuleContract.MODULE_NAME_COLUMN, name);
            Uri uri = ModuleProvider.BASE_URI;
            Uri result = context.getContentResolver().insert(uri, values);

            return result == null ? -1 : Long.valueOf(result.getLastPathSegment());
        } catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public long getId(String name) {
        String[] projection = new String[] {ModuleContract.MODULE_ID_COLUMN};
        String selection = ModuleContract.MODULE_NAME_COLUMN + "=?";
        String[] selectionArgs = new String[] {name};
        try {
            Cursor cursor = context.getContentResolver().query(ModuleProvider.BASE_URI, projection, selection, selectionArgs, null);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int id = cursor.getInt(0);
                return id;
            }

                cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }



    public ModuleConfig getModules(){
        String[] projection = new String[] {
                ModuleContract.MODULE_ID_COLUMN,
                ModuleContract.MODULE_NAME_COLUMN,
                ModuleContract.MODULE_VISIBLE_COLUMN
        };

        try {
            Cursor cursor = context.getContentResolver().query(ModuleProvider.BASE_URI, projection, null, null, null);
            if (cursor == null) {
                return null;
            }

            cursor.moveToFirst();

            BoolItemTableHelper bHelper = new BoolItemTableHelper(context);
            NumItemTableHelper nHelper = new NumItemTableHelper(context);
            BoolModule bMod = new BoolModule(true, bHelper.getByParentId(ModuleContract.Bool.ID));
            MoodModule mMod = new MoodModule(true, bHelper.getByParentId(ModuleContract.Mood.ID));
            NumModule numMod = new NumModule(true, nHelper.getByParentId(ModuleContract.Num.ID));
            NoteModule noteMod = new NoteModule(-1, ModuleContract.NOTE_MODULE_NAME, true);

            cursor.close();
            return new ModuleConfig(bMod, mMod, numMod, noteMod);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ModuleTableHelper", e.getMessage());
        }

        return null;
    }


}
