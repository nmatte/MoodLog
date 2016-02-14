package com.nmatte.mood.models;

import android.support.v4.util.SimpleArrayMap;

import java.util.ArrayList;

public class BoolModule extends Module {
    private SimpleArrayMap<BoolItem,Boolean> boolItems;

    public BoolModule(SimpleArrayMap<BoolItem, Boolean> boolItems) {
        this.boolItems = boolItems;
    }

    public ArrayList<BoolItem> getItems() {
        ArrayList<BoolItem> items = new ArrayList<>();

        for (int i = 0; i < boolItems.size(); i++) {
            items.add(boolItems.keyAt(i));
        }

        return items;
    }

    public boolean get(BoolItem boolItem){
        return this.boolItems.get(boolItem);
    }

    public void set(BoolItem boolItem, boolean value) {
        this.boolItems.put(boolItem, value);
    }
}
