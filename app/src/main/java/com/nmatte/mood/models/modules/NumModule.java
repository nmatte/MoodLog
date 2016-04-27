package com.nmatte.mood.models.modules;


import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NumModuleAdapter;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

/**
 * The num module is a component of a chart entry.
 * It maps numItems to their values for a given day.
 */
public class NumModule extends Module {
    private ArrayList<NumComponent> components = new ArrayList<>();

    public NumModule(boolean isEnabled, ArrayList<NumComponent> components) {
        super(ModuleContract.Num.ID, ModuleContract.Num.NAME, isEnabled);
        this.components = components;
    }

    public ArrayList<NumComponent> getItems() {
        return components;
    }

    @Override
    public ModuleAdapter getViewAdapter() {
        return new NumModuleAdapter(this);
    }

}
