package com.nmatte.mood.views.chart.cells;

import android.content.Context;

public abstract class CellViewBuilder {
    Context context;
    int backgroundColor = -1;

    public CellViewBuilder(Context context) {
        this.context = context;
    }

    public abstract CellViewBuilder setBackgroundColor(int color);

    public abstract CellView build();


}

