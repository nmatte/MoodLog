package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.views.chart.ChartColumn;

import java.util.ArrayList;

abstract public class ModuleAdapter {

    protected final Module module;

    public ModuleAdapter(Module module) {
        this.module = module;
    }

    public ArrayList<View> getViews(ChartColumn.Mode mode, Context context) {
        switch(mode) {
            case ENTRY_READ:
                return getReadViews(context);
            case ENTRY_EDIT:
                return getEditViews(context);
            case LABEL:
                return getLabelViews(context);
            default:
                return new ArrayList<>();
        }
    }

    abstract protected ArrayList<View> getLabelViews(Context context);
    abstract protected ArrayList<View> getReadViews(Context context);
    abstract protected ArrayList<View> getEditViews(Context context);
}
