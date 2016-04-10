package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class MoodModule extends BoolModule {


    public MoodModule(long id, String name, boolean isEnabled, ArrayList<BoolComponent> components) {
        super(id, name, isEnabled, components);
    }

    @Override
    public ModuleAdapter getViewAdapter(Context context) {
        return new MoodModuleAdapter(context, this);
    }

}
