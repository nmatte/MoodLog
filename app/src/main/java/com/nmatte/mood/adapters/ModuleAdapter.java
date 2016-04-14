package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.views.chart.columns.ChartColumn;

import java.util.ArrayList;

abstract public class ModuleAdapter {

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

    public abstract ArrayList<View> getLabelViews(Context context);
    abstract protected ArrayList<View> getReadViews(Context context);
    abstract protected ArrayList<View> getEditViews(Context context);
}
