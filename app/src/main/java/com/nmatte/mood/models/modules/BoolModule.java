package com.nmatte.mood.models.modules;

import com.nmatte.mood.adapters.BoolModuleAdapter;
import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class BoolModule extends Module {
    private ArrayList<BoolComponent> components = new ArrayList<>();

    public BoolModule(boolean isEnabled, ArrayList<BoolComponent> components) {
        super(ModuleContract.Bool.ID, ModuleContract.Bool.NAME, isEnabled);
        this.components = components;
    }

    protected BoolModule(long id, String name, boolean isEnabled, ArrayList<BoolComponent> components) {
        super(id, name, isEnabled);
        this.components = components;
    }

    public void setComponents(ArrayList<BoolComponent> components) {
        this.components = components;
    }

    public ArrayList<BoolComponent> getItems() {
        return this.components;
    }

    @Override
    public ModuleAdapter getViewAdapter() {
        return new BoolModuleAdapter(this);
    }

}
