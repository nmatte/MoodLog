package com.nmatte.mood.models.modules;

import android.content.Context;

import com.nmatte.mood.adapters.ModuleAdapter;
import com.nmatte.mood.adapters.MoodModuleAdapter;
import com.nmatte.mood.models.components.BoolComponent;

import java.util.ArrayList;

public class MoodModule extends BoolModule {

    boolean isMini;

    public MoodModule(ArrayList<BoolComponent> components, boolean isMini) {
        super(components);

        this.isMini = isMini;
    }

    public boolean isMini() {
        return isMini;
    }

    @Override
    public ModuleAdapter getAdapter(Context context) {
        return new MoodModuleAdapter(context, this);
    }

}
