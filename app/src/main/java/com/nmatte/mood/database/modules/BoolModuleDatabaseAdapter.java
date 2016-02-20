package com.nmatte.mood.database.modules;

import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.models.modules.Module;

import java.util.ArrayList;

public class BoolModuleDatabaseAdapter extends ModuleDatabaseAdapter {
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<BoolComponent> components = new ArrayList<>();


    public BoolModuleDatabaseAdapter(ModuleInfo info) {
        super(info);
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

    public BoolModule constructModule (Cursor c) {
        BoolModule module = new BoolModule(getComponents());

        for (BoolComponent component :
                getComponents()) {
            int index = c.getColumnIndex(component.toString());
            if (index > 0){
                module.set(component,c.getInt(index) != 0);
            }
        }

        return module;
    }

    @Override
    public Module blankModule() {
        // TODO!!
        return null;
    }

    @Override
    public void putModule(ContentValues values, Module module) {
        //TODO!!
    }
}
