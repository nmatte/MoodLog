package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;

public abstract class Module {
    abstract public ModuleAdapter getAdapter(Context context);

}
