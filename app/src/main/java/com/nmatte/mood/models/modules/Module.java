package com.nmatte.mood.models.modules;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.models.components.LogbookComponent;

import java.util.ArrayList;

public abstract class Module {
    long id;
    String name;
    boolean isEnabled;
    ArrayList<LogbookComponent> components;

    public Module(long id, String name, boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.isEnabled = isEnabled;
//        this.components = components;
    }

    abstract public ModuleAdapter getViewAdapter();
}
