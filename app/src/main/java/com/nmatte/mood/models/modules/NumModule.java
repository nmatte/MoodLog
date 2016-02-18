package com.nmatte.mood.models.modules;


import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NumModuleAdapter;
import com.nmatte.mood.models.components.NumComponent;

import java.util.ArrayList;

/**
 * The num module is a component of a chart entry.
 * It maps numItems to their values for a given day.
 */
public class NumModule extends Module {
    private SimpleArrayMap<NumComponent,Integer> numItems;
    private ArrayList<NumComponent> components = new ArrayList<>();

    public NumModule(SimpleArrayMap<NumComponent, Integer> numItems) {
        this.numItems = numItems;
    }

    public NumModule(ArrayList<NumComponent> components) {
        this.components = components;
    }

    public ArrayList<NumComponent> getItems() {
        if (components.isEmpty()) {
            for (int i = 0; i < numItems.size(); i++) {
                components.add(numItems.keyAt(i));
            }
        }

        return components;
    }

    public int get(NumComponent numlItem){
        return this.numItems.get(numlItem);
    }

    public void set(NumComponent numlItem, int value) {
        this.numItems.put(numlItem, value);
    }

    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new NumModuleAdapter(context, this);
    }

}
