package com.nmatte.mood.database.modules;

import android.content.Context;
import android.database.Cursor;
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
