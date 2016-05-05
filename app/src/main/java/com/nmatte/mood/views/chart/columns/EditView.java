package com.nmatte.mood.views.chart.columns;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nmatte.mood.adapters.EntryAdapter;
import com.nmatte.mood.models.ChartEntry;

public class EditView extends LinearLayout {
    ChartEntry entry;

    public EditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public void setEntry(EntryAdapter adapter, ChartEntry entry) {
        removeAllViews();
        this.entry = entry;
        adapter.getEditViews(getContext(), entry)
                .doOnNext(this::addView)
                .doOnError(Throwable::printStackTrace)
                .subscribe();
    }

    public ChartEntry getEntry() {
        return entry;
    }
}
