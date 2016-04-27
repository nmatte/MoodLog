package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.cells.ImageCellViewBuilder;
import com.nmatte.mood.views.chart.cells.TextCellView;
import com.nmatte.mood.views.chart.cells.TextCellViewBuilder;

import java.util.ArrayList;

import rx.Observable;

public class BoolModuleAdapter extends ModuleAdapter {
    BoolModule module;

    public BoolModuleAdapter(BoolModule module) {
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

        for (BoolComponent item : module.getItems()) {
            TextCellViewBuilder b = new TextCellViewBuilder(context)
                    .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m))
                    .setText(item.getName())
                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER);
            views.add(b.build());
        }

        return views;
    }

    @Override
    public Observable<View> getReadViews(Context context, ChartEntry entry) {
        return Observable
                .from(module.getItems())
                .map(component -> {
                    boolean value = entry.values().containsKey(component.columnLabel())
                            ?  entry.values().getAsBoolean(component.columnLabel())
                            : false;

                    return new ImageCellViewBuilder(context)
                            .setBackgroundColor(component.getColor())
                            .setValue(value)
                            .build();
                });
    }

    @Override
    protected Observable<View> getEditViews(Context context, ChartEntry entry) {
        ArrayList<View> views = new ArrayList<>();
//
//        for (final BoolComponent item : module.getItems()) {
//            ImageCellView cellView = new ImageCellView(context, true);
//            cellView.setOnChangeListener(new ImageCellView.OnChangeListener() {
//                @Override
//                public void onChange(boolean value) {
//                    module.set(item, value);
//                }
//            });
//            views.add(cellView);
//        }


        return Observable.from(views);
    }
}
