package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.models.NoteModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.views.chart.ImageCellView;
import com.nmatte.mood.views.chart.OpenNoteEvent;
import com.nmatte.mood.views.chart.TextCellView;
import com.nmatte.mood.views.chart.TextCellViewBuilder;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

public class NoteModuleAdapter extends ModuleAdapter {
    NoteModule module;

    public NoteModuleAdapter(Context context, NoteModule module) {
        super(context, module);
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews() {
        TextCellViewBuilder b = new TextCellViewBuilder(context)
                .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m))
                .setStroke(TextCellView.Stroke.BOLD)
                .setText("Notes");

        ArrayList<View> result = new ArrayList<View> ();
        result.add(b.build());

        return result;
    }

    @Override
    public ArrayList<View> getReadViews() {
        ImageCellView cellView = new ImageCellView(context, false);
        cellView.setImageResource(R.drawable.ic_assignment_black_24dp);

        if (module.get().length() > 0){
            cellView.setChecked(true);
            cellView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new OpenNoteEvent(module));
                }
            });
        }

        ArrayList<View> result = new ArrayList<View> ();
        result.add(cellView);

        return result;
    }

    @Override
    public ArrayList<View> getEditViews() {
        ImageCellView cellView = new ImageCellView(context, false);
        if (module.get().length() > 0){
            cellView.setImageResource(R.drawable.ic_assignment_black_24dp);
        } else {
            cellView.setImageResource(R.drawable.ic_edit_black_24dp);
        }
        cellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OpenNoteEvent(module));
            }
        });
        cellView.setChecked(true);

        ArrayList<View> result = new ArrayList<View> ();
        result.add(cellView);

        return result;
    }
}
