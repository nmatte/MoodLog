package com.nmatte.mood.chart;

import android.content.Context;
import android.widget.LinearLayout;

import com.nmatte.mood.logbookentries.LogbookEntry;

public class ReadonlyColumn extends LinearLayout {
    public ReadonlyColumn(Context context) {
        super(context);
    }

    public ReadonlyColumn(Context context, LogbookEntry entry){
        super(context);
    }
}
