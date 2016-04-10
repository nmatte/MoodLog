package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NoteModuleAdapter;

public class NoteModule extends Module {
    String note;

    public NoteModule(long id, String name, boolean isEnabled) {
        super(id, name, isEnabled);
    }


    public String get() {
        return note;
    }

    public void set(String note) {
        this.note = note;
    }


    @Override
    public ModuleAdapter getViewAdapter(Context context) {
        return new NoteModuleAdapter(context, this);
    }

}
