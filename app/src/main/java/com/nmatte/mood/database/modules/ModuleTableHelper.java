package com.nmatte.mood.database.modules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.nmatte.mood.database.components.BoolItemTableHelper;
import com.nmatte.mood.database.components.NumItemTableHelper;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.models.modules.NoteModule;
import com.nmatte.mood.models.modules.NumModule;
import com.nmatte.mood.providers.ModuleProvider;

import java.util.ArrayList;

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
            BoolModule bMod = new BoolModule(-1, ModuleContract.BOOL_MODULE_NAME, false, new ArrayList<BoolComponent>());
            MoodModule mMod = new MoodModule(-1, ModuleContract.MOOD_MODULE_NAME, false, new ArrayList<BoolComponent>());
            NumModule numMod = new NumModule(-1, ModuleContract.NUM_MODULE_NAME, false, new ArrayList<NumComponent>());
            NoteModule noteMod = new NoteModule(-1, ModuleContract.NOTE_MODULE_NAME, false);
            do {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                boolean isVisible = cursor.getInt(2) == 1;
                switch (name) {
                    case ModuleContract.BOOL_MODULE_NAME:
                        bMod = new BoolModule(id, name, isVisible, bHelper.getByParentId(id));
                        break;
                    case ModuleContract.MOOD_MODULE_NAME:
                        mMod = new MoodModule(id, name, isVisible, bHelper.getByParentId(id));
                        break;
                    case ModuleContract.NOTE_MODULE_NAME:
                        noteMod = new NoteModule(id, name, isVisible);
                        break;
                    case ModuleContract.NUM_MODULE_NAME:
                        numMod = new NumModule(id, name, isVisible, nHelper.getByParentId(id));
                        break;
                }
            } while (cursor.moveToNext());
            cursor.close();
            return new ModuleConfig(bMod, mMod, numMod, noteMod);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ModuleTableHelper", e.getMessage());
        }

        return null;
    }


}
