package com.nmatte.mood.database.modules;

import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.models.modules.Module;

import java.util.ArrayList;

public abstract class ModuleDatabaseAdapter {
    private ModuleInfo info;

    public ModuleDatabaseAdapter(ModuleInfo info) {
        this.info = info;
    }


    public boolean isVisible() {
        return info.isVisible;
    }

    public long getId() {
        return info.id;
    }

    abstract public Module constructModule(Cursor cursor);

    abstract public Module blankModule();

    abstract public void putModule(ContentValues values, Module module);

    abstract public ArrayList<String> getColumnNames();
}
