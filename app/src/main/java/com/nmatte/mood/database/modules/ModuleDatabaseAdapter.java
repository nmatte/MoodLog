package com.nmatte.mood.database.modules;

import android.database.Cursor;

import com.nmatte.mood.models.modules.Module;

import java.util.ArrayList;

public abstract class ModuleDatabaseAdapter {
    ModuleTableHelper helper;
    private String moduleName;
    private ModuleInfo info = null;

    public ModuleDatabaseAdapter(ModuleTableHelper helper, String moduleName) {
        this.helper = helper;
        this.moduleName = moduleName;
    }

    private void queryInfo() {
        info = helper.getModuleInfo(moduleName);
    }

    public boolean isVisible() {
        if (info == null) {
            queryInfo();
        }

        return info.isVisible;
    }

    public long getId() {
        if (info == null) {
            queryInfo();
        }

        return info.id;
    }

    abstract public Module constructModule(Cursor cursor);

    abstract public ArrayList<String> getColumnNames();
}
