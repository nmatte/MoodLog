package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.NoteModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.cells.ImageCellViewBuilder;
import com.nmatte.mood.views.chart.cells.TextCellView;
import com.nmatte.mood.views.chart.cells.TextCellViewBuilder;

import java.util.ArrayList;

import rx.Observable;

public class NoteModuleAdapter extends ModuleAdapter {
    NoteModule module;

    public NoteModuleAdapter(NoteModule module) {
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews(Context context) {
        TextCellViewBuilder b = new TextCellViewBuilder(context)
                .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m))
                .setStroke(TextCellView.Stroke.BOLD)
                .setText("Notes");

        ArrayList<View> result = new ArrayList<View> ();
        result.add(b.build());

        return result;
    }

    @Override
    public rx.Observable<View> getReadViews(Context context, ChartEntry entry) {
        ImageCellViewBuilder b = new ImageCellViewBuilder(context)
                .setBackgroundColor(context.getResources().getColor(R.color.white))
                .setValue(false)
                .setImageResource(R.drawable.ic_assignment_black_24dp);

        if (module.get().length() > 0){
            b.setValue(true);
            // TODO replace
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    EventBus.getDefault().post(new ChartEvents.OpenNoteEvent(module));
//                }
//            });
        }

        return rx.Observable.just(b.build());
    }

    @Override
    protected Observable<View> getEditViews(Context context, ChartEntry entry) {
//        ImageCellView cellView = new ImageCellView(context, 0x000000, false);
//        if (module.get().length() > 0){
//            cellView.setImageResource(R.drawable.ic_assignment_black_24dp);
//        } else {
//            cellView.setImageResource(R.drawable.ic_edit_black_24dp);
//        }
//        cellView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EventBus.getDefault().post(new ChartEvents.OpenNoteEvent(module));
//            }
//        });
//        cellView.setValue(true);

        ArrayList<View> result = new ArrayList<View> ();
//        result.add(cellView);

        return Observable.from(result);
    }
}
