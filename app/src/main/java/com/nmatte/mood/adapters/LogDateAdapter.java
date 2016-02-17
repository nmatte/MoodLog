package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

public class LogDateAdapter extends ModuleAdapter{
    LogDateModule module;

    public LogDateAdapter(Context context, LogDateModule module) {
        super(context, module);
        this.module = module;
    }

    @Override
    protected ArrayList<View> getLabelViews() {
        ArrayList<View> views = new ArrayList<>();
        TextCellViewBuilder b = new TextCellViewBuilder(context);
        b
                .setText("Date")
                .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));

        views.add(b.build());

        return views;
    }

    @Override
    protected ArrayList<View> getReadViews() {
        ArrayList<View> views = new ArrayList<>();
        TextCellViewBuilder b = new TextCellViewBuilder(context);
        b
                .setText(String.valueOf(module.getDate().getDayOfMonth()))
                .setHorizontalAlignment(TextCellView.TextAlignment.CENTER);
        if (module.isToday()) {
            b.setStroke(TextCellView.Stroke.BOLD);
        }

        views.add(b.build());

        return views;
    }

    @Override
    protected ArrayList<View> getEditViews() {
        ArrayList<View> views = new ArrayList<>();
        TextCellViewBuilder b = new TextCellViewBuilder(context);

        String text = module.isToday() ? "Today" : module.getDate().toString(ChartEntry.EDIT_ENTRY_FORMATTER);
        b
                .setText(text)
                .setHorizontalAlignment(TextCellView.TextAlignment.CENTER);


        views.add(b.build());

        return views;
    }
}
