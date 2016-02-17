package com.nmatte.mood.models.modules;


import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.NumModuleAdapter;
import com.nmatte.mood.models.NumComponent;

import java.util.ArrayList;

/**
 * The num module is a component of a chart entry.
 * It maps numItems to their values for a given day.
 */
public class NumModule extends Module {
    private SimpleArrayMap<NumComponent,Integer> numItems;

    public NumModule(SimpleArrayMap<NumComponent, Integer> numItems) {
        this.numItems = numItems;
    }

    public ArrayList<NumComponent> getItems() {
        ArrayList<NumComponent> items = new ArrayList<>();

        for (int i = 0; i < numItems.size(); i++) {
            items.add(numItems.keyAt(i));
        }

        return items;
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
