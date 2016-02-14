package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.BoolItem;
import com.nmatte.mood.models.BoolModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.CellView;
import com.nmatte.mood.views.chart.ImageCellView;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

public class BoolModuleAdapter extends ModuleAdapter {
    BoolModule module;

    public BoolModuleAdapter(Context context, BoolModule module) {
        super(context, module);
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews() {
        ArrayList<View> views = new ArrayList<>();

        for (BoolItem item : module.getItems()) {
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            b
                    .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m))
                    .setText(item.getName())
                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
                    .build();
            views.add(b.build());
        }

        return views;
    }

    @Override
    public ArrayList<View> getReadViews() {
        ArrayList<View> views = new ArrayList<>();

        for (BoolItem item : module.getItems()) {
            ImageCellView imageCellView = new ImageCellView(context, false);
            imageCellView.setBackground(CellView.Background.NONE);
            imageCellView.setChecked(module.get(item));

            views.add(imageCellView);
        }


        return views;
    }

    @Override
    public ArrayList<View> getEditViews() {
        ArrayList<View> views = new ArrayList<>();

        for (final BoolItem item : module.getItems()) {
            ImageCellView cellView = new ImageCellView(context, true);
            cellView.setOnChangeListener(new ImageCellView.OnChangeListener() {
                @Override
                public void onChange(boolean value) {
                    module.set(item, value);
                }
            });
            views.add(cellView);
        }


        return views;
    }
}
