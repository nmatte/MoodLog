package com.nmatte.mood.adapters;

import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.NumModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.CellView;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

import rx.Observable;

public class NumModuleAdapter extends ModuleAdapter{
    NumModule module;

    public NumModuleAdapter(NumModule module) {
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

        for (NumComponent item : module.getItems()) {
            TextCellViewBuilder b = new TextCellViewBuilder(context)
                    .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m))
                    .setText(item.getName());
            views.add(b.build());

        }

        return views;
    }

    @Override
    public Observable<View> getReadViews(Context context, ChartEntry entry) {
        return Observable
                .from(module.getItems())
                .map(component -> {
                    String value = "";

                    if (entry.values().containsKey(component.columnLabel())) {
                        value = entry.values().getAsString(component.columnLabel());
                    }

                    return new TextCellViewBuilder(context)
                            .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                            .setHorizontalAlignment(TextCellView.TextAlignment.CENTER)
                            .setBackground(CellView.Background.NONE)
                            .setText(value)
                            .build();
                });
    }

    @Override
    protected ArrayList<View> getEditViews(Context context) {
        ArrayList<View> views = new ArrayList<>();
//
//        for (final NumComponent numItem : module.getItems()) {
//                final CustomNumberPicker numPicker = new CustomNumberPicker(context,numItem);
//                numPicker.setNumChangeListener(new CustomNumberPicker.NumChangeListener() {
//                    @Override
//                    public void onChange(int change) {
//                        module.set(numItem, change);
//                    }
//                });
//                views.add(numPicker);
//
//        }

        return views;
    }
}
