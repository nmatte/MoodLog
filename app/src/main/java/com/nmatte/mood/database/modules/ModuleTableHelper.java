package com.nmatte.mood.database.modules;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ModuleTableHelper {
    private SQLiteDatabase db;

    public ModuleTableHelper(SQLiteDatabase db) {
        this.db = db;
    }

    /**
     * @param moduleName The moduleName to receive its metadata.
     * @return A ModuleInfo object containing the id and isVisible attributes.
     */
    public ModuleInfo getModuleInfo(String moduleName) {
        String [] columns = {
                ModuleContract.MODULE_ID_COLUMN,
                ModuleContract.MODULE_VISIBLE_COLUMN
        };

        String selection = ModuleContract.MODULE_NAME_COLUMN + "= ?";

        String [] args = {
                moduleName
        };

        Cursor c = db.query(
                ModuleContract.MODULE_TABLE_NAME,
                columns,
                selection,
                args,
                null, null, null
        );

        ModuleInfo info = null;

        if (c.getCount() > 0) {
            c.moveToFirst();
            info = new ModuleInfo(c.getLong(0), c.getInt(1) == 1);
        }
        c.close();

        return info;
    }
}
