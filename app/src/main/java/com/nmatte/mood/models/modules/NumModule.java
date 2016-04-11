package com.nmatte.mood.models.modules;


import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NumModuleAdapter;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

/**
 * The num module is a component of a chart entry.
 * It maps numItems to their values for a given day.
 */
public class NumModule extends Module {
    private ArrayList<NumComponent> components = new ArrayList<>();

    public NumModule(long id, String name, boolean isEnabled, ArrayList<NumComponent> components) {
        super(id, name, isEnabled);
        this.components = components;
    }

    public ArrayList<NumComponent> getItems() {
        return components;
    }

    @Override
    public ModuleAdapter getViewAdapter(Context context) {
        return new NumModuleAdapter(context, this);
    }

}
