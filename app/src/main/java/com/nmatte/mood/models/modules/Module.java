package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;

import java.util.ArrayList;

public abstract class Module {
    long id;
    String name;
    boolean isEnabled;
    ArrayList<String> columns;

    public ArrayList<String> getColumns() {
        return columns;
    }

    abstract public ModuleAdapter getViewAdapter(Context context);
}
