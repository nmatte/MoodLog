package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.modules.Module;
import com.nmatte.mood.views.chart.ChartColumn;

import java.util.ArrayList;

abstract public class ModuleAdapter {

    protected final Context context;
    protected final Module module;

    public ModuleAdapter(Context context, Module module) {
        this.context = context;
        this.module = module;
    }

    public ArrayList<View> getViews(ChartColumn.Mode mode) {
        switch(mode) {
            case ENTRY_READ:
                return getReadViews();
            case ENTRY_EDIT:
                return getEditViews();
            case LABEL:
                return getLabelViews();
            default:
                return new ArrayList<>();
        }
    }

    abstract protected ArrayList<View> getLabelViews();
    abstract protected ArrayList<View> getReadViews();
    abstract protected ArrayList<View> getEditViews();
}
