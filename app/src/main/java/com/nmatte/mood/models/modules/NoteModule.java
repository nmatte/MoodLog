package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NoteModuleAdapter;

public class NoteModule extends Module {
    String note;

    public NoteModule(String note) {
        this.note = note;
    }

    public String get() {
        return note;
    }

    public void set(String note) {
        this.note = note;
    }


    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new NoteModuleAdapter(context, this);
    }

}
