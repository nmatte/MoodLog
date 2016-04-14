package com.nmatte.mood.adapters;

import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.ModuleConfig;
import com.nmatte.mood.views.chart.columns.ReadView;

import java.util.ArrayList;

public class EntryAdapter {
    ModuleConfig config;
    ArrayList<ModuleAdapter> adapters;

    public EntryAdapter(ModuleConfig config) {
        this.config = config;
        adapters = config.adapters();
    }

    public ReadView getView(Context context, ChartEntry entry) {
        ReadView readView = new ReadView(context);

        for (ModuleAdapter adapter : adapters) {
            for (View view : adapter.getReadViews(context)) {
                readView.addView(view);
            }
        }

        return readView;
    }
}
