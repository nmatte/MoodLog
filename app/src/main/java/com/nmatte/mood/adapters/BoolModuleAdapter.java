package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.components.BoolComponent;
import com.nmatte.mood.models.modules.BoolModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

public class BoolModuleAdapter extends ModuleAdapter {
    BoolModule module;

    public BoolModuleAdapter(BoolModule module) {
        this.module = module;
    }

    @Override
    protected ArrayList<View> getLabelViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

        for (BoolComponent item : module.getItems()) {
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
    protected ArrayList<View> getReadViews(Context context) {
        ArrayList<View> views = new ArrayList<>();

//        for (BoolComponent item : module.getItems()) {
//            ImageCellView imageCellView = new ImageCellView(context, false);
//            imageCellView.setBackground(CellView.Background.NONE);
//            imageCellView.setChecked(module.get(item));
//
//            views.add(imageCellView);
//        }


        return views;
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
