package com.nmatte.mood.models.modules;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.adapters.BoolModuleAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.models.BoolComponent;

import java.util.ArrayList;

public class BoolModule extends Module {
    private SimpleArrayMap<BoolComponent,Boolean> boolItems;

    public BoolModule(SimpleArrayMap<BoolComponent, Boolean> boolItems) {
        this.boolItems = boolItems;
    }

    public ArrayList<BoolComponent> getItems() {
        ArrayList<BoolComponent> items = new ArrayList<>();

        for (int i = 0; i < boolItems.size(); i++) {
            items.add(boolItems.keyAt(i));
        }

        return items;
    }

    public boolean get(BoolComponent boolItem){
        return this.boolItems.get(boolItem);
    }

    public void set(BoolComponent boolItem, boolean value) {
        this.boolItems.put(boolItem, value);
    }

    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new BoolModuleAdapter(context, this);
    }
}
