package com.nmatte.mood.adapters;


import android.content.Context;
import android.view.View;

import com.nmatte.mood.database.entries.ChartEntryContract;
import com.nmatte.mood.models.ChartEntry;
import com.nmatte.mood.models.modules.LogDateModule;
import com.nmatte.mood.moodlog.R;
import com.nmatte.mood.util.DateUtils;
import com.nmatte.mood.views.chart.cells.TextCellView;
import com.nmatte.mood.views.chart.cells.TextCellViewBuilder;

import org.joda.time.DateTime;

import java.util.ArrayList;

import rx.Observable;

public class LogDateAdapter extends ModuleAdapter{
    LogDateModule module;

    public LogDateAdapter(LogDateModule module) {
        this.module = module;
    }

    @Override
    public ArrayList<View> getLabelViews(Context context) {
        ArrayList<View> views = new ArrayList<>();
        TextCellViewBuilder b = new TextCellViewBuilder(context);
        b
                .setText("Date")
                .setXoffset(context.getResources().getDimension(R.dimen.chart_cell_width_m));

        views.add(b.build());

        return views;
    }

    @Override
    public Observable<View> getReadViews(Context context, ChartEntry entry) {

        int dInt = entry.values().getAsInteger(ChartEntryContract.ENTRY_DATE_COLUMN);
        DateTime date = DateUtils.fromInt(dInt);

        String dateString = String.valueOf(date.getDayOfMonth());

        TextCellViewBuilder b = new TextCellViewBuilder(context)
                .setText(dateString)
                .setHorizontalAlignment(TextCellView.TextAlignment.CENTER);


        return Observable.just(b.build());
    }

    @Override
    protected ArrayList<View> getEditViews(Context context) {
        ArrayList<View> views = new ArrayList<>();
        TextCellViewBuilder b = new TextCellViewBuilder(context);

        String text = module.isToday() ? "Today" : module.getDate().toString(DateUtils.EDIT_ENTRY_FORMATTER);
        b
                .setText(text)
                .setHorizontalAlignment(TextCellView.TextAlignment.CENTER);


        views.add(b.build());

        return views;
    }
}
