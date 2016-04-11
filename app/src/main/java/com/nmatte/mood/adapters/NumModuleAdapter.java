package com.nmatte.mood.adapters;

import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.components.NumComponent;
import com.nmatte.mood.models.modules.NumModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

public class NumModuleAdapter extends ModuleAdapter{
    NumModule module;

    public NumModuleAdapter(Context context, NumModule module) {
        super(module);
        this.module = module;
    }

    @Override
    protected ArrayList<View> getLabelViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

        for (NumComponent item : module.getItems()) {
            TextCellViewBuilder b = new TextCellViewBuilder(context);
            b.setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));

            b.setText(item.getName());
            views.add(b.build());

        }

        return views;
    }

    @Override
    protected ArrayList<View> getReadViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

//        for (NumComponent item : module.getItems()) {
//            TextCellViewBuilder b = new TextCellViewBuilder(context)
//                    .setVerticalAlignment(TextCellView.TextAlignment.CENTER)
//                    .setHorizontalAlignment(TextCellView.TextAlignment.CENTER)
//                    .setBackground(CellView.Background.NONE)
//                    .setText(String.valueOf(module.get(item)));
//
//            views.add(b.build());
//        }

        return views;
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
