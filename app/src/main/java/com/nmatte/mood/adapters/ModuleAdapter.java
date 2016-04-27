package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;

import java.util.ArrayList;

import rx.Observable;

abstract public class ModuleAdapter {

    public abstract ArrayList<View> getLabelViews(Context context);
    public abstract Observable<View> getReadViews(Context context, ChartEntry entry);
    abstract protected Observable<View> getEditViews(Context context, ChartEntry entry);
}
