package com.nmatte.mood.database.modules;

import android.database.Cursor;

import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.NumModule;

import java.util.ArrayList;

public class NumModuleDatabaseAdapter extends ModuleDatabaseAdapter {
    ArrayList<String> columnNames = new ArrayList<>();
    ArrayList<NumComponent> components = new ArrayList<>();

    public NumModuleDatabaseAdapter(ModuleInfo info) {
        super(info);
    }

    @Override
    public Module constructModule(Cursor cursor) {
        NumModule module = new NumModule(getComponents());
        for (NumComponent component : getComponents()) {
            int index = cursor.getColumnIndex(component.columnLabel());
            if (index > 0) {
                module.set(component, cursor.getInt(index));
            }
        }

        return module;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        // TODO populate list
        return columnNames;
    }

    private ArrayList<NumComponent> getComponents() {
        // TODO populate list
        return components;
    }
}
