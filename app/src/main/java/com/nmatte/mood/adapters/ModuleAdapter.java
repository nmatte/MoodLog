package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.Module;

import java.util.ArrayList;

abstract public class ModuleAdapter {

    protected final Context context;
    protected final Module module;

    public ModuleAdapter(Context context, Module module) {
        this.context = context;
        this.module = module;
    }

    abstract public ArrayList<View> getLabelViews();
    abstract public ArrayList<View> getReadViews();
    abstract public ArrayList<View> getEditViews();
}
