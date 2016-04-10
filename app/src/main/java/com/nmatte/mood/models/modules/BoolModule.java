package com.nmatte.mood.models.modules;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;

import com.nmatte.mood.adapters.BoolModuleAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class BoolModule extends Module {
    private SimpleArrayMap<BoolComponent,Boolean> valuesMap;
    private ArrayList<BoolComponent> components = new ArrayList<>();

    public BoolModule(long id, String name, boolean isEnabled, ArrayList<BoolComponent> components) {
        super(id, name, isEnabled);
    }


//
//    public BoolModule(ArrayList<BoolComponent> components) {
//        this.valuesMap = new SimpleArrayMap<>();
//        this.components = components;
//
//        for (BoolComponent component :
//                components) {
//            valuesMap.put(component, false);
//        }
//    }

    public ArrayList<BoolComponent> getItems() {
        if (this.components.isEmpty()) {
            for (int i = 0; i < valuesMap.size(); i++) {
                this.components.add(valuesMap.keyAt(i));
            }
        }

        return this.components;
    }

    public boolean get(BoolComponent boolItem){
        return this.valuesMap.get(boolItem);
    }

    public void set(BoolComponent boolItem, boolean value) {
        this.valuesMap.put(boolItem, value);
    }

    @Override
    public ModuleAdapter getViewAdapter(Context context) {
        return new BoolModuleAdapter(context, this);
    }

}
