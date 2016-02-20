package com.nmatte.mood.database.modules;


import android.content.ContentValues;
import android.database.Cursor;

import com.nmatte.mood.database.ChartEntryContract;
import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.models.modules.NoteModule;

import java.util.ArrayList;

public class NoteModuleDatabaseAdapter extends ModuleDatabaseAdapter{
    public NoteModuleDatabaseAdapter(ModuleInfo info) {
        super(info);
    }

    @Override
    public Module constructModule(Cursor cursor) {
        return new NoteModule(
                cursor.getString(cursor.getColumnIndex(ChartEntryContract.ENTRY_NOTE_COLUMN))
        );
    }

    @Override
    public Module blankModule() {
        //TODO
        return null;
    }

    @Override
    public void putModule(ContentValues values, Module module) {
        //TODO
    }

    @Override
    public ArrayList<String> getColumnNames() {
        String name = ChartEntryContract.ENTRY_NOTE_COLUMN;
        ArrayList<String> a = new ArrayList<>();
        a.add(name);
        return a;
    }
}
