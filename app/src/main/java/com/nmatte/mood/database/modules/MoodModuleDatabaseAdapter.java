package com.nmatte.mood.database.modules;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.MoodModule;
import com.nmatte.mood.settings.PreferencesContract;

import java.util.ArrayList;

public class MoodModuleDatabaseAdapter extends ModuleDatabaseAdapter {
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<BoolComponent> components = new ArrayList<>();
    boolean isMini;

    public MoodModuleDatabaseAdapter(ModuleInfo info) {
        super(info);
        this.isMini = isMini;
    }

    public static boolean getIsMini(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        return settings.getBoolean(PreferencesContract.MINI_MOOD_MODULE_ENABLED, false);
    }

    @Override
    public Module constructModule(Cursor cursor) {
        MoodModule module = new MoodModule(getComponents(), isMini);

        for (BoolComponent component :
                getComponents()) {
            int index = cursor.getColumnIndex(component.toString());
            if (index > 0){
                module.set(component,cursor.getInt(index) != 0);
            }
        }

        return module;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        // TODO populate list
        return columnNames;
    }

    private ArrayList<BoolComponent> getComponents() {
        // TODO populate list
        return components;
    }

}
