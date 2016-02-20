package com.nmatte.mood.database.modules;


import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.MoodModule;

import java.util.ArrayList;

public class MoodModuleDatabaseAdapter extends ModuleDatabaseAdapter {
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<BoolComponent> components = new ArrayList<>();

    public MoodModuleDatabaseAdapter(ModuleInfo info) {
        super(info);
    }

    @Override
    public Module constructModule(Cursor cursor) {
        MoodModule module = new MoodModule(getComponents());

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
    public Module blankModule() {
        return null;
    }

    @Override
    public void putModule(ContentValues values, Module module) {

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
