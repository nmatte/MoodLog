package com.nmatte.mood.models;


import android.support.v4.util.SimpleArrayMap;

import java.util.ArrayList;

/**
 * The num module is a component of a chart entry.
 * It maps numItems to their values for a given day.
 */
public class NumModule extends Module {
    private SimpleArrayMap<NumItem,Integer> numItems;

    public NumModule(SimpleArrayMap<NumItem, Integer> numItems) {
        this.numItems = numItems;
    }

    public ArrayList<NumItem> getItems() {
        ArrayList<NumItem> items = new ArrayList<>();

        for (int i = 0; i < numItems.size(); i++) {
            items.add(numItems.keyAt(i));
        }

        return items;
    }

    public int get(NumItem numlItem){
        return this.numItems.get(numlItem);
    }

    public void set(NumItem numlItem, int value) {
        this.numItems.put(numlItem, value);
    }
}
