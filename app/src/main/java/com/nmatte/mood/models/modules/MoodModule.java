package com.nmatte.mood.models.modules;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;
import com.nmatte.mood.database.modules.ModuleContract;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class MoodModule extends BoolModule {


    public MoodModule(boolean isEnabled, ArrayList<BoolComponent> components) {
        super(ModuleContract.Mood.ID, ModuleContract.Mood.NAME, isEnabled, components);
    }

    @Override
    public ModuleAdapter getViewAdapter() {
        return new MoodModuleAdapter(this);
    }

}
