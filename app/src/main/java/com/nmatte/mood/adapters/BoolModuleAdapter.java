package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.ImageCellView;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

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
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            b
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
                    boolean value = false;
                    if (entry.values().containsKey(component.columnLabel())) {
                        value = entry.values().getAsBoolean(component.columnLabel());
                    }
                    ImageCellView imageCellView = new ImageCellView(context, false);
                    imageCellView.setBackgroundColor(component.getColor());
                    imageCellView.setChecked(value);
                    return imageCellView;
                });
    }

    @Override
    protected ArrayList<View> getEditViews(Context context) {
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


        return views;
    }
}
